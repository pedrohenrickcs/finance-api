# finance-api

API de finanças pessoais em **Kotlin + Spring Boot + PostgreSQL**. Projeto de estudo de backend.

## Status

- [x] **Fase 1:** base do projeto, Docker Compose com Postgres, Flyway, CRUD de contas e categorias
- [ ] Fase 2: autenticação (JWT) e isolamento de dados por usuário
- [ ] Fase 3: transações, filtros, paginação
- [ ] Fase 4: relatórios e regras de negócio
- [ ] Fase 5: testes, logs, Swagger
- [ ] Fase 6: front, CI e deploy

## Pré-requisitos

- JDK 21 (o Gradle também baixa um toolchain 21 sozinho, se faltar)
- Docker com Docker Compose
- IntelliJ IDEA (Community serve)

## Como rodar

```bash
docker compose up -d     # sobe o Postgres em localhost:5432
./gradlew bootRun        # sobe a API em http://localhost:8080
```

No primeiro start, o Flyway aplica `V1__create_accounts_and_categories.sql`.
Teste com o arquivo `requests.http` (IntelliJ) ou com curl:

```bash
curl -X POST localhost:8080/accounts \
  -H 'Content-Type: application/json' \
  -d '{"name":"Nubank","type":"CHECKING","initialBalance":1500.00}'

curl localhost:8080/accounts
```

Para zerar o banco: `docker compose down -v`.

## Endpoints (Fase 1)

| Método | Rota               | Descrição                          |
|--------|--------------------|------------------------------------|
| GET    | `/accounts`        | lista contas                       |
| GET    | `/accounts/{id}`   | busca uma conta                    |
| POST   | `/accounts`        | cria conta                         |
| PUT    | `/accounts/{id}`   | atualiza conta                     |
| DELETE | `/accounts/{id}`   | remove conta                       |
| GET    | `/categories?type=`| lista categorias (filtro opcional) |
| GET/POST/PUT/DELETE | `/categories[/{id}]` | idem contas          |

Tipos de conta: `CHECKING`, `WALLET`, `CREDIT_CARD`. Tipos de categoria: `INCOME`, `EXPENSE`.

Erros seguem o formato `ProblemDetail` (RFC 9457): 400 validação, 404 não encontrado, 409 duplicado.

## Estrutura

```
src/main/kotlin/com/example/finance/
├── account/    Entity, Repository, Service, Controller, DTOs
├── category/   idem
└── common/     tratamento global de erros
src/main/resources/
├── application.yml
└── db/migration/   migrations do Flyway (nunca edite uma migration já aplicada)
```

Fluxo: `Controller` (HTTP + validação) → `Service` (regras + transação) → `Repository` (banco).

## Decisões que valem estudar

- **`ddl-auto: validate`**: quem cria o schema é o Flyway, o Hibernate só confere.
- **`BigDecimal` + `NUMERIC(15,2)`** para dinheiro, nunca `Double`.
- **DTOs separados das entidades**: a API não expõe o modelo do banco.
- **`open-in-view: false`**: evita lazy loading escondido na camada web.
- **Constraint no banco** (`UNIQUE`, `CHECK`) além da validação na API: o banco é a última linha de defesa.

## Exercícios para fechar a Fase 1

1. Crie a `V2__...sql` adicionando a coluna `color` em `categories` e ajuste entidade/DTOs.
2. Faça a criação de categoria duplicada retornar uma mensagem mais clara que o 409 genérico.
3. Adicione `GET /accounts?type=` filtrando por tipo.
4. Escreva um teste de service com MockK (as dependências de teste entram na Fase 5).

## Próximo passo (Fase 2)

Criar a tabela `users`, adicionar `user_id` em `accounts` e `categories` por migration (V3, V4...) e proteger as rotas com JWT.
