package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.CreateUserRequest;
import com.luisrew.reqres.models.CreateUserResponse;
import com.luisrew.reqres.services.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("POST /users — criação de usuário")
class CreateUserTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("criar um usuário com nome e cargo retorna 201 com os dados enviados + id gerado")
    void criaUsuarioComSucesso() {
        CreateUserRequest request = new CreateUserRequest("Luis QA", "QA Engineer");

        Response response = userService.createUser(request);

        assertThat(response.statusCode()).isEqualTo(201);

        CreateUserResponse body = response.as(CreateUserResponse.class);
        assertThat(body.getName()).isEqualTo("Luis QA");
        assertThat(body.getJob()).isEqualTo("QA Engineer");
        assertThat(body.getId()).isNotBlank();
        assertThat(body.getCreatedAt()).isNotBlank();
    }
}
