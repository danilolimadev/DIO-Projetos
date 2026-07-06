# Transcreve Despesas

API REST em Java para gerenciamento inteligente de despesas utilizando texto, voz e inteligência artificial.

A aplicação permite registrar, consultar e interpretar despesas através de comandos em linguagem natural. A solução utiliza **OpenAI integrada ao Spring AI** para compreender intenções do usuário, executar operações de negócio através de **Tool Calling**, transcrever áudios com **Whisper** e gerar respostas faladas utilizando **Text-to-Speech**.

---

## 📌 Visão geral

O objetivo do projeto é demonstrar uma arquitetura moderna combinando:

* API REST com Java e Spring Boot;
* Inteligência artificial generativa;
* processamento de linguagem natural;
* reconhecimento de voz;
* síntese de voz;
* autenticação JWT;
* persistência relacional;
* documentação OpenAPI.

O usuário pode interagir com a aplicação de diferentes formas:

* enviando uma despesa manualmente em JSON;
* digitando uma instrução em português;
* enviando um áudio com uma solicitação;
* solicitando consultas e resumos através de linguagem natural.

Exemplo:

```text
Gastei 400 reais no mercado.
```

A API interpreta a intenção, identifica os dados necessários e registra automaticamente a despesa.

---

# 🚀 Funcionalidades

## Gestão de despesas

* Registro manual de despesas via JSON.
* Consulta de despesas por:

  * ID;
  * período;
  * categoria.
* Consulta de totais:

  * por período;
  * por categoria.
* Persistência em PostgreSQL.
* Migrações de banco utilizando Flyway.

---

## Inteligência artificial

* Interpretação de comandos em português através de IA.
* Registro de despesas utilizando Tool Calling.
* Consulta de informações reais persistidas no banco através da IA.
* Histórico persistente de conversas utilizando `conversationId`.
* Recuperação das últimas interações para reconstrução de contexto.

---

## Áudio

* Transcrição de áudio utilizando:

```text
whisper-1
```

* Geração de respostas em áudio utilizando:

```text
gpt-4o-mini-tts
```

* Voz configurada:

```text
nova
```

* Resumo falado diário contendo:

  * total gasto;
  * despesas individuais;
  * categorias dos lançamentos.

---

## Segurança e documentação

* Autenticação utilizando JWT stateless.
* Spring Security.
* Usuários BCrypt em memória para desenvolvimento.
* Swagger UI para testes dos endpoints.
* Especificação OpenAPI pública.

---

# 🎙️ Caso de uso principal: registrar uma despesa através de voz

O usuário grava um áudio dizendo:

```text
Gastei 400 no mercado.
```

O cliente envia o arquivo para:

```http
POST /api/assistente/audio
Content-Type: multipart/form-data
```

---

## Fluxo de processamento

```text
Áudio enviado pelo usuário
          |
          v
Whisper
(transcrição do áudio)
          |
          v
Chat Model
(interpretação da intenção)
          |
          v
Tool Calling
(execução da função Java)
          |
          v
DespesaService
(regra de negócio)
          |
          v
PostgreSQL
(persistência da despesa)
          |
          v
Resposta JSON
(transcrição + confirmação)
```

---

## Exemplo de chamada

PowerShell:

```powershell
$form = @{
    conversationId = "usuario-local"
    file = Get-Item ".\gastei-400-mercado.m4a"
}

Invoke-RestMethod -Method Post `
    -Uri "http://localhost:8080/api/assistente/audio" `
    -Headers $headers `
    -Form $form
```

---

## Resposta esperada

```json
{
  "conversationId": "usuario-local",
  "textoOriginal": "Gastei 400 no mercado.",
  "transcricao": "Gastei 400 no mercado.",
  "resposta": "A despesa de 400 reais com mercado foi registrada com sucesso."
}
```

A despesa persistida poderá ser consultada:

```powershell
Invoke-RestMethod -Method Get `
    -Uri "http://localhost:8080/api/despesas/categoria/MERCADO" `
    -Headers $headers
```

---

# 🏗️ Arquitetura

Visão simplificada da solução:

```text
Cliente
 |
 | Authorization: Bearer JWT
 |
 v
JwtAuthFilter
 |
 v
Controller
 |
 +-----------------------------+
 |                             |
 v                             v
