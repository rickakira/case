# **Case PIX**

Projeto baseado nas premissas de desenvolvimento do **PIX**.

---

## 🚀 **Tecnologias Utilizadas**

- Java 21
- Spring Boot
- Lombok
- Spring Data
- JPA / Hibernate
- Bean Validations
- Spring Test
- OpenAPI 3.0
- Spring Initializr

---

## 🛠️ **Ferramenta de Build**

- Maven

---

## 🗄️ **Banco de Dados Utilizado**

- H2 (banco de dados em memória)

---

## 🎨 **Design Patterns Utilizados**

### **Strategy Pattern**

Utilizado para **segregar regras e validações de negócio** por tipo de PIX. Esse padrão auxilia na inclusão de novas validações e regras para cada tipo de PIX **sem impactar** outras funcionalidades. Além disso, melhora a **legibilidade do código**, eliminando múltiplas condicionais (`if-else`).

### **Builder Pattern**

Utilizado em conjunto com a biblioteca **Lombok** para reduzir **boilerplate code** e facilitar a construção de entidades/DTOs. Esse padrão melhora a **criação de objetos** no código, tornando-o mais limpo e organizado.

---

## 🔹 **12 Fatores do Desenvolvimento de Software**

Os princípios aplicados no projeto são:

1. **Codebase** - O código-fonte está disponível no GitHub: [Repositório do Projeto](https://github.com/rickakira/case.git).
2. **Dependencies** - Utilização do **Maven** para gerenciamento de dependências e organização do projeto.

---

## 📊 **Cobertura de Testes**

Os relatórios de cobertura de testes podem ser encontrados no diretório:

📂 `coverage/htmlReport`

---



