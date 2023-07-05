package com.projeto_pessoal.SistemaGerenciamentoTarefas.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.ErrosGenericosDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@SpringBootTest
class UsuarioControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@BeforeEach
	private void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroNome() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("nome");


		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		String bodyRecebido = resultadoRequisicao.getResponse().getContentAsString();
		ErrosGenericosDTO erroEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Nome e um campo obrigatorio e deve ser enviado");

		Assert.assertEquals(erroEsperado, converteParaDTO(bodyRecebido));
	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroCargo() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("cargo");

		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTO(resultadoRequisicao.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Cargo e um campo obrigatorio e deve ser enviado");


		Assert.assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroTarefas() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("tarefas");

		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTO(resultadoRequisicao.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Tarefas e um campo obrigatorio e deve ser enviado");

		Assert.assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	@Test
	void deveRetornarErroQuandoEnviarParametroTarefasContendoUmCaractereInvalido() throws Exception {
		JSONObject usuario = criaJsonSemParametro("tarefas");
		usuario.put("tarefas", new JSONArray().put("teste123"));

		MvcResult resultadoRecebido = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
										.contentType(MediaType.APPLICATION_JSON).content(usuario.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTO(resultadoRecebido.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Tarefas deve conter apenas letras e espacos");

		Assert.assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	private ErrosGenericosDTO converteParaDTO(String json) throws IOException {
		ObjectMapper objTest = new ObjectMapper();
		return objTest.readValue(json, ErrosGenericosDTO.class);
	}

	private JSONObject criaJsonSemParametro(String parametroParaNaoUtilizar) {
		JSONObject usuario = new JSONObject();
		JSONArray tarefas = new JSONArray();
		tarefas.put("teste");
		try {
			usuario.put("nome", "Bruno");
			usuario.put("cargo", "QA");
			usuario.put("tarefas", tarefas);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}


		usuario.remove(parametroParaNaoUtilizar);

		return usuario;
	}
}
