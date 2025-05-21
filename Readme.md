## DESAFIO-API  

  API desenvolvida em Java 17 com Spring Boot para avaliação técnica no processo seletivo PSS 02/2025/SEPLAG. 

## ⚠️ Pré-requisitos

1. **Docker Desktop instalado**  
   - Certifique-se de ter o Docker Desktop instalado e em execução.  
   - [Download Docker Desktop](https://www.docker.com/products/docker-desktop)  

2. **Configurar o arquivo `hosts`**  
   - Adicione a seguinte linha ao arquivo `hosts` do seu sistema:  
     ```
     127.0.0.1 minio
     ```  
   - **Como editar o arquivo `hosts`:**  
     - **Windows:** `C:\Windows\System32\drivers\etc\hosts` (edite como administrador).  
     - **Linux/Mac:** `/etc/hosts` (use `sudo` para editar).

## 🚀 Contruir a Aplicação  
 -  Subir os Containers
  ```sh 
  docker compose up --build -d  # ou `docker-compose up --build -d` (para versões mais antigas do Compose)
  ```

## 📄 Documentação da API
O Swagger pode ser acessado em:🔗 http://localhost:8080/swagger-ui/index.html

## 🛢️ Acessando o Banco de Dados PostgreSQL dentro do Container
  1. **Execute o comando para entrar no container:**
```sh
docker exec -it postgres-desafio bash 
```
  2. **Dentro do container, acesse o banco de dados:**
```sh
psql -U admin -d desafiotech_bd
```

  3. **Banco de dados populado**  
   - É necessário ter dados nas tabelas, Para testar endpoints especificos de Consulta por parte de Nome e Consulta de servidores por unidade.
   - Utilize o script `script_teste.sql` (disponível na raiz do projeto) para inserir registros manualmente.  

  ## ⚠️ Atenção: Gerenciamento de IDs no Banco de Dados  

  Este sistema utiliza **sequências automáticas** para geração de IDs no banco de dados PostgreSQL. É importante observar que:  
    - Quando um registro é excluído:
    - O ID excluído **não é reutilizado** automaticamente
    - A sequência **continua incrementando** a partir do último valor.
  ### Recomendações:
  - Nunca assuma que um ID específico existe no banco
  - Para operações que referenciam IDs existentes, sempre:
    - Consulte primeiro o banco de dados
    - Use os endpoints de listagem para obter IDs válidos
    - Trate casos onde o ID pode não existir

## 🔑 Credenciais

(PostgreSQL)
 - URL: jdbc:postgresql://postgres-desafio:5432/desafiotech_bd
 - Usuário: admin
 - Senha: admin

(MinIO)
 - URL de acesso: http://minio:9000
 - Access Key: minioadmin
 - Secret Key: minioadmin
 - Bucket Padrão: minhas-imagens.

(Spring Security)  
 - Usuário: admin  
 - senha: admin123  


## 🔗 Endpoints - Disponivel para testar pelo Swagger.
📌 FotoMinio
 - POST /api/fotos/upload - Upload de foto - recupera link temporario no Response.
Parâmetro: file (formato multipart/form-data).

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
- Java	17  
- Spring Boot	3.4.3  
- PostgreSQL	postgres:latest  
- MinIO	minio/minio:latest  
- Docker Compose	3.x ou superior	Orquestração dos containers
- Maven	Wrapper (./mvnw)
- Swagger/OpenAPI	2.8.5 (springdoc-openapi)	Documentação interativa da API
