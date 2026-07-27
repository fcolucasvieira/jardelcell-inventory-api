# 📦 JardelCell Inventory API

![Java](https://img.shields.io/badge/Java-21-FFD700?style=for-the-badge&logo=openjdk&logoColor=black)
![Spring Boot](https://img.shields.io/badge/spring_boot-3.5-6DB33F?style=for-the-badge&logo=springboot)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-ORM-6DB33F?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/postgresql-database-2496ED?style=for-the-badge&logo=postgresql&logoColor=2496ED)
![Flyway](https://img.shields.io/badge/flyway-migrations-CC0200?style=for-the-badge)
![Spring Security](https://img.shields.io/badge/spring_security-JWT-6DB33F?style=for-the-badge&logo=springsecurity)
![Docker](https://img.shields.io/badge/docker-containerization-2496ED?style=for-the-badge&logo=docker)
![JUnit](https://img.shields.io/badge/JUnit_5-Testing-25A162?style=for-the-badge&logo=junit5&logoColor=25A162)
![Mockito](https://img.shields.io/badge/Mockito-Mocking-red?style=for-the-badge)
![Swagger](https://img.shields.io/badge/swagger-api--docs-green?style=for-the-badge&logo=swagger)
![Render](https://img.shields.io/badge/render-cloud-black?style=for-the-badge&logo=render)

---

# 📌 Sobre o projeto

A **JardelCell Inventory API** é uma aplicação backend desenvolvida para gerir o estoque de dispositivos Apple da empresa JardelCell.

O sistema centraliza o gerenciamento completo do ciclo de vida dos dispositivos, permitindo acompanhar cada produto desde a sua entrada no estoque até a sua venda, troca, reserva, envio para reparo, retorno ao estoque ou descarte, mantendo o histórico completo das movimentações realizadas pelos colaboradores.

Além da resolução do problema de negócio, o projeto foi desenvolvido seguindo boas práticas de engenharia de software, adotando princípios como **Clean Code**, **SOLID**, arquitetura modular e autenticação baseada em **JWT**, buscando representar uma aplicação próxima ao ambiente corporativo.

---

# 🎯 Problema

Antes da aplicação, o controle do estoque era realizado manualmente, dificultando operações importantes como:

- localização rápida de aparelhos;
- controle de disponibilidade do estoque;
- rastreabilidade do histórico completo de cada aparelho;
- cálculo do investimento atual;
- acompanhamento do faturamento obtido com vendas e trocas;
- identificação do responsável por cada movimentação.

À medida que o volume de produtos aumentava, esse processo tornava-se cada vez mais suscetível a erros e inconsistências.

---

# 💡 Solução

A Inventory API foi desenvolvida para centralizar todo esse fluxo em uma única aplicação.

Entre as principais funcionalidades estão:

- cadastro de dispositivos Apple;
- autenticação utilizando JWT;
- controle de acesso por perfis (**ADMIN** e **EMPLOYEE**);
- gerenciamento completo das movimentações de estoque;
- dashboard com indicadores do negócio;
- rastreabilidade das operações realizadas pelos usuários;
- documentação automática via Swagger;
- migrações versionadas com Flyway;
- ambiente totalmente containerizado utilizando Docker.

---

# 🚀 Tecnologias utilizadas

| Categoria | Tecnologias |
|-----------|-------------|
| Linguagem | Java 21 |
| Framework | Spring Boot |
| Segurança | Spring Security + JWT |
| Banco de Dados | PostgreSQL |
| Persistência | Spring Data JPA / Hibernate |
| Migrações | Flyway |
| Testes | JUnit 5 + Mockito |
| Documentação | Swagger / OpenAPI |
| Containerização | Docker + Docker Compose |
| Deploy | Render |
| Build | Maven |

---