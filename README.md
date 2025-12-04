# 🎓 Sistema de Controle Acadêmico
**Trabalho Final – Tópicos em Java Web – IFCE – 2025.2**

Aplicação web completa para gerenciamento acadêmico, construída utilizando:

- **Spring Boot**
- **Thymeleaf**
- **Spring Security (roles ADMIN/SECRETARIA)**
- **Spring Data JPA (Hibernate)**

---

## 📌 Funcionalidades

### 👨‍🎓 **Alunos**
- Cadastrar, editar, listar e excluir
- Campos: *id, nome, matrícula (única), email, dataNascimento, status*
- Status: **ATIVO / INATIVO**

### 📚 **Disciplinas**
- ADMIN pode cadastrar, editar, listar e excluir
- Campos: *código (único), nome, carga horária, semestre*

### 📝 **Matrículas**
- Cadastrar, editar, listar e excluir
- Vincula **Aluno + Disciplina**
- Situações: *CURSANDO, APROVADO, REPROVADO, TRANCADO*
- Impede matrícula duplicada *CURSANDO* para o mesmo aluno na mesma disciplina

### 🔐 **Autenticação & Autorização**
- Login em `/login`
- Cadastro de usuários em `/register`
- Senhas com **BCrypt**
- Perfis:
    - **ADMIN** → acesso total
    - **SECRETARIA** → gerencia alunos e matrículas

---

## ⚙️ Requisitos

- **Java 21+**
- **Maven 3.9+**
- (Opcional) MySQL 8.0+

---

## 🔑 Usuários de teste (criadas automaticamente)

| Login         | Senha    | Perfil         |
|---------------|----------|----------------|
| **admin**     | adm123   | ROLE_ADMIN     |
| **secretaria**| sec123   | ROLE_SECRETARIA|

---

## 🧭 5. Páginas Principais

### 🔓 Público
| URL       | Função            |
|----------|--------------------|
| `/login` | Tela de login      |
| `/`      | Home pública       |

### 🔐 Protegidos
| URL               | Acesso                                  |
|-------------------|-------------------------------------------|
| `/register`       | ADMIN, SECRETARIA                         |
| `/alunos/**`      | ADMIN, SECRETARIA                         |
| `/matriculas/**`  | ADMIN, SECRETARIA                         |
| `/disciplinas/**` | ADMIN                                     |

---

## 📂 6. Estrutura do projeto

```
src
 └─ main
     ├─ java
     │   └─ ifce.edu.br.controle_academico
     │        ├─ config
     │        ├─ controller
     │        ├─ exception
     │        ├─ model
     │        │    ├─ entity
     │        │    └─ enums
     │        ├─ repository
     │        ├─ service
     │        └─ ControleAcademicoApplication.java
     │
     └─ resources
         ├─ templates
         │    ├─ alunos/
         │    ├─ disciplinas/
         │    ├─ matriculas/
         │    ├─ fragments/
         │    ├─ login.html
         │    ├─ register.html
         │    ├─ index.html
         │    └─ erro.html
         └─ application.properties
```
## 🛠 Como Rodar o Projeto

### 1) Clonar o repositório

```
git clone https://github.com/carolineaarrais/TJW_TrabalhoFinal.git
cd TJW_TrabalhoFinal
```

### 2) Configuração da Database
- Crie o banco **academico**
- Confirme que o arquivo **`src/main/resources/application-prod.properties`** está assim:
```
spring.datasource.url=jdbc:mysql://localhost:3306/controle_academico?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=>Escreva sua senha aqui<

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3) Executar a aplicação
- Rode o comando no terminal:
```
mvn spring-boot:run
```
- Ou execute a classe **`ControleAcademicoApplication
`**
