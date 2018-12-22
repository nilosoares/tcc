# UERJ - Ciência da Computação

Esse é o repositório do projeto de final de curso do aluno Nilo Soares, orientado pela doutora Ana Carolina Almeida.

## Pré Requisitos

- Ubuntu 16.04 ou superior
- Docker 18.09 ou superior
- Docker Compose 1.23.2 ou superior
- Git

## Clonando o projeto

Após instalar e configurar o Git, abra o terminal (Ctrl Alt T) e execute o comando abaixo:

    git clone git@bitbucket.org:nilosoares/uerj-projeto-final-v2.git

## Iniciando os containers (Java, MongoDB, Postgres) 

Após intalar o Docker e Docker-Compose, abra o terminal na raiz do projeto e execute o comando abaixo:

    docker-compose up
    
> Importante: As imagens do Docker serão baixadas e construidas durante a primeira execução, portanto isso pode demorar alguns minutos.
Nas execuções seguintes o processo será mais rápido.

## Compilando o projeto

Para compilar o projeto, basta executar o comando abaixo:

    bin/build

## Gerando os dados do TPC-H 

Primeiro é necessário gerar os dados do TPC-H:

    bin/dbgen
    
Esse comando irá gerar 1GB de dados para o TPC-H.

> Atenção: isso pode demorar alguns segundos.

## Inserindo os dados do TPC-H no MongoDB

Para inserir os dados do TPC-H no MongoDB de forma rápida, precisamos primeiro inserir os dados no Postgres e então convertemos esses dados para o MongoDB.

    bin/populate_postgres
    bin/convert_to_nosql
    
> Atenção: isso pode demorar alguns minutos.

## Inserindo os dados do TPC-H no MongoDB (sem Postgres)

Você também pode inserir os dados diretamente no MongoDB sem criar uma instância do Postgres, porém esse algoritmo é extremamente lento.

    bin/populate_mongo
    bin/convert_to_nosql_slow
    
> Atenção: isso pode morar alguns dias.

## Executando o TPC-H

...

## To do

- Executando o TPC-H