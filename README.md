# 📦 CatALog - Sistema de Gerenciamento de Biblioteca - Repositório Backend

Este repositório contém o backend do sistema **CatALog**, uma aplicação de gerenciamento de biblioteca desenvolvida com **Spring Boot** e persistência em **MySQL**. Ele é responsável por toda a lógica de negócio, autenticação, regras de empréstimo e integração com APIs externas.

Este projeto foi proposto em aula como exercício prático para consolidar os conhecimentos adquiridos ao longo do semestre, especialmente no uso de **Java com Spring Boot**, **persistência com JDBC**, **consultas nativas, derivadas e JPQL**, além de práticas de arquitetura RESTful e autenticação com JWT.

<br>

## 🌐 Integração com Frontend

O backend expõe uma API REST robusta que é consumida pelo frontend desenvolvido em **Angular**, permitindo uma experiência completa e fluida para o usuário.

> 🔗 **Confira o repositório do frontend aqui:**
> 👉 [🌐 CatALog - Frontend (Angular)](https://github.com/lariiscriis/CatAlog_frontEnd)

<br>

## 📡 Integração com API do Google Books

O sistema se conecta à **API do Google Books** para realizar buscas por livros a partir de títulos, autores ou ISBN. Os dados retornados (como título, autores, descrição, capa e categorias) são então **salvos no banco de dados**, otimizando o desempenho e permitindo futuras consultas sem depender da API externa.

Essa funcionalidade permite enriquecer o catálogo da biblioteca de forma prática, com uma base rica e atualizada de obras literárias.

<br>

## 🌐 Funcionalidades

- 📚 **Cadastro de livros**
  - ISBN, título, autor, disponibilidade, imagem e categoria
- 🔍 **Busca por título e autor**
  - Utilizando queries derivadas
- 📊 **Consultas personalizadas**
  - Nativas, JPQL e derivadas
- 🧾 **Empréstimos**
  - Registrar empréstimos com data prevista
  - Devoluções com cálculo de multa por atraso
  - Controle de renovação (até 2 vezes)
- 🔒 **Autenticação com JWT**
  - Registro e login de usuários com segurança
- 📘 **Estante Virtual**
  - Marcar livros como **Favorito**, **Desejado** ou **Emprestado**
- 📝 **Anotações**
  - Permite que o usuário anote páginas específicas com observações e avaliações
- 👤 **Gestão de Perfil**
  - Dados do usuário e upload de imagem (Cloudinary)

<br>

## 🔮 Funcionalidades Futuras

### 🔔 Sistema de Notificações:

- Aviso de devolução próxima  
- Aviso quando um livro desejado estiver disponível  
- Confirmação de devolução  
- Alerta de multa pendente  

### 📩 Envio de email automático para alertas  
### 📊 Dashboard administrativa com estatísticas gráficas  

<br>

## 🔧 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security + JWT**
- **MySQL**
- **Maven**
- **Lombok**
- **Imgur (upload de imagem)**

<br>

## ▶️ Como Rodar o Projeto

Siga os passos abaixo para executar o backend localmente:

### 🔧 Pré-requisitos

- Java 17 ou superior  
- Maven  
- MySQL Server rodando  
- (Opcional) Postman para testes  

### ⚙️ Configuração do banco de dados

Certifique-se de criar um banco de dados chamado `catalog` e configurar suas credenciais no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/catalog
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```

🚀 Passo a passo
1. Clone o repositório:
```cmd
git clone https://github.com/lariiscriis/CatAlog_backEnd.git
cd CatAlog_backEnd
```
2. Compile e rode o projeto clicando no botão de play da IDE ou dando o comando:
   ```
   ./mvnw spring-boot:run
```

> 💡 **Importante:** o frontend Angular deve estar configurado para consumir as rotas REST.


