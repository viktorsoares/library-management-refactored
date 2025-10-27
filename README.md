# Library Management Refactored

Este projeto é uma **refatoração do Library Management System original** disponível em [Library-Management-System-JAVA](https://github.com/harismuneer/Library-Management-System-JAVA). O objetivo é modernizar a aplicação, aplicando princípios de **Clean Architecture**, **padrões de projeto (GoF)** e boas práticas de **Spring Boot**.

## Objetivo do Projeto

* Transformar o código legado, altamente acoplado e procedural, em uma arquitetura modular e testável.
* Separar responsabilidades entre camadas: **Model** (domínio), **Repository** (persistência), **Service** (lógica de negócio), **Facade** (integração) e **Web** (REST API).
* Aplicar padrões de projeto como **Strategy** (políticas de empréstimo), **Factory** (criação de entidades), **Observer** (eventos de empréstimo/devolução) e **Facade** (API unificada).
* Melhorar **reusabilidade, manutenibilidade e testabilidade** do sistema.
* Substituir entrada/saída baseada em console por **endpoints REST**, utilizando Spring Boot.

## Funcionalidades

* Cadastro e gerenciamento de livros (`Book`), usuários (`Borrower`), empréstimos (`Loan`) e pedidos de reserva (`HoldRequest`).
* Controle de empréstimos e devoluções com política diferenciada por tipo de usuário.
* Emissão de eventos para notificação de ações importantes (ex.: livro emprestado ou devolvido).
* Persistência de dados usando **H2 Database** (em memória para desenvolvimento e testes).
* Console H2 disponível para inspeção do banco de dados em tempo de execução.

## Tecnologias e Padrões Utilizados

* **Java 21**
* **Spring Boot 3.3**
* **Spring Data JPA**
* **H2 Database** (em memória)
* **Clean Architecture / Hexagonal / DDD leve**
* **Padrões GoF**: Strategy, Factory, Observer, Facade

## Benefícios da Refatoração

* Código modular, com **baixo acoplamento e alta coesão**.
* **Testabilidade completa** usando mocks e testes unitários.
* **Extensibilidade facilitada**: novas estratégias, listeners ou adaptadores podem ser adicionados sem alterar o código existente.
* **Eliminação de classes “Deus”** e lógica procedural misturada com I/O.
