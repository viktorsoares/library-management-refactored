🧱 **1️⃣ Arquitetura adotada — Arquitetura em camadas com princípios de Clean Architecture / Hexagonal (Ports & Adapters)**

O projeto refatorado é um Spring Boot modular baseado em seis camadas principais, inspirado em Clean Architecture + Ports & Adapters, com um toque de DDD (Domain-Driven Design leve).

**🌐 Estrutura Geral**

```
com.example.library
│
├── model/              →  Entidades de domínio (Book, Loan, Borrower, HoldRequest)
├── repository/         →  Portas de saída (persistence adapters – Spring Data JPA)
├── service/            →  Regras de negócio (camada de aplicação)
│   ├── strategy/       →  Estratégias de empréstimo (Strategy pattern)
│   ├── listener/       →  Observadores de eventos (Observer pattern)
│   ├── events/         →  Eventos de domínio (ex: BookIssuedEvent)
│   └── LoanPolicyResolver.java
├── factory/            →  Fábrica de entidades (Factory pattern)
├── facade/             →  Fachada para unificar o acesso (Facade pattern)
└── web/                →  Controladores REST (camada de apresentação)
```

---

🧩 **Padrões de projeto (GoF) aplicados dentro da arquitetura**

| Camada               | Padrão GoF                   | Função                                                                 |
|---------------------|-----------------------------|------------------------------------------------------------------------|
| service.strategy     | Strategy                     | Define políticas de empréstimo diferentes por tipo de usuário.         |
| factory              | Factory Method / Abstract Factory | Cria entidades (Book, Loan, Borrower) sem acoplamento ao `new`.       |
| facade               | Facade                       | Expõe uma API unificada (LibraryFacade) para controllers e clientes.  |
| service.events + listener | Observer                 | Notifica eventos de empréstimo/devolução sem acoplar serviços diretamente. |
| (opcional) adapters.persistence.file | Template Method / Adapter | Poderia ser adicionado para persistência em arquivo (substituto de JPA). |

Essa combinação implementa os princípios de **reuso, baixo acoplamento e alta coesão**.

---

⚙️ **2️⃣ Como as camadas se comunicam**

Fluxo típico de uma requisição:

```
[Controller] → [Facade] → [Service] → [Repository]
                                  ↳ [Strategy] para política de empréstimo
                                  ↳ [Observer/Event] para notificação
```

**Exemplo:**

1. O `BookController` recebe `POST /api/books/{id}/issue`.
2. Chama `LibraryFacade.issue(...)`.
3. A fachada delega ao `BookService.issueBook(...)`.
4. O serviço aplica a `LoanPolicy` adequada (via `LoanPolicyResolver`), grava a transação (`LoanRepository`) e dispara um `BookIssuedEvent`.
5. Um `NotificationListener` reage ao evento e realiza ações (ex: notificar via e-mail, log etc.).

---

🧾 **6️⃣ Resumo da arquitetura adotada (para relatório)**

O sistema refatorado foi desenvolvido em **Spring Boot 3.3 (Java 21)**, adotando uma arquitetura **em camadas modular** com princípios de **Clean Architecture e Ports & Adapters**, favorecendo **reuso e manutenibilidade**.

As responsabilidades foram distribuídas entre camadas independentes:

- **Model** (domínio)  
- **Repository** (persistência)  
- **Service** (regras de negócio e aplicação de padrões GoF)  
- **Facade** (integração entre camadas)  
- **Web** (exposição REST)  

**Padrões de projeto aplicados:** Strategy, Factory, Observer, Facade.

Essa estrutura elimina duplicações, classes “Deus” e dependências rígidas, tornando o sistema **extensível e testável**.

