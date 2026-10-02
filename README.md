# RestAssured + Java + JUnit 5 — reqres.in

Projeto de automação de testes de **API** (sem navegador nenhum) usando **RestAssured + Java + JUnit 5**, rodando contra a [API pública do reqres.in](https://reqres.in/) — uma API REST gratuita, feita especificamente para prática de automação.

> Projeto de portfólio, feito para demonstrar organização de um framework de testes de API do zero. Sinta-se à vontade para clonar, rodar e usar como referência.

---

## Stack

| Camada | Ferramenta |
|---|---|
| Linguagem | Java 17 |
| Build | Maven (com Maven Wrapper — não precisa instalar Maven) |
| Cliente HTTP / DSL de teste | RestAssured |
| Runner / Assertions | JUnit 5 + AssertJ |
| Conversão JSON ↔ objeto | Jackson |
| CI | GitHub Actions |

---

## Estrutura do projeto

```
selenium-restassured-reqres/
├── src/test/java/com/luisrew/reqres/
│   ├── config/ApiConfig.java        # URL base e configuração compartilhada das requisições
│   ├── models/                      # POJOs que representam o JSON de request/response
│   ├── services/                    # "Service Objects": um método por endpoint da API
│   └── tests/                       # os testes de fato (JUnit 5)
├── .github/workflows/ci.yml
├── pom.xml
└── mvnw / mvnw.cmd                  # Maven Wrapper
```

---

## Como rodar

Pré-requisito: **Java 17+** instalado. Não precisa instalar Maven — o wrapper cuida disso.

```bash
# Windows
./mvnw.cmd test

# Linux / Mac
./mvnw test
```

Isso faz requisições reais contra `https://reqres.in/api` e roda os 10 testes. Não precisa de navegador, Chrome, nem nada visual — é só HTTP.

---

## Por que RestAssured (e por que testes de API, especificamente)

Esse é o quarto projeto de uma série de portfólio; os três anteriores (Selenium, Playwright, Cypress) testam a camada de **interface**. Esse aqui testa uma camada abaixo: o **contrato da API** diretamente.

Isso importa na prática porque testes de API são, em geral:
- **Muito mais rápidos** — sem renderização de página, sem esperar elementos aparecerem. A suíte inteira aqui roda em segundos.
- **Mais estáveis** — não quebram por causa de uma mudança de CSS ou de um elemento que demorou pra carregar.
- **Mais próximos da lógica de negócio** — validam exatamente o que a API promete entregar (status code, formato dos dados, regras de validação), sem depender de como isso é desenhado na tela.

Por isso é comum, em suítes de teste maduras, ter uma pirâmide com **muito mais testes de API do que de UI**: a UI cobre os fluxos críticos de ponta a ponta, e a API cobre a lógica de negócio em profundidade, de forma barata.

---

## Como os testes estão organizados

O padrão usado aqui é o de **Service Object** — equivalente ao Page Object Model dos projetos de UI, só que para endpoints em vez de páginas:

```java
// services/UserService.java
public Response getUser(int id) {
    return given()
            .spec(ApiConfig.requestSpec())
            .when()
            .get("/users/{id}", id);
}
```

```java
// tests/SingleUserTest.java
Response response = userService.getUser(2);

assertThat(response.statusCode()).isEqualTo(200);
SingleUserResponse body = response.as(SingleUserResponse.class);
assertThat(body.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
```

O teste não sabe (nem precisa saber) qual é a URL exata, o verbo HTTP ou como a requisição é montada — isso fica isolado no Service. Se a API mudar um endpoint, você ajusta em um lugar só.

### O que cada teste cobre

| Classe | Endpoint | Cenários |
|---|---|---|
| `ListUsersTest` | `GET /users` | Listagem paginada |
| `SingleUserTest` | `GET /users/{id}` | Usuário existente (200) e inexistente (404) |
| `CreateUserTest` | `POST /users` | Criação (201) |
| `UpdateUserTest` | `PUT /users/{id}` | Atualização (200) |
| `DeleteUserTest` | `DELETE /users/{id}` | Remoção (204) |
| `RegisterTest` | `POST /register` | Registro válido (200) e sem senha (400) |
| `LoginTest` | `POST /login` | Login válido (200) e sem senha (400) |

---

## Aulinha: como criar um projeto desse do zero, sem IA

### 1. Instale o Java (JDK)

Baixe o **JDK 17+** em [adoptium.net](https://adoptium.net/) e confirme com `java -version`.

### 2. Instale o Git

Baixe em [git-scm.com](https://git-scm.com/downloads) e configure:
```bash
git config --global user.name "Seu Nome"
git config --global user.email "seu-email@exemplo.com"
```

### 3. Crie a estrutura do projeto Maven

Pela IDE (`New Project > Maven`) ou na mão:
```
meu-projeto/
├── pom.xml
└── src/test/java/
```

### 4. Adicione as dependências no `pom.xml`

Dentro de `<dependencies>`, em [mvnrepository.com](https://mvnrepository.com/):
- `io.rest-assured:rest-assured` — o cliente HTTP com a DSL de teste
- `org.junit.jupiter:junit-jupiter` — framework de testes
- `com.fasterxml.jackson.core:jackson-databind` — para converter JSON em objetos Java automaticamente
- `org.assertj:assertj-core` — assertions mais legíveis (opcional, dá pra usar só o JUnit)

### 5. Entenda a DSL do RestAssured

A sintaxe segue o padrão **given / when / then** (dado um contexto, quando eu faço uma ação, então eu espero um resultado):

```java
given()
    .baseUri("https://reqres.in/api")
    .contentType(ContentType.JSON)
.when()
    .get("/users/2")
.then()
    .statusCode(200);
```

Nesse projeto, em vez de encadear `.then()` direto, pegamos a `Response` e usamos AssertJ — é uma escolha de estilo, as duas formas são válidas e bem comuns no mercado.

### 6. Crie um POJO para o corpo da resposta

```java
public class User {
    private int id;
    private String email;
    // getters e setters
}
```

O RestAssured (via Jackson) converte o JSON da resposta direto para essa classe com `response.as(User.class)` — sem você escrever nenhum parser manual.

### 7. Escreva o teste

```java
@Test
void buscaUsuarioExistente() {
    Response response = given()
            .baseUri("https://reqres.in/api")
        .when()
            .get("/users/2");

    assertThat(response.statusCode()).isEqualTo(200);
}
```

### 8. Rode

```bash
mvn test
```

(Sem Maven instalado? Veja a seção de wrapper no README do projeto Selenium desta mesma série de portfólio — o processo é idêntico.)

### 9. Crie o repositório no GitHub e suba o projeto

```bash
git init
git add .
git commit -m "primeiro commit"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/nome-do-repo.git
git push -u origin main
```

### 10. CI (GitHub Actions)

Crie `.github/workflows/ci.yml` com um job que instala o Java e roda `mvn test` a cada push — assim como nos outros projetos desta série.
