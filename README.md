
# FinBal – Projeto Fintech FIAP

**Autores:**  
- Elise Oliveira
- Lais Sallas
- Gabrielli  Martinelli

**Curso:** Análise e Desenvolvimento de Sistemas – FIAP  
**Capítulo/Atividade:** Capítulo 14, Fase 7 – Entrega Final da Fintech  

---

## 1. Descrição do Projeto

O **FinBal** é um projeto de sistema financeiro (Fintech) desenvolvido em Java, utilizando Spring Boot, com o objetivo de gerenciar **usuários, contas e transações**.  

O sistema permite:  
- Cadastro de usuários;  
- Criação e gerenciamento de contas bancárias;  
- Registro e consulta de transações financeiras;  
- Tratamento de exceções personalizadas para uma experiência segura e consistente.  

O projeto segue boas práticas de arquitetura **MVC (Model-View-Controller)** e separação de responsabilidades, garantindo manutenção e escalabilidade.

---

## 2. Estrutura do Projeto

```
src
 └─ main
     └─ java/com/fiap/finbal
         ├─ controller
         │   ├─ ContaController.java
         │   ├─ TransacaoController.java
         │   └─ UsuarioController.java
         ├─ dto
         │   └─ MensagemResponseDTO.java
         ├─ exception
         │   ├─ GlobalException.java
         │   └─ UsuarioException.java
         ├─ model
         │   ├─ Conta.java
         │   ├─ Pessoa.java
         │   ├─ Transacao.java
         │   └─ Usuario.java
         ├─ repository
         │   ├─ ContaRepository.java
         │   ├─ TransacaoRepository.java
         │   └─ UsuarioRepository.java
         ├─ service
         │   ├─ ContaService.java
         │   ├─ TransacaoService.java
         │   └─ UsuarioService.java
         └─ FinbalApplication.java
 └─ resources
     ├─ static
     ├─ templates
     └─ application.properties
test/java/com/fiap/finbal
 └─ service
     ├─ UsuarioServiceTest.java
     └─ FinbalApplicationTests.java
target
.gitignore
.gitattributes
pom.xml
Finbal-FIAP.postman_collection.json
HELP.md
README.md
```

**Detalhes da Estrutura:**

- **controller:** Contém os controladores REST que recebem e processam as requisições HTTP, interagindo com os serviços.  
- **dto:** Objetos de transferência de dados (Data Transfer Objects), usados para enviar mensagens ou dados específicos.  
- **exception:** Tratamento de exceções personalizadas, garantindo que erros do sistema ou do usuário sejam bem comunicados.  
- **model:** Classes de domínio que representam entidades do sistema, como `Usuario`, `Conta` e `Transacao`.  
- **repository:** Interfaces que comunicam com o banco de dados usando Spring Data JPA.  
- **service:** Camada de negócios que contém regras e lógica de processamento das operações financeiras.  
- **resources:** Arquivos de configuração e templates para a aplicação.  
- **test:** Testes unitários e de integração para validar a funcionalidade dos serviços.  
- **FinbalApplication.java:** Classe principal para iniciar a aplicação Spring Boot.

---

## 3. Funcionalidades Implementadas

1. **Usuário**
   - Cadastro de usuário com validação de dados;  
   - Listar informações de usuários ou um único usuário;  
   - Atualizar usuário
   - Deletar usuário
   - Lançamento de exceções personalizadas caso haja erro.

2. **Conta**
   - Criação de contas associadas a usuários;  
   - Consultar e atualizar a conta;  
   - Deletar conta.

3. **Transações**
   - Criação de transação associadas a usuários;  
   - Consultar e atualizar a transação;  
   - Deletar transação.

4. **Tratamento de Exceções**
   - Mensagens claras de erro;  
   - `GlobalException` gerencia exceções de forma centralizada;  
   - `UsuarioException` específica para erros de usuários.

---

## 4. Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Maven  
- JUnit 5 (para testes unitários)  
- Postman (para testar APIs)

---

## 5. Como Rodar o Projeto

1. Abra o terminal da IDE ou aplicação direto no diretório e execute:  
```bash
mvn clean install && java -jar target/finbal-0.0.1-SNAPSHOT.jar
```

4. Acesse a API via Postman ou navegador, endpoints principais:  
- `/usuarios` – gerenciar usuários  
- `/contas` – gerenciar contas  
- `/transacoes` – registrar e consultar transações
Na raiz do projeto tem a collection em anexo.

---

## 6. Testes
Os testes estão localizados em `test/java/com/fiap/finbal` e incluem:  
- `UsuarioServiceTest.java` – validações da camada de serviço de usuários  
- `FinbalApplicationTests.java` – testes de inicialização da aplicação  

---

## 7. Considerações Finais

Este projeto da **Fase 7 do Capítulo 14** foi desenvolvido com foco em:  
- Boas práticas de programação;  
- Estrutura clara e organizada (MVC);  
- Testes unitários para garantir confiabilidade;  
- Facilidade de manutenção e escalabilidade futura.  

O **FinBal** serve como base para sistemas financeiros reais, podendo ser expandido com novas funcionalidades como integração com PIX, relatórios financeiros e dashboards.
