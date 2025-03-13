# Case PIX

Projeto de um case baseado nas premissas de desenvolvimento PIX

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Lombok
* Spring Data
* JPA / Hibernate
* Bean Validations
* Spring Test
* OpenAPI 3.0
* Lombok
* Spring Initialzr 

## Ferramenta de build

* Maven

## Banco de dados Utilizado

* H2

## Designs Pattern utilizados

* Strategy Pattern : Utilizado para segregar as regras e validações de negócio por tipo PIX. A utilização do Pattern auxilia em casos de inclusão de novas validações ou regras para cada tipo de PIX, sem gerar impactos em outras funcionalidades. Além disso, deixa o código mais legível, sem necessidade de múltiplas condicionais.
* Builder Pattern : Utilizado em conjunto com a biblioteca Lombok (para diminuir boilerplate) para a construção de entidades/dtos. A utilização de ambos foi para uma melhorar a criação de objetos no código.


## 12 fatores

Abaixo encontram-se os fatores utilizados no projeto. 
1. Codebase
O código-fonte encontra-se no github, através da URL:https://github.com/rickakira/case.git

2. Dependencies
Utilizado o Maven como ferramenta de build e para organização das dependências utilizadas pelo projeto.

## Cobertura

A cobertura encontra-se na pasta coverage\htmlReport
