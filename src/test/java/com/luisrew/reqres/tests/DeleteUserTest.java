package com.luisrew.reqres.tests;

import com.luisrew.reqres.services.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DELETE /users/{id} — remoção de usuário")
class DeleteUserTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("deletar um usuário existente retorna 204 sem corpo")
    void deletaUsuarioComSucesso() {
        Response response = userService.deleteUser(2);

        assertThat(response.statusCode()).isEqualTo(204);
        assertThat(response.getBody().asString()).isEmpty();
    }
}
