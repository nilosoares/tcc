# UERJ - Ciência da Computação

Esse é repositório do TCC do aluno Nilo Silva Cancio Pereira Soares, formando de Ciência da Computação pela Universidade do Estado do Rio de Janeiro (UERJ), orientado pela doutora Ana Carolina Almeida. Esse projeto tem como objetivo avaliar tuning com índices parciais em Banco de Dados NOSQL (MongoDB).

## Requisitos

- Ubuntu 16.04 ou superior
- Docker 18.09 ou superior
- Docker Compose 1.17.1 ou superior
- Git

## Clonando o projeto

Após instalar e configurar o Git, abra o terminal e execute o comando abaixo:

    git clone https://github.com/nilosoares/tcc.git

## Iniciando os containers

Antes de executar qualquer programa é necessário baixar as imagens e iniciar os containers do Docker.
Para isso, basta executar o comando abaixo:

    bin/start
  
## Instalando o TPC-H

Para instalar o TPC-H no MongoDB, basta executar o comando abaixo na raiz do projeto:

    bin/dbgen
    
> Atenção: a instalação pode demorar alguns minutos.

## Gerando as consultas
 
Para gerar as consultas (sem executá-las), basta executar o comando abaixo na raiz do projeto:
 
     bin/qgen
     
As consultas geradas podem ser encontradas na pasta "output/mongo_query".

## Executando o TPC-H

Após gerar o banco de dados você pode executar as consultas do TPC-H no MongoDB. Para isso, basta executar o comando abaixo:

    bin/run
    
O código das consultas, explain e índices criados podem ser encontrados na pasta "output/mongo_query", "output/mongo_explain" e "output_index" respectivamente.
Cada consulta também gerará um arquivo ".log" que pode ser encontrado na pasta "output/logs". Esse arquivo contém os parâmetros utilizados, tempo de execução e plano de execução da consulta, tempo de criação de cada índice utilizado e estado do banco de dados. 

> Atenção: o benchmark pode demorar algumas horas dependendo da configuração da máquina.

## Dados usados no TCC

Os dados utilizados na elaboração das tabelas, gráficos e texto do TCC podem ser encontrados na pasta "resources/tpc-h-mongo-results".
Os resultados estão divididos em pastas que representam a quantidade de memória RAM de cada máquina utilizada nos testes.