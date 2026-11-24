<div align="center">

# Monitoramento de Preços

</div>

Uma loja de produtos naturais recebe tabelas de preços e orçamentos em PDF de diferentes fornecedores. A conferência manual dificulta detectar aumentos e reduções, consultar valores antigos e avaliar o impacto na precificação.

Este sistema transforma esses PDFs em dados estruturados e rastreáveis, mantém o histórico de importações e compara automaticamente os novos preços com o último preço válido conhecido de cada item e fornecedor.

---

📌 **Status atual**

| Item | Situação |
| :--- | :--- |
| Estrutura do projeto (Spring Boot + JPA + PostgreSQL) | Concluído |
| Banco de dados via Docker Compose | Concluído |
| Cadastro de produtos (`/produtos`) | Concluído (criar, listar, buscar por id e atualizar) |
| Cadastro de fornecedores (`/fornecedores`) | Concluído (criar e listar) |
| Edição de produtos e consulta por id | Concluído |
| Vínculo entre item do fornecedor e produto interno | Concluído |
| Importação de PDF, extração e conferência | Concluído |
| Histórico de importação e comparação de preços | Concluído |
| Testes automatizados | Concluído |
| Pipeline de CI (GitHub Actions) | Concluído |
| Aplicação conteinerizada (`Dockerfile`) | Concluído |
| Logs de importação | Concluído |

---

🛠️ **Tecnologias**

* Java e Spring Boot (Spring Web e Spring Data JPA)
* PostgreSQL 15
* Maven (com Maven Wrapper, `./mvnw`)
* Docker e Docker Compose
* Lombok
* Apache PDFBox

---

📋 **Requisitos**

* JDK 17 ou superior
* Docker e Docker Compose
* Git

> 💡 **Nota:** Não é necessário instalar o Maven: o projeto inclui o Maven Wrapper (`./mvnw`).

---

🚀 **Como executar**

### 1. Clonar o repositório

```bash
git clone https://github.com/Luh-Ramiro/projeto-devops.git
cd projeto-devops