Services                  Serviços de IA
 |                             |
 v                             |
Repository                    |
 |                             |
 v                             v
PostgreSQL              OpenAI API
                              |
                 +------------+-------------+
                 |                          |
                 v                          v
             Chat Model               Whisper / TTS
                 |
                 v
            DespesaTools
```

---

## Fluxo de autenticação

```text
Authorization: Bearer JWT

        |
        v

JwtAuthFilter

        |
        v

Controller

        |
        v

Service

        |
        v

Repository

        |
        v

PostgreSQL
```

---

## Fluxo de inteligência artificial

```text
Mensagem do usuário

        |
        v

ChatClient (Spring AI)

        |
        v

Modelo OpenAI

        |
        v

DespesaTools

        |
        v

Serviços Java

        |
        v

Banco de dados
```

---

# 💬 Histórico de conversas

As interações da IA são armazenadas na tabela:

```text
interacoes_ia
```

Cada conversa possui um identificador:

```text
conversationId
```

Antes de processar uma nova mensagem, a aplicação recupera as últimas dez interações daquela conversa para reconstruir o contexto.

Esse mecanismo permite que a IA mantenha continuidade mesmo após reinicializações da aplicação.

---

# 🧰 Tecnologias utilizadas

| Componente        | Versão                      |
| ----------------- | --------------------------- |
| Java              | `21`                        |
| Spring Boot       | `4.0.6`                     |
| Spring AI         | `2.0.0-M4`                  |
| Spring Security   | Gerenciado pelo Spring Boot |
| JJWT              | `0.13.0`                    |
| springdoc-openapi | `3.0.3`                     |
| Maven Wrapper     | `3.3.4`                     |
| Maven             | `3.9.9`                     |
| PostgreSQL        | `postgres:16`               |
| Flyway            | Gerenciado pelo Spring Boot |
| Docker Desktop    | Versão estável atual        |

---

# 🤖 Serviços OpenAI utilizados

| Serviço        | Modelo            |
| -------------- | ----------------- |
| Chat           | `gpt-4o-mini`     |
| Speech-to-text | `whisper-1`       |
| Text-to-speech | `gpt-4o-mini-tts` |
| Voz TTS        | `nova`            |

---

# 📚 Referências oficiais

| Tecnologia      | Referência                |
| --------------- | ------------------------- |
| Java 21         | Eclipse Temurin           |
| Spring Boot     | Spring Boot               |
| Spring AI       | Spring AI                 |
| Spring Security | Spring Security           |
| PostgreSQL      | Imagem oficial Docker     |
| Flyway          | Flyway                    |
| OpenAI Models   | OpenAI Platform           |
| Speech-to-text  | OpenAI Speech API         |
| Text-to-speech  | OpenAI Text-to-Speech API |

---

Durante a validação integrada realizada em **1 de junho de 2026**, a imagem Docker:

```text
postgres:16
```

resolveu para:

```text
PostgreSQL 16.14
```

Como a tag `postgres:16` recebe atualizações de patch automaticamente, instalações futuras podem utilizar outra versão `16.x`.
# ⚙️ Instalação no Windows

Esta seção descreve como preparar o ambiente local para executar a aplicação.

Pré-requisitos:

* Windows 10 ou superior;
* Java 21;
* Docker Desktop;
* acesso à API OpenAI;
* PowerShell.

O Maven não precisa ser instalado manualmente, pois o projeto utiliza Maven Wrapper.

---

# 1. Instalar Java 21

Baixe o JDK 21:

Eclipse Temurin 21:

```text
https://adoptium.net/temurin/releases/?version=21
```

Passos:

1. Baixe o instalador Windows x64 JDK.
2. Execute o instalador.
3. Habilite a configuração de `JAVA_HOME`.
4. Abra um novo PowerShell.
5. Valide a instalação:

```powershell
java -version
```

Resultado esperado:

```text
java version "21.x.x"
```

O projeto utiliza Java 21.

Durante a validação integrada também foi utilizado:

```text
JDK 26.0.1
```

---

# 2. Instalar Docker Desktop

Baixe o Docker Desktop:

```text
https://docs.docker.com/desktop/setup/install/windows-install/
```

Passos:

1. Instale o Docker Desktop.
2. Inicie o aplicativo.
3. Aguarde o Docker Engine ficar disponível.
4. Valide:

```powershell
docker version
docker compose version
```

Não é necessário instalar PostgreSQL manualmente.

O Docker Compose irá:

* baixar a imagem oficial;
* criar o container;
* iniciar o banco;
* disponibilizar o PostgreSQL para a aplicação.

---

# 3. Baixar o projeto

## Opção A — Repositório Git

Instale o Git:

```text
https://git-scm.com/downloads
```

Execute:

```powershell
git clone URL_DO_REPOSITORIO

