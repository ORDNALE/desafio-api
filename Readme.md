# Desafio API

## Dados de Inscrição


## Descrição do Projeto
Este projeto é uma API desenvolvida em **Java 17** com **Spring Boot** para avaliação técnica no processo seletivo **PSS 02/2025/SEPLAG** .
A API utiliza banco de dados **PostgreSQL** e armazenamento **MinIO**, ambos containerizados via Docker.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **PostgreSQL** (Banco de dados)
- **MinIO** (Armazenamento de imagens)
- **Docker e Docker Compose**
- **Maven** (Gerenciamento de dependências)
- **Swagger/OpenAPI** (Documentação da API)

## Configuração e Execução
### 1. Clonar o Repositório
```sh
git clone <https://github.com/ORDNALE/desafio-api.git>
cd desafio-api
```

### 2. Subir os Containers do Banco e MinIO

Certifique-se de ter **Docker** e **Docker Compose** instalados. Em seguida, execute:
```sh
docker compose up -d  # ou `docker-compose up -d` (versões mais antigas)
```
Isso iniciará os containers do PostgreSQL e do MinIO.

# Documentação
O Swagger deve estar acessível em **http://localhost:8080/swagger-ui.html**


## 3. Credenciais
(PostgreSQL)
 - URL: jdbc:postgresql://postgres:5432/desafiotech_bd
 - Usuário: admin
 - Senha: admin

(MinIO)
 - URL de acesso: http://minio:9000
 - Access Key: minioadmin
 - Secret Key: minioadmin
 - Bucket Padrão: minhas-imagens.


### 4.Acessando o banco de dados PostgreSQL dentro do container:

1. Execute o comando para entrar no container:
```sh
docker exec -it postgres_container bash
```
2. Dentro do container, acesse o banco de dados:
```sh
psql -U admin -d desafiotech_bd
```
⚠️Caso queira popular o banco de dados manualmente, há um script SQL disponível no arquivo na raiz do projeto - `script_teste.sql`.     

### 5.URLs para os endpoints.
# PessoaController
 - POST /api/pessoas - Criar uma nova pessoa.
 - PUT /api/pessoas/{id} - Atualizar uma pessoa existente.
 - GET /api/pessoas/{id} - Obter uma pessoa pelo ID.
 - GET /api/pessoas?page={page}&size={size} - Listar todas as pessoas (paginado).
(Parâmetros opcionais: page e size para paginação).
 - DELETE /api/pessoas/{id} - Excluir uma pessoa.

# ServidorEfetivoController
 - POST /api/servidores-efetivos - Criar um novo servidor efetivo.
 - GET /api/servidores-efetivos?page={page}&size={size} - Listar todos os servidores
   efetivos (paginado).
 - GET /api/servidores-efetivos/{id} - Buscar servidor efetivo por ID.
 - PUT /api/servidores-efetivos/{id} - Atualizar um servidor efetivo.
 - DELETE /api/servidores-efetivos/{id} - Excluir um servidor efetivo.

# ServidorTemporarioController
 - POST /api/servidores-temporarios - Criar um novo servidor temporário.
 - GET /api/servidores-temporarios?page={page}&size={size}&sort={sort}&nome={nome} -   Listar todos os servidores temporários (paginado, com filtro opcional por nome).
 - GET /api/servidores-temporarios/{id} - Buscar servidor temporário por ID.
 - PUT /api/servidores-temporarios/{id} - Atualizar um servidor temporário.
 - DELETE /api/servidores-temporarios/{id} - Excluir um servidor temporário.

# UnidadeController
 - POST /api/unidades - Criar uma nova unidade.
 - GET /api/unidades/{id} - Buscar unidade por ID.
 - GET /api/unidades?page={page}&size={size}&sort={sort} - Listar todas as unidades
   (paginado).
 - PUT /api/unidades/{id} - Atualizar uma unidade existente.
 - DELETE /api/unidades/{id} - Excluir uma unidade.

# LotacaoController
 - POST /api/lotacoes - Criar uma nova lotação
 - GET /api/lotacoes/{id} - Buscar lotação por ID.
 - GET /api/lotacoes?page={page}&size={size} - Listar todas as lotações (paginado).
   (Parâmetros opcionais: page e size)
 - PUT /api/lotacoes/{id} - Atualizar uma lotação existente.
 - DELETE /api/lotacoes/{id} - Excluir uma lotação.

### ENPOINTS ESPECIFICOS
# FotoController
 - POST /api/fotos/upload - Upload de foto - recupera link temporario no Response.
   Parâmetro: file (formato multipart/form-data)

# ServidoresConsultaController
⚠️ Para que esses endpoints retornem dados, é necessário inserir registros manualmente no banco de dados.
 - GET /api/listar-servidores/{unidadeId} - Listar servidores efetivos por unidade.
 - GET /api/listar-servidores/endereco-funcional - Buscar endereço funcional por nome do  servidor.

## Dependências Principais
O projeto inclui as seguintes dependências no `pom.xml`:
- **Spring Boot Starter Web** (Para criação da API)
- **Spring Boot Starter Security** (Autenticação e Autorização)
- **Spring Boot Starter Data JPA** (Persistência de dados)
- **PostgreSQL Driver** (Conexão com banco de dados)
- **MinIO SDK** (Integração com armazenamento)
- **JWT** (Autenticação com tokens JWT)
- **Swagger/OpenAPI** (Documentação da API)
- **Lombok** (Redução de boilerplate)
- **Spring Boot DevTools** (Facilidade no desenvolvimento)
- **Testes com JUnit e Mockito**

