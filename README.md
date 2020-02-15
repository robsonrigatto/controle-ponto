# Tete de Nivelamento - Itaú Mastertech

##Tecnologias utilizadas:

* Java 8
* Maven
* Spring Boot
* H2 Database
* Swagger UI

##Subindo a aplicação

Após clonar o repositório, é necessário executar o comando:

```mvn spring:boot run```

Após isso, é possível acessar a documentação da API e testar pelo [Swagger](http://localhost:8080/controle-ponto/swagger-ui.html).
Todos os endpoints estão documentados na ferramenta. Caso 

##Endpoints - Aluno

* `GET /alunos`: lista de todos os alunos, ordenados pelo nome
* `GET /alunos/{id}`: retorna os dados do aluno com o ID informado
* `POST /alunos/{id}`: cria o aluno com os dados informados (nome, cpf e/ou e-mail) e, em seguida, retornando o registro criado
* `PATCH /alunos/{id}`: atualiza os dados do aluno (nome, cpf e/ou e-mail) passando o ID
* `DELETE /alunos/{id}`: exclui os dados do aluno com o ID informado

##Endpoints - Ponto

* `GET /alunos/{id}/pontos`: lista de todos os pontos batidos pelo aluno com ID informado, ordenados pela data de registro
* `POST /alunos/{id}/pontos`: registra um ponto do aluno com o ID informado

##Regras de Negócios

* Não é possível apagar um aluno que possua algum registro de ponto
* A API possui a inteligência de saber se o ponto a ser registrado é entrada ou saída