cd api_inteligente
```

---

## Opção B — Arquivo ZIP

Caso o projeto seja entregue compactado:

1. Extraia o arquivo.
2. Abra o PowerShell.
3. Acesse a pasta:

```powershell
cd C:\caminho\para\api_inteligente
```

---

O Maven Wrapper será responsável por baixar a versão correta do Maven:

```text
mvnw.cmd
```

Não é necessário instalar Maven globalmente.

---

# 4. Criar chave da OpenAI

A aplicação utiliza serviços OpenAI para:

* interpretação de texto;
* transcrição de áudio;
* síntese de voz.

Crie uma chave:

```text
https://platform.openai.com/api-keys
```

Passos:

1. Acesse a área de API Keys.
2. Crie uma nova chave.
3. Confirme que a conta possui créditos disponíveis.
4. Armazene a chave com segurança.

Nunca:

* envie a chave para o Git;
* coloque a chave no README;
* compartilhe a chave em mensagens públicas.

---

# 5. Configurar variáveis de ambiente

Copie o arquivo de exemplo:

```powershell
Copy-Item .env.example .env
```

Edite o arquivo:

```dotenv
OPENAI_API_KEY=sk-substitua-pela-chave

DB_URL=jdbc:postgresql://localhost:5432/api_inteligente
DB_USER=api_inteligente
DB_PASSWORD=api_inteligente

JWT_SECRET_BASE64=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=

JWT_EXPIRATION_MINUTES=60
```

---

## Gerar um segredo JWT seguro

O valor acima é apenas para desenvolvimento local.

Para gerar um segredo novo:

```powershell
[Convert]::ToBase64String(
    [Security.Cryptography.RandomNumberGenerator]::GetBytes(32)
)
```

Copie o resultado para:

```dotenv
JWT_SECRET_BASE64=
```

---

## Carregar `.env` no PowerShell

O arquivo `.env` não deve ser versionado.

Para carregar as variáveis na sessão atual:

```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#][^=]*)=(.*)$') {
        [Environment]::SetEnvironmentVariable(
            $matches[1].Trim(),
            $matches[2].Trim(),
            'Process'
        )
    }
}
```

---

# ▶️ Executando a aplicação

## 1. Iniciar PostgreSQL

Execute:

```powershell
docker compose --env-file .env up -d postgres
```

Confira o container:

```powershell
docker compose --env-file .env ps
```

Resultado esperado:

```text
postgres:16
healthy
```

---

## Verificar versão do PostgreSQL

Execute:

```powershell
docker compose --env-file .env exec postgres `
    psql -U $env:DB_USER `
    -d api_inteligente `
    -c "select version();"
```

---

# 2. Executar testes automatizados

Execute:

```powershell
.\mvnw.cmd test
```

Resultado esperado:

```text
18 testes executados
0 falhas
0 erros
```

---

# 3. Gerar pacote da aplicação

Execute:

```powershell
.\mvnw.cmd package
```

Resultado esperado:

```text
target\api_inteligente-0.0.1-SNAPSHOT.jar
```

---

# 4. Iniciar a API

Execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde:

