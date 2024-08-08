
# HACKTHON

**Pós 2ADJT**

Aluno: Gabriel Fellone  
RM: 350771

---

## Desenho e Documentação do Projeto

- [**Desenho da solução** ](https://miro.com/app/board/uXjVKrPLMaQ=/?share_link_id=624660829579)


### Informações para Testes

- **Repositório do Código:** [https://github.com/gabrielfellone/hackthon](https://github.com/gabrielfellone/hackthon)
- **Collection Insomnia:** [hackthon.json](https://1drv.ms/u/c/c0bcf29f7faf1b0e/ETdYL-cFE2pBhYIPNyQdM1QBrc-p3-KwKX5Hf8zPkB2KWw?e=fWh878)

**Observação:** O usuário padrão para autenticação já foi criado na execução da aplicação `data.sql` na pasta `resources`, portanto, não é necessário criar um novo. Para testar as APIs, é necessário obter o token e usá-lo como Bearer.

### Criar o Banco de Dados

1. **Baixar a Imagem do PostgreSQL:**
    ```bash
    docker pull postgres
    ```

2. **Executar o Contêiner PostgreSQL:**
    ```bash
    docker run -d --name postgre-fiap -e POSTGRES_PASSWORD=102030 -p 5432:5432 postgres:latest
    ```



## APIs

### 0 - Autenticação

**Descrição:** Gera um token para autenticação nas APIs subsequentes.

- **Método:** `POST`
- **Endpoint:** `localhost:8080/api/autenticacao`
- **Corpo da Requisição:**
  ```json
  {
    "username": "adj2",
    "password": "adj@1234"
  }
  ```
- **Resposta Esperada:**
  - **200 OK:** Retorna um token de autenticação.
  - **401 Unauthorized:** Credenciais inválidas.

---

### 1 - Registro de Cliente

**Descrição:** Cria um novo cliente na base de dados.

- **Método:** `POST`
- **Endpoint:** `localhost:8080/api/cliente`
- **Corpo da Requisição:**
  ```json
  {
    "nome": "Gabriel Fellone",
    "login": "Gabriel",
    "senha": "12345",
    "email": "gbfellone@fiap.com.br",
    "cidade": "São Paulo",
    "estado": "SP",
    "rua": "Avenida Paulista",
    "cep": "01311-000",
    "pais": "Brasil",
    "cpf": "123.456.789-00",
    "telefone": "+551198765-4321"
  }
  ```

- **Validações que foram feitas:**
  - Cliente não pode ter mais que 2 cartões
  - Apenas 1 número de cartão por cliente 
  
- **Resposta Esperada:**
  - **200 OK:** Cliente criado com sucesso.
  - **403 Bad Request:** Número máximo de cartões atingidos.
  - **401 Unauthorized:** Token de autenticação inválido.
  - **500:** Erro na logica.


---

### 2 - Gerar Cartão

**Descrição:** Gera um novo cartão de crédito para um cliente.

- **Método:** `POST`
- **Endpoint:** `localhost:8080/api/cartao`
- **Corpo da Requisição:**
  ```json
  {
    "cpf": "123.456.789-00",
    "limite": 5000,
    "numero": "1234-5678-9012-3456",
    "cvv": "123",
    "dataValidade": "12/25"
  }
  ```

- **Validações que foram feitas:**
  - Limite de cartão de credito
  - Validade do cartão
  - Valida cliente CPF
  - Valida código

- **Resposta Esperada:**
  - **200 OK:** Sucesso e devolve chave cartão.
  - **401 Unauthorized:** Token de autenticação inválido.
  - **402 Payment Required:** Estorou limite ou cartão inválido.

---

### 3 - Registro de Pagamento

**Descrição:** Registra um pagamento realizado com um cartão de crédito.

- **Método:** `POST`
- **Endpoint:** `localhost:8080/api/pagamentos`
- **Corpo da Requisição:**
  ```json
  {
    "cpf": "123.456.789-00",
    "valor": 2000,
    "numero": "1234-5678-9012-3456",
    "cvv": "123"
  }
  ```
- **Resposta Esperada:**
  - **200 OK:** Pagamento registrado com sucesso.
  - **400 Bad Request:** Dados inválidos.
  - **401 Unauthorized:** Token de autenticação inválido.
  - **402 Payment Required:** Erro de limite de crédito.

---

### 4 - Consulta Pagamento por Cliente

**Descrição:** Consulta todos os pagamentos realizados por um cliente específico.

- **Método:** `GET`
- **Endpoint:** `localhost:8080/api/pagamentos/cliente/{cpf}`
- **Parâmetros na URL:**
  - `{cpf}`: CPF do cliente para consultar os pagamentos.
- **Resposta Esperada:**
  - **200 OK:** Lista de pagamentos do cliente com detalhes como valor, descrição, método de pagamento e status.
  - **401 Unauthorized:** Token de autenticação inválido.

---

## Exemplos de Uso

### Autenticação

**Requisição:**
```bash
curl -X POST localhost:8080/api/autenticacao -H "Content-Type: application/json" -d '{"username":"adj2","password":"adj@1234"}'
```

### Registro de Cliente

**Requisição:**
```bash
curl -X POST localhost:8080/api/cliente -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{
  "nome": "Gabriel Fellone",
  "login": "Gabriel",
  "senha": "12345",
  "email": "gbfellone@fiap.com.br",
  "cidade": "São Paulo",
  "estado": "SP",
  "rua": "Avenida Paulista",
  "cep": "01311-000",
  "pais": "Brasil",
  "cpf": "123.456.789-00",
  "telefone": "+551198765-4321"
}'
```

### Gerar Cartão

**Requisição:**
```bash
curl -X POST localhost:8080/api/cartao -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{
  "cpf": "123.456.789-00",
  "limite": 5000,
  "numero": "1234-5678-9012-3456",
  "cvv": "123",
  "dataValidade": "12/25"
}'
```

### Registro de Pagamento

**Requisição:**
```bash
curl -X POST localhost:8080/api/pagamentos -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{
  "cpf": "123.456.789-00",
  "valor": 2000,
  "numero": "1234-5678-9012-3456",
  "cvv": "123"
}'
```

### Consulta Pagamento por Cliente

**Requisição:**
```bash
curl -X GET localhost:8080/api/pagamentos/cliente/123.456.789-00 -H "Authorization: Bearer <token>"
```

---

## Arquitetura do Código (Resumida)

- **`securty`**  
  Contém a lógica de autenticação com o Spring Securty, JWT, etc.

- **`controller`**  
  Contém as classes responsáveis pelas chamadas de API, camada de aplicação. Inclui endpoints, requests e responses.

- **`domain`**  
  Define as classes de entidades e domínios do sistema.

- **`exceptions`**  
  Define exceções personalizadas.

- **`repository`**  
  Inclui classes/interfaces/entidades relacionadas ao banco de dados.

- **`services`**  
  Implementa classes de serviços para manipular os domains e aplicar regras de negócio.
