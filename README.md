# minsait-agenda-api

O desafio consiste em criar uma aplicação API Rest para gerenciar um sistema de  cadastro de Pessoas e seus respectivos Contatos, onde cada Pessoa pode ter vários Contatos. O  principal objetivo é permitir que operações CRUD (Criar, Ler, Atualizar, Deletar) sejam realizadas  na estrutura de Pessoas e Contatos. 

# Sobre o projeto

https://www.youtube.com/watch?v=gpsqASsm73Q

# Acessar o Swagger UI

http://localhost:8080/swagger-ui/index.html


# Tecnologias utilizadas
- Java Versão 17.0.2
- Spring Boot
- JPA / Hibernate
- Maven
- MySQL

# Como executar o projeto
- git clone https://github.com/yramonn/minsait-agenda-api.git
- Execute: mvn clean package install
- application.properties - Aplicação foi desenvolvida utilizando o MySQL. Crie um schema que vai receber a criação das tabelas quando á aplicação for inicializada. Configuere o spring.datasource.url, spring.datasource.username, spring.datasource.password com suas devidas Credenciais do seu MySQL. Todas tabelas serão criadas através do: spring.jpa.hibernate.ddl-auto=update.

# Autor

Ramon Silva
https://www.linkedin.com/in/ramon--silva/
