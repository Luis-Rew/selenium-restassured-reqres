package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.CreateUserRequest;
import com.luisrew.reqres.models.UpdateUserResponse;
import com.luisrew.reqres.services.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PUT /users/{id} — atualização de usuário")
class UpdateUserTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("atualizar um usuário existente retorna 200 com os novos dados")
    void atualizaUsuarioComSucesso() {
        CreateUserRequest request = new CreateUserRequest("Luis QA", "QA Lead");

        Response response = userService.updateUser(2, request);

        assertThat(response.statusCode()).isEqualTo(200);

        UpdateUserResponse body = response.as(UpdateUserResponse.class);
        assertThat(body.getName()).isEqualTo("Luis QA");
        assertThat(body.getJob()).isEqualTo("QA Lead");
        assertThat(body.getUpdatedAt()).isNotBlank();
    }
}
