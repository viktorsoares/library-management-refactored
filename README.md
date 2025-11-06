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

#  Pré-requisitos

- **IntelliJ IDEA** (preferencialmente a versão Ultimate, mas a Community também funciona)
- **Java 21** instalado e configurado no sistema
- **Git** (se for clonar o projeto do GitHub)
- **Maven** (dependendo do projeto)

---

#  Passo a passo para configurar o projeto no IntelliJ

## 1. Clonar o projeto

Se ainda não fez isso:

```
git clone https://github.com/seu-usuario/Library-Management-Refactored.git
cd Library-Management-Refactored
```

## 2. Abrir o projeto no IntelliJ

1. Abra o **IntelliJ IDEA**  
2. Vá em **File > Open**  
3. Selecione a pasta do projeto **Library-Management-Refactored**  
4. O IntelliJ vai detectar o projeto Maven e importar automaticamente
---

## 3. Configurar o SDK para Java 21

1. Vá em **File > Project Structure > Project**  
2. Em **Project SDK**, clique em **Add SDK** e selecione **Java 21**  
3. Em **Project language level**, escolha:  
   `21 - (Preview) Pattern Matching for switch, Record Patterns, etc.`
---

## 4. Verificar o pom.xml

Certifique-se de que o `pom.xml` está configurado para Java 21:

```xml
<properties>
    <java.version>21</java.version>
</properties>
```
##  5. Rodar a aplicação

1. Localize a classe principal (geralmente em `src/main/java/.../LibraryManagementApplication.java`)  
2. Clique com o botão direito e selecione **Run 'LibraryManagementApplication'**  
3. Aguarde o servidor iniciar — o console indicará algo como:  
   `Started LibraryManagementApplication in X seconds (JVM running for X.X)`  

---
##  6. Acessar o console H2

Se o projeto estiver configurado corretamente, o console H2 estará disponível em:

```
http://localhost:8080/h2-console
```
Use as credenciais padrão:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `sa`
- **Senha:** *(em branco)*

>  Caso o console não esteja habilitado, verifique no `application.properties` se há a linha:
> ```
> spring.h2.console.enabled=true
> ```
