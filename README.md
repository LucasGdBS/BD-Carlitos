# Projeto BD-Carlitos ERP

## 📝 Descrição

Este projeto foi desenvolvido para a disciplina de Banco de Dados, com o objetivo de criar um sistema de gerenciamento de pedidos, estoque, clientes e funcionários do CarlitosBurguer

## 📋 Funcionalidades

O sistema possui as seguintes funcionalidades:

- Gestão de funcionarios
  - Assim como de gerentes, motoqueiros, e atendentes da lanchonete
- Gestão de clientes
- Gestão de produtos
- Gestão do estoque de ingredientes
- Gestão de pedidos, como adição, remoção e edição de pedidos

Garantindo assim uma visão geral do funcionamento da lanchonete

## 🚀 Tecnologias

Para o desenvolvimento do projeto foi utilizado:

- [Java](https://www.java.com/pt-BR/)
- [MySQL](https://www.mysql.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)

Lembrando que em momento algum foi utilizando um ORM ou alguma ferramenta que facilitasse a escrita de queries SQL

- E para a interface gráfica foi utilizado o [StreamLit](https://streamlit.io), para ter acesso ao código da interface gráfica, acesse o repositório [aqui](https://github.com/LucasGdBS/Carlitos_Interface)

## 📦 Instalação

Para rodar o projeto, é necessário ter o Java 22 e o MySQL instalados na máquina, e seguir os seguintes passos:

> Os passos a seguir serão realizados com o IntelliJ IDEA e com o DBeaver, recomendamos o uso do mesmo para seguir o tutorial de instalação

### Clonando o Projeto

1. Abra o IntelliJ IDEA
2. Crie um novo projeto a partir de um controle de Versão
3. Cole o link desse repositorio no campo de URL e clique em `Clone`
4. Abra o projeto
5. O proprio IntelliJ deve reconhecer que o projeto é um projeto Spring Boot, e irá baixar as dependências automaticamente através do Maven

> Caso o IntelliJ não reconheça o projeto como um projeto Spring Boot, clique com o botão direito no arquivo `BDCarlitosApllication.java` e clique em `Run`

### Configurando o Banco de Dados

1. Abra o DBeaver
2. Crie uma nova conexão com o MySQL (**Anota seu login e senha ein**)
3. Crie um novo arquivo SQL e cole o conteúdo do arquivo `CreateCarlitosDB.sql` que está na raiz do projeto
4. Selecione todo o conteúdo do arquivo e clique em `Execute Script` (O pergaminho na barra de lateral)
5. Pronto, o banco de dados foi criado, basta agora recarregar a conexão e você verá o banco `carlitosdb` criado

### Conectando o Projeto com o Banco de Dados

1. Voltando ao IntelliJ clique la no canto superior direito, onde tem um BdCarlitosApplication e clique em `Edit Configurations`
2. Clique em `Modify Options` e depois em `Environment Variables` vai ter um campo `Environment Variables` e clique na listinha ao lado com o nome de `Edit environment variables`
3. Clique no `+` e adicione as seguintes variáveis de ambiente:
   - `DATASOURCE_USERNAME` com o valor do seu usuário do MySQL
   - `DATASOURCE_PASSWORD` com o valor da sua senha do MySQL
4. Clique em `OK` e depois em `Apply` e `OK` novamente

### Rodando o Projeto

1. Clique no botão de play no canto superior direito do IntelliJ
2. O projeto irá compilar e rodar, e você verá no console a mensagem `Started BdCarlitosApplication in X.XXX seconds`

🎉 Pronto, o projeto está rodando, para testar os endpoint da API rest pode usar o [insomnia](https://insomnia.rest/download), [PostMan](https://www.postman.com/downloads/) ou outra ferramenta de sua escolha.

> Para acessar a interface gráfica do projeto, basta rodar o projeto [Carlitos_Interface](https://github.com/LucasGdBS/Carlitos_Interface)

## 📚 Documentação

⚠️ **Ainda não implementada**

~~Para acessar a documentação da API, basta acessar o link `http://localhost:8080/swagger-ui.html` no seu navegador~~
