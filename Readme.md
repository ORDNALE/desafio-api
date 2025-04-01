  DESAFIO-API  

  
  API desenvolvida em Java 17 com Spring Boot para avaliação técnica no processo seletivo PSS 02/2025/SEPLAG.    


📌 Dados de Inscrição  
Inscrição: 8831  
  Nome: ELANDRO SOARES MAGALHAES  
E-mail: elandro10@outlook.com  

📢 Importante!  
⚠️ Para o endpoint especifico (ServidoresConsulta) faça as consultas por endereço-funcional e consultar os servidores efetivos lotados, é necessário ter registros nas tabelas.  
⚠️ Para popular o manualmente, utilize o script SQL disponível na raiz do projeto, arquivo -> `script_teste.sql`  


⚙️ Configuração e Execução
  1️⃣ Clonar o Repositório
```sh
git clone <https://github.com/ORDNALE/desafio-api.git>
cd desafio-api
```

  2️⃣ Subir os Containers do Banco e MinIO

Certifique-se de ter **Docker** e **Docker Compose** instalados. Em seguida, execute:
```sh
docker compose up -d  # ou `docker-compose up -d` (versões mais antigas)
```
Isso iniciará os containers do PostgreSQL e do MinIO.


📄 Documentação da API
O Swagger pode ser acessado em:🔗 http://localhost:8080/swagger-ui.html


🔑 Credenciais

(PostgreSQL)
 - URL: jdbc:postgresql://postgres:5432/desafiotech_bd
 - Usuário: admin
 - Senha: admin

(MinIO)
 - URL de acesso: http://minio:9000
 - Access Key: minioadmin
 - Secret Key: minioadmin
 - Bucket Padrão: minhas-imagens.

Spring Security
Usuário: admin
senha: admin123 

🛢️ Acessando o Banco de Dados PostgreSQL dentro do Container
  1. Execute o comando para entrar no container:
```sh
docker exec -it postgres_container bash
```
  2. Dentro do container, acesse o banco de dados:
```sh
psql -U admin -d desafiotech_bd
```    

🔗 Endpoints - Disponivel para testar pelo Swagger.
# Requisitos Específicos!
📌 FotoMinio
 - POST /api/fotos/upload - Upload de foto - recupera link temporario no Response.
Parâmetro: file (formato multipart/form-data)

📌 ServidoresConsulta
 - GET /api/listar-servidores/{unidadeId} - Listar servidores efetivos por unidade.
 - GET /api/listar-servidores/endereco-funcional - Buscar endereço funcional por nome do  servidor.

📌Pessoa
 - POST /api/pessoas - Criar uma nova pessoa.
 - PUT /api/pessoas/{id} - Atualizar uma pessoa existente.
 - GET /api/pessoas/{id} - Obter uma pessoa pelo ID.
 - GET /api/pessoas?page={page}&size={size} - Listar todas as pessoas (paginado).
(Parâmetros opcionais: page e size para paginação).
 - DELETE /api/pessoas/{id} - Excluir uma pessoa.

📌ServidorEfetivo
 - POST /api/servidores-efetivos - Criar um novo servidor efetivo.
 - GET /api/servidores-efetivos?page={page}&size={size} - Listar todos os servidores
   efetivos (paginado).
 - GET /api/servidores-efetivos/{id} - Buscar servidor efetivo por ID.
 - PUT /api/servidores-efetivos/{id} - Atualizar um servidor efetivo.
 - DELETE /api/servidores-efetivos/{id} - Excluir um servidor efetivo.

📌ServidorTemporario
 - POST /api/servidores-temporarios - Criar um novo servidor temporário.
 - GET /api/servidores-temporarios?page={page}&size={size}&sort={sort}&nome={nome} -      Listar todos os servidores temporários (paginado, com filtro opcional por nome).
 - GET /api/servidores-temporarios/{id} - Buscar servidor temporário por ID.
 - PUT /api/servidores-temporarios/{id} - Atualizar um servidor temporário.
 - DELETE /api/servidores-temporarios/{id} - Excluir um servidor temporário.

📌Unidade
 - POST /api/unidades - Criar uma nova unidade.
 - GET /api/unidades/{id} - Buscar unidade por ID.
 - GET /api/unidades?page={page}&size={size}&sort={sort} - Listar todas as unidades
   (paginado).
 - PUT /api/unidades/{id} - Atualizar uma unidade existente.
 - DELETE /api/unidades/{id} - Excluir uma unidade.

📌Lotacao
 - POST /api/lotacoes - Criar uma nova lotação
 - GET /api/lotacoes/{id} - Buscar lotação por ID.
 - GET /api/lotacoes?page={page}&size={size} - Listar todas as lotações (paginado).
   (Parâmetros opcionais: page e size)
 - PUT /api/lotacoes/{id} - Atualizar uma lotação existente.
 - DELETE /api/lotacoes/{id} - Excluir uma lotação.

🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **PostgreSQL** (Banco de dados)
- **MinIO** (Armazenamento de imagens)
- **Docker e Docker Compose**
- **Maven** (Gerenciamento de dependências)
- **Swagger/OpenAPI** (Documentação da API)
