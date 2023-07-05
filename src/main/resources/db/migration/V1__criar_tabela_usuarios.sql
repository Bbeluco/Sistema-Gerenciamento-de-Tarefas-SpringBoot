CREATE TABLE usuarios (
    id int NOT NULL auto_increment,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    tarefas VARCHAR(1024) NOT NULL,

    primary key(id)
);