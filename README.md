# Projeto de uma api de banco
API REST desenvolvida com Spring Boot para estudo de conceitos de backend, com CRUD de Usuarios, validações em MySQL.
## 🚀 Tecnologias

- Java 27
- Spring Boot 4.1.1
- Spring Data JPA
- Spring Validation
- MySQL
- Lombok
- Docker / Docker Compose

## 📌 Funcionalidades

### User (`/User`)
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/User` | Cria um novo Usuario |

O projeto também conta com um `RestExceptionHandler` global para retornar respostas de erro padronizadas (validação, bad request, etc.).

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java 27+
- Maven (ou use o `./mvnw` incluso no projeto)
- Docker e Docker

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/AryelsonTevis/bank-api.git
   cd projetoapibanco
   ```

2. Crie um arquivo `.env` na raiz do projeto (baseado no `.env.example`) com suas credenciais de banco:
   ```
   DB_USERNAME=root
   DB_PASSWORD=sua_senha
   DB_URL=jdbc:mysql://localhost:3306/bank_api?createDatabaseIfNotExist=true
   ```

3. Suba o banco de dados MySQL com Docker:
   ```bash
   docker-compose up -d
   ```

4. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

5. A API estará disponível em `http://localhost:8080`.

## 📄 Licença

Este projeto está sob a licença MIT.