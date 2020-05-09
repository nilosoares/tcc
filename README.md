# UERJ - Ciência da Computação

Esse é o repositório do projeto de final de curso do aluno Nilo Soares, orientado pela doutora Ana Carolina Almeida.

## Pré Requisitos

- Ubuntu 16.04 ou superior
- Docker 18.09 ou superior
- Docker Compose 1.17.1 ou superior
- Git

## Clonando o projeto

Após instalar e configurar o Git, abra o terminal (Ctrl Alt T) e execute os comandos abaixo:

    git clone git@github.com:nilosoares/tcc.git
  
## Instalando o TPC-H

Para instalar o TPC-H no MongoDB, basta executar o comando abaixo na raiz do projeto:

    bin/install
    
> Atenção: a instalação pode demorar alguns minutos.

## Executando o TPC-H

Após a instalação, execute o TPC-H usando o MongoDB usando o comando abaixo:

    bin/run
    
Os dados com os parâmetros utilizados e tempo de execução estarão disponíveis no arquivo "/var/logs/tpch.log".

> Atenção: o benchmark pode demorar algumas horas.
