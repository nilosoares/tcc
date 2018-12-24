# Guia de Instalação

## Iniciando os containers (Java, MongoDB, Postgres) 

Primeiro precisamos baixar as imagens utilizadas pelo projeto. Para isso, execute o comando abaixo na raiz do projeto:

    docker-compose pull
    
Para iniciar os containers, execute o comando abaixo:
    
    docker-compose up -d
 
## Compilando o projeto

Para compilar o projeto, basta executar o comando abaixo:

    bin/build

## Gerando os dados do TPC-H 

Primeiro é necessário gerar os dados do TPC-H:

    bin/dbgen
    
Esse comando irá gerar 1GB de dados para o TPC-H.

> Atenção: isso pode demorar alguns segundos.

## Inserindo os dados do TPC-H no MongoDB usando Postgres (recomendado)

Para inserir os dados do TPC-H no MongoDB de forma rápida, precisamos primeiro inserir os dados no Postgres e então convertemos esses dados para o MongoDB.

    bin/populate_postgres
    bin/convert_to_nosql
    
> Atenção: isso pode demorar alguns minutos.

## Inserindo os dados do TPC-H no MongoDB sem Postgres

Você também pode inserir os dados diretamente no MongoDB sem criar uma instância do Postgres, porém esse algoritmo é extremamente lento.

    bin/populate_mongo
    bin/convert_to_nosql_slow
    
> Atenção: isso pode morar alguns dias.