```text
Started ApiInteligenteApplication
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

# 🧪 Testando pelo Swagger UI

O projeto disponibiliza Swagger UI para facilitar os testes.

Não é necessário:

* instalar ModHeader;
* configurar extensões no navegador;
* gerar manualmente chamadas HTTP.

O Swagger gerencia automaticamente o header JWT após autenticação.

---

# 1. Abrir Swagger UI

Acesse:

```text
http://localhost:8080/swagger-ui.html
```

---

# 2. Gerar token JWT

No Swagger:

1. Localize:

```text
POST /auth/login
```

2. Clique em:

```text
Try it out
```

3. Informe um usuário de desenvolvimento.

---

## Usuário influencer

```json
{
  "username": "influencer",
  "password": "password"
}
```

---

## Usuário brand

```json
{
  "username": "brand",
  "password": "password"
}
```

---

4. Clique em:

```text
Execute
```

5. A resposta deve retornar HTTP:

```text
200 OK
```

Exemplo:

```json
{
  "token": "JWT_GERADO_AQUI",
  "type": "Bearer",
  "expiresInMinutes": 60
}
```

Copie somente:

```text
JWT_GERADO_AQUI
```

---

# 3. Autorizar chamadas protegidas

No Swagger:

1. Clique em:

```text
Authorize
```

2. Cole somente o token JWT.

Não informe:

```text
Bearer TOKEN
```

Informe apenas:

```text
TOKEN
```

O Swagger adicionará automaticamente:

```http
Authorization: Bearer TOKEN
```

3. Clique em:

```text
Authorize
```

4. Clique em:

```text
Close
```

---

# 4. Validar usuário autenticado

Execute:

```http
GET /api/me
```

Resposta esperada:

```json
{
  "username": "influencer",
  "authorities": [
    "ROLE_INFLUENCER"
  ]
}
```

---

# 5. Validar uma API protegida

Execute:

```http
GET /api/despesas
```

Resultado esperado:

```text
HTTP 200 OK
```

Sem JWT:

```text
HTTP 401 Unauthorized
```

---

# 🔐 Autenticação JWT

A autenticação utiliza JWT stateless.

A aplicação não mantém sessão no servidor.

---

## Rotas públicas

```text
POST /auth/login

/swagger-ui.html

/v3/api-docs

