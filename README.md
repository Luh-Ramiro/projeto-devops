<div align="center">

# Monitoramento de Preços

</div>

Uma loja de produtos naturais recebe tabelas de preços e orçamentos em PDF de diferentes fornecedores. A conferência manual dificulta detetar aumentos e reduções, consultar valores antigos e avaliar o impacto na precificação.

Este sistema transforma esses PDFs em dados estruturados e rastreáveis, mantém o histórico de importações e compara automaticamente os novos preços com o último preço válido conhecido de cada item e fornecedor.

---

## 📌 Status atual (marco E1)

| Item | Situação |
| :--- | :--- |
| Estrutura do projeto (Spring Boot + JPA + PostgreSQL) | Concluído |
| Banco de dados via Docker Compose | Concluído |
| Cadastro de produtos (`/produtos`) | Concluído (criar e listar) |
| Cadastro de fornecedores (`/fornecedores`) | Concluído (criar e listar) |
| Edição de produtos e consulta por id | Planejado |
| Vínculo entre item do fornecedor e produto interno | Planejado |
| Importação de PDF, extração e conferência | Planejado |
| Histórico de importações e comparação de preços | Planejado |
| Testes automatizados | Planejado |
| Pipeline de CI (GitHub Actions) | Planejado |
| Aplicação conteinerizada (`Dockerfile`) | Planejado |
| Logs de importação | Planejado |

---

## 🛠️ Tecnologias

* Java e Spring Boot (Spring Web e Spring Data JPA)
* PostgreSQL 15
* Maven (com Maven Wrapper, `./mvnw`)
* Docker e Docker Compose
* Lombok

---

## 📋 Requisitos

* JDK 17 ou superior
* Docker e Docker Compose
* Git

> 💡 **Nota:** Não é necessário instalar o Maven: o projeto inclui o Maven Wrapper (`./mvnw`).

---

## 🚀 Como executar

### 1. Clonar o repositório

bash
git clone https://github.com/Luh-Ramiro/projeto-devops.git
cd projeto-devops

### 2. Subir o banco de dados

bash
docker compose up -d

Isso inicia um container PostgreSQL na porta `5432`. Para conferir:

bash
docker ps

### 3. Subir a aplicação

bash
./mvnw spring-boot:run


A API fica disponível em `http://localhost:8080`. Na primeira execução o Maven baixa as dependências, o que pode levar alguns minutos. Se aparecer "permission denied" no `mvnw`, rode `chmod +x mvnw`.

As tabelas são criadas automaticamente pelo Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

### 4. Testar os endpoints
O arquivo `src/teste.http` reúne requisições prontas, que podem ser executadas diretamente pelo IntelliJ IDEA. Também é possível usar `curl`:

bash
curl http://localhost:8080/produtos
curl http://localhost:8080/fornecedores

---

## 🔌 Endpoints disponíveis

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| **GET** | `/produtos` | Lista os produtos |
| **POST** | `/produtos` | Cadastra um produto |
| **GET** | `/fornecedores` | Lista os fornecedores |
| **POST** | `/fornecedores` | Cadastra um fornecedor |

### Exemplo: Cadastrar um produto

http
POST http://localhost:8080/produtos
Content-Type: application/json

{
"codigo": "PROD003",
"nome": "Café Expresso",
"categoria": "Bebidas",
"unidade": "un",
"precoCompra": 5.00,
"precoVenda": 12.00,
"estoqueAtual": 0,
"estoqueMinimo": 0,
"status": "ATIVO"
}

*O campo `codigo` é único, então um código já cadastrado não pode ser repetido.*

### Exemplo: Cadastrar um fornecedor

http
POST http://localhost:8080/fornecedores
Content-Type: application/json

{
"nome": "Fornecedor Teste",
"cnpj": "12.345.678/0001-90",
"contato": "contato@fornecedor.com"
}

---

## 🗂️ Estrutura do projeto

text
src/
├── main/
│   ├── java/com/uniesp/devops/
│   │   ├── MonitoramentoPrecosApplication.java
│   │   ├── fornecedor/      # entidade, repository e controller
│   │   └── produto/         # entidade, repository e controller
│   └── resources/
│       └── application.properties
├── test/                    # testes automatizados
└── teste.http               # requisições de exemplo
docker-compose.yml           # serviços de infraestrutura (PostgreSQL)
pom.xml

---

## 🧪 Testes

Para executar os testes automatizados:

bash
./mvnw test

Os testes das regras críticas de comparação de preços (aumento, redução, igualdade, novo item, preço zero e associação pendente) serão adicionados nos próximos marcos.

## 🔄 Pipeline de CI

Implementado: workflow do GitHub Actions acionado por `push` e `Pull Request`, com etapas de build e testes. Falhas nos testes reprovam a pipeline.

## ⚙️ Configuração e variáveis de ambiente

Atualmente a conexão com o banco usa os valores de desenvolvimento definidos em `src/main/resources/application.properties`. Esses valores servem apenas para o ambiente local.

*Implementado: workflow do GitHub Actions acionado por push e Pull Request, com etapas de build e testes. Falhas nos testes reprovam a pipeline*.

---

## 🌿 Fluxo de trabalho (Git)

* A branch `main` recebe apenas código integrado por `Pull Request`.
* Cada funcionalidade é desenvolvida em uma branch própria, criada a partir da `main` atualizada, com o padrão `feature/nome-da-funcionalidade`.
* Todo `Pull Request` deve ser revisado por outro integrante da equipe antes do merge.
* As mensagens de commit descrevem o que foi alterado (exemplo: *Adiciona CRUD de produto e fornecedor*).

---

## Marcos do projeto

| Marco | Entrega | Situação |
| :--- | :--- | :--- |
| **E0** | Histórias de usuário e Product Backlog | Concluído |
| **E1** | Git e estrutura: repositório, arquitetura inicial e cadastros básicos | Concluído |
| **E2** | Núcleo funcional: upload, extração, associação e persistência | Pendente |
| **E3** | Histórico e CI: comparação, casos extremos, testes e pipeline | Concluído |
| **E4** | Containers: ambiente reproduzível e parametrizado | Pendente |
| **E5** | Operação e apresentação: logs, documentação e fluxo completo | Pendente |
