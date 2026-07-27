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

A **JardelCell Inventory API** é uma aplicação backend desenvolvida para gerenciar o estoque de dispositivos da Apple da empresa JardelCell.

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

À medida que o volume de produtos aumentava, esse processo tornava-se cada vez mais suscetível a erros e inconsistências. Além disso, não existia uma forma simples de responder perguntas estratégicas como "quantos aparelhos estão em reparo?", "qual o investimento atual no estoque?" ou "quem realizou determinada movimentação?".

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

# 🏗️ Arquitetura

A **JardelCell Inventory API** foi projetada seguindo uma arquitetura modular, organizando o sistema por contextos de negócio ao invés de camadas técnicas.

Essa abordagem aumenta a coesão entre componentes relacionados, reduz o acoplamento entre funcionalidades e facilita a manutenção e evolução do sistema conforme novas necessidades surgem.

Durante o desenvolvimento foram adotados princípios de **Clean Code**, **SOLID** e separação clara de responsabilidades, buscando reproduzir práticas adotadas em aplicações corporativas reais.

---

## Estrutura geral

A aplicação está organizada em módulos independentes.

```text
src
├── auth
├── common
├── config
├── dashboard
├── inventory
├── product
├── security
└── user
```

Cada módulo concentra apenas os componentes necessários para sua responsabilidade de negócio, evitando dependências desnecessárias entre domínios.

Dentro de cada módulo encontram-se componentes como:

- Controller
- DTOs
- Entity
- Mapper
- Repository
- Service
- UseCase

Essa organização permite que cada domínio evolua de forma independente, mantendo o restante da aplicação estável.

---

## Fluxo da requisição

Toda requisição segue um fluxo bem definido desde sua autenticação até a persistência dos dados.

```
Cliente
    │
    ▼
Spring Security (JWT)
    │
    ▼
Controller
    │
    ▼
   DTO
    │
    ▼
Use Case
    │
    ▼
Repository
    │
    ▼
PostgreSQL
```

Cada camada possui uma responsabilidade específica:

| Camada     | Responsabilidade |
|------------|------------------|
| Security   | Autenticação e autorização da requisição |
| Controller | Receber e validar requisições HTTP |
| Use Case   | Executar regras de negócio |
| Repository | Persistência dos dados |
| Database   | Armazenamento permanente |

Essa separação reduz o acoplamento entre as camadas e facilita testes unitários, manutenção e evolução do software.

---

# 📦 Principais componentes

Os módulos abaixo concentram a maior parte das regras de negócio da aplicação e representam os principais fluxos existentes no sistema.

Cada componente possui responsabilidades bem definidas e foi desenvolvido buscando alta coesão e baixo acoplamento.

---

## 🍎 Product

O módulo **Product** representa o núcleo do estoque.

Cada produto cadastrado possui informações suficientes para permitir sua identificação individual, rastreabilidade e controle financeiro dentro da empresa.

Entre os dados armazenados destacam-se:

- modelo do dispositivo;
- número de série;
- IMEI;
- capacidade;
- cor;
- condição do aparelho;
- preço de compra;
- preço de venda;
- situação atual no estoque.

Além do cadastro dos dispositivos, este módulo também concentra as regras responsáveis por impedir inconsistências durante operações de movimentação, garantindo que apenas produtos em estados válidos possam participar de determinados fluxos da aplicação.

![Diagrama de Produto](docs/images/product.png)

---

## 📦 Inventory Movement

Toda alteração realizada sobre um produto gera um registro permanente de movimentação.

Esse módulo foi desenvolvido para garantir rastreabilidade completa sobre o histórico de cada dispositivo.

Cada movimentação registra:

- produto movimentado;
- tipo da movimentação;
- usuário responsável;
- data e hora;
- observações quando necessárias.

Entre os tipos de movimentação implementados estão:

- Entrada em estoque;
- Venda;
- Troca;
- Reserva;
- Cancelamento da reserva;
- Envio para reparo;
- Retorno do reparo;
- Descarte.

Essa abordagem permite reconstruir todo o histórico operacional de qualquer aparelho presente no sistema.

![Diagrama de Movimentação de Inventário](docs/images/inventory-movements.png)

---

## 🔐 Segurança

O controle de acesso da aplicação é realizado utilizando **Spring Security** e autenticação baseada em **JWT (JSON Web Token)**.

Após a autenticação, cada requisição protegida passa por um filtro responsável por validar o token recebido e reconstruir o contexto de autenticação da aplicação.

Os usuários são classificados em dois perfis:

- **ADMIN**
- **EMPLOYEE**

As permissões são controladas diretamente pelo Spring Security, restringindo o acesso às funcionalidades conforme o perfil autenticado.

Além disso, a aplicação adota boas práticas de segurança como:

- senhas armazenadas utilizando BCrypt;
- autenticação stateless;
- proteção contra acesso não autorizado;
- validação da assinatura e expiração dos tokens JWT;
- autorização baseada em Roles (ROLE_ADMIN e ROLE_EMPLOYEE);
- reconstrução do SecurityContext a cada requisição autenticada.

![Diagrama de Segurança](docs/images/security.png)

---

# 📊 Dashboard

A aplicação disponibiliza indicadores estratégicos para apoio à tomada de decisão, permitindo visualizar informações como:

- estoque disponível;
- aparelhos reservados;
- aparelhos com defeito;
- aparelhos vendidos;
- investimento atual em estoque;
- faturamento obtido com vendas;
- despesas de produtos defeituosos e reparos;

Essas informações são calculadas dinamicamente a partir das movimentações registradas no sistema.

---

# ☁️ Deploy

A aplicação encontra-se publicada utilizando a plataforma Render.

O deploy é realizado através de:

- Docker
- Docker Compose
- PostgreSQL
- Variáveis de ambiente
- Build automatizado a partir do GitHub

Além disso, o projeto possui ambientes separados para desenvolvimento e produção utilizando Spring Profiles.

---