/v3/api-docs.yaml
```

---

## Rotas protegidas

Todas as APIs abaixo exigem:

```http
Authorization: Bearer TOKEN
```

Exemplo:

```text
/api/**
```

---

# Usuários de desenvolvimento

| Usuário    | Senha    | Permissão       |
| ---------- | -------- | --------------- |
| influencer | password | ROLE_INFLUENCER |
| brand      | password | ROLE_BRAND      |

---

# Gerando token via PowerShell

```powershell
$login = @{
    username = "influencer"
    password = "password"
} | ConvertTo-Json


$auth = Invoke-RestMethod `
    -Method Post `
    -Uri "http://localhost:8080/auth/login" `
    -ContentType "application/json" `
    -Body $login


$headers = @{
    Authorization = "$($auth.type) $($auth.token)"
}


Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/me" `
    -Headers $headers
```

O token expira conforme:

```dotenv
JWT_EXPIRATION_MINUTES
```

Valor padrão:

```text
60 minutos
```

---
# 📘 Swagger UI e OpenAPI

A aplicação disponibiliza documentação interativa através do Swagger UI.

Os recursos OpenAPI são públicos para facilitar testes locais, porém as APIs de negócio continuam protegidas por JWT.

---

## Endereços disponíveis

| Recurso      | URL                                      |
| ------------ | ---------------------------------------- |
| Swagger UI   | `http://localhost:8080/swagger-ui.html`  |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs`      |
| OpenAPI YAML | `http://localhost:8080/v3/api-docs.yaml` |

---

## Testando APIs protegidas pelo Swagger

Fluxo recomendado:

1. Acesse:

```text
http://localhost:8080/swagger-ui.html
```

2. Execute:

```text
POST /auth/login
```

3. Copie o campo:

```json
token
```

4. Clique em:

```text
Authorize
```

5. Informe apenas o JWT.

6. Execute qualquer endpoint:

```text
/api/**
```

O Swagger adicionará automaticamente:

```http
Authorization: Bearer TOKEN
```

---

# 🎤 Testando o fluxo de voz

O endpoint:

```http
POST /api/assistente/audio
```

permite enviar um arquivo de áudio contendo uma solicitação.

Exemplo:

```text
Gastei 400 no mercado.
```

A aplicação executa:

```text
Áudio
 |
 v
Whisper
 |
 v
Transcrição
 |
 v
Chat Model
 |
 v
Tool Calling
 |
 v
Registro da despesa
 |
 v
Resposta
```

Parâmetros:

| Campo          | Obrigatório | Descrição                 |
| -------------- | ----------- | ------------------------- |
| file           | Sim         | Arquivo de áudio          |
| conversationId | Não         | Identificador da conversa |

Os modelos de IA utilizam a API OpenAI e podem gerar cobrança conforme o uso.

---

# 🔊 Resumo diário em áudio

O endpoint:

```http
GET /api/assistente/resumo-dia/audio
```

gera um arquivo MP3 contendo:

* total gasto no dia;
* cada despesa individual;
* categoria de cada lançamento.

---

## Informando uma data específica

Exemplo:

```http
GET /api/assistente/resumo-dia/audio?data=2026-06-01
```

---

## Usando a data atual

```http
GET /api/assistente/resumo-dia/audio
```

Quando não existem despesas:

```text
Não foram registradas despesas neste dia.
```

A geração depende do serviço OpenAI TTS e normalmente leva alguns segundos.

Caso a API externa permaneça indisponível após tentativas automáticas, a aplicação retorna:

```text
HTTP 503 Service Unavailable
```

---

# ✅ Validando a solução

Os testes abaixo validam:

* persistência;
* integração OpenAI;
* Tool Calling;
* histórico;
* Whisper;
* TTS;
* agregações;
* documentação.

Execute os comandos em outro PowerShell.

Primeiro gere o token JWT conforme a seção:

```text
Autenticação JWT
```

Depois carregue:

```powershell
$headers
```

---

# 1. Validar registro manual e PostgreSQL

Criar uma despesa:

```powershell
$body = @{
    descricao = "Mercado"
    valor = 89.90
    categoria = "MERCADO"
    dataDespesa = "2026-06-01"
    formaPagamento = "PIX"
    observacao = "Compras da semana"
} | ConvertTo-Json


Invoke-RestMethod `
    -Method Post `
    -Uri "http://localhost:8080/api/despesas" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body $body
```

Consultar:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/despesas" `
    -Headers $headers
```

Validar:

```text
categoria = MERCADO

origemLancamento = MANUAL
```

---

# 2. Validar comandos em linguagem natural

Enviar uma solicitação:

```powershell
$body = @{
    conversationId = "validacao-texto"
    mensagem = "Registre uma despesa de 45 reais com gasolina hoje na categoria combustivel."
} | ConvertTo-Json


Invoke-RestMethod `
    -Method Post `
    -Uri "http://localhost:8080/api/assistente/texto" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body $body
```

Consultar:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/despesas/categoria/COMBUSTIVEL" `
    -Headers $headers
```

Resultado esperado:

```text
valor = 45

categoria = COMBUSTIVEL

origemLancamento = TEXTO
```

---

# 3. Validar consulta inteligente e histórico

Enviar pergunta:

```powershell
$body = @{
    conversationId = "validacao-texto"
    mensagem = "Quanto gastei com combustivel hoje?"
} | ConvertTo-Json


Invoke-RestMethod `
    -Method Post `
    -Uri "http://localhost:8080/api/assistente/texto" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body $body
```

Consultar histórico:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/interacoes/validacao-texto" `
    -Headers $headers
```

Validar:

* IA consultou dados reais;
* histórico contém as interações;
* `conversationId` foi preservado.

---

# 4. Validar Text-to-Speech

Gerar áudio:

```powershell
$body = @{
    texto = "Registre uma despesa de trinta e dois reais com mercado hoje."
} | ConvertTo-Json


Invoke-WebRequest `
    -Method Post `
    -Uri "http://localhost:8080/api/assistente/sintese" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body $body `
    -OutFile ".\validacao-tts.mp3"
```

Verificar arquivo:

```powershell
Get-Item .\validacao-tts.mp3
```

Resultado esperado:

```text
Arquivo existente
Tamanho maior que zero
```

Configuração:

```text
src/main/resources/application.yml
```

Voz:

```text
nova
```

---

# 5. Validar Whisper e fluxo completo de áudio

Utilizando o áudio criado:

```powershell
$form = @{
    conversationId = "validacao-audio"
    file = Get-Item ".\validacao-tts.mp3"
}


Invoke-RestMethod `
    -Method Post `
    -Uri "http://localhost:8080/api/assistente/audio" `
    -Headers $headers `
    -Form $form
```

Consultar:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/despesas/categoria/MERCADO" `
    -Headers $headers
```

Validar:

```text
transcricao preenchida

resposta preenchida

conversationId correto

origemLancamento = AUDIO
```

---

# 6. Validar agregações

Total por período:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/despesas/totais/periodo?inicio=2026-06-01&fim=2026-06-30" `
    -Headers $headers
```

Total por categoria:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/api/despesas/totais/categoria" `
    -Headers $headers
```

---

# 7. Validar OpenAPI

Executar:

```powershell
Invoke-WebRequest `
    -Uri "http://localhost:8080/swagger-ui.html"
```

E:

```powershell
Invoke-RestMethod `
    -Method Get `
    -Uri "http://localhost:8080/v3/api-docs"
```

Resultado esperado:

* Swagger disponível;
* especificação OpenAPI carregada;
* endpoints documentados.

---

# 8. Validar resumo diário em áudio

Gerar resumo:

```powershell
Invoke-WebRequest `
    -Method Get `
    -Uri "http://localhost:8080/api/assistente/resumo-dia/audio?data=2026-06-01" `
    -Headers $headers `
    -OutFile ".\resumo-despesas-dia.mp3"
```

Verificar:

```powershell
Get-Item .\resumo-despesas-dia.mp3
```

---

# 📡 Endpoints

| Método | Endpoint                              | Descrição                |
| ------ | ------------------------------------- | ------------------------ |
| POST   | `/auth/login`                         | Autenticação JWT         |
| GET    | `/api/me`                             | Usuário autenticado      |
| POST   | `/api/despesas`                       | Registrar despesa manual |
| GET    | `/api/despesas`                       | Listar despesas          |
| GET    | `/api/despesas/{id}`                  | Consultar por ID         |
| GET    | `/api/despesas/periodo?inicio=&fim=`  | Consultar período        |
| GET    | `/api/despesas/categoria/{categoria}` | Consultar categoria      |
| GET    | `/api/despesas/totais/periodo`        | Total por período        |
| GET    | `/api/despesas/totais/categoria`      | Total por categoria      |
| POST   | `/api/assistente/texto`               | Interpretar texto        |
| POST   | `/api/assistente/audio`               | Interpretar áudio        |
| POST   | `/api/assistente/sintese`             | Gerar MP3                |
| GET    | `/api/assistente/resumo-dia/audio`    | Resumo diário em áudio   |
| GET    | `/api/interacoes/{conversationId}`    | Histórico da IA          |
| GET    | `/swagger-ui.html`                    | Swagger UI               |
| GET    | `/v3/api-docs`                        | OpenAPI JSON             |
| GET    | `/v3/api-docs.yaml`                   | OpenAPI YAML             |

---

# 🛑 Encerrar ambiente

Parar somente o PostgreSQL:

```powershell
docker compose --env-file .env down
```

Remover também os dados:

```powershell
docker compose --env-file .env down -v
```

Use:

```text
down -v
```

somente quando desejar apagar permanentemente o banco local.

---

# 🛠️ Solução de problemas

## Docker não responde

Verifique:

```powershell
docker info
```

Confirme que o Docker Desktop está aberto.

---

## Porta 5432 ocupada

Outro PostgreSQL pode estar utilizando a porta.

Soluções:

* parar o serviço local;
* alterar o mapeamento no `docker-compose.yml`;
* atualizar:

```dotenv
DB_URL
```

---

## API não inicia por erro de banco

Confira:

```powershell
docker compose --env-file .env ps
```

Valide:

* usuário;
* senha;
* banco;
* variáveis carregadas.

---

## OpenAI retorna erro de autenticação

Verifique:

* chave configurada;
* créditos disponíveis;
* permissões da conta.

---

## Resumo em áudio retorna HTTP 503

A geração depende do serviço OpenAI.

A aplicação executa tentativas automáticas.

Caso receba:

```text
503 Service Unavailable
```

aguarde alguns segundos e tente novamente.

---

## API retorna HTTP 401

Confirme:

* login realizado;
* JWT válido;
* header correto.

Formato:

```http
Authorization: Bearer TOKEN
```

O token expira conforme:

```dotenv
JWT_EXPIRATION_MINUTES
```

Valor padrão:

```text
60 minutos
```

---