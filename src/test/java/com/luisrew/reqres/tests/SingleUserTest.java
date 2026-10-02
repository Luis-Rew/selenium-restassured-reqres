package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.SingleUserResponse;
import com.luisrew.reqres.services.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GET /users/{id} — busca de um usuário específico")
class SingleUserTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("usuário existente retorna 200 com os dados dele")
    void usuarioExistenteEhEncontrado() {
        Response response = userService.getUser(2);

        assertThat(response.statusCode()).isEqualTo(200);

        SingleUserResponse body = response.as(SingleUserResponse.class);
        assertThat(body.getData().getId()).isEqualTo(2);
        assertThat(body.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
    }

    @Test
    @DisplayName("usuário inexistente retorna 404")
    void usuarioInexistenteRetorna404() {
        Response response = userService.getUser(23);

        assertThat(response.statusCode()).isEqualTo(404);
    }
}
