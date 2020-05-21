# UERJ - Ciência da Computação

Esse é o repositório do projeto de final de curso do aluno Nilo Soares, orientado pela doutora Ana Carolina Almeida.

## Pré Requisitos

- Ubuntu 16.04 ou superior
- Docker 18.09 ou superior
- Docker Compose 1.17.1 ou superior
- Git

## Clonando o projeto

Após instalar e configurar o Git, abra o terminal (Ctrl Alt T) e execute o comando abaixo:

    git clone https://github.com/nilosoares/tcc.git

## Iniciando os containers

Antes de executar qualquer programa é necessário baixar as imagens e iniciar os containers do Docker.
Para isso, basta executar o comando abaixo:

    bin/start
  
## Instalando o TPC-H

Para instalar o TPC-H no MongoDB, basta executar o comando abaixo na raiz do projeto:

    bin/dbgen
    
> Atenção: a instalação pode demorar alguns minutos.

## Executando o TPC-H

Após a instalação, execute o TPC-H usando o MongoDB usando o comando abaixo:

    bin/run
    
O código das consultas, explain e índices criados podem ser encontrados na pasta "output/mongo_query", "output/mongo_explain" e "output_index" respectivamente.
Cada consulta também gerará um arquivo ".log" que pode ser encontrado na pasta "output/logs". Esse arquivo contém os parâmetros utilizados, tempo de execução e plano de execução da consulta, tempo de criação de cada índice utilizado e estado do banco de dados. 

> Atenção: o benchmark pode demorar algumas horas dependendo da configuração da máquina.
