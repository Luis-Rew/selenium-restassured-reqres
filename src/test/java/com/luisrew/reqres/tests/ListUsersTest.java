package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.ListUsersResponse;
import com.luisrew.reqres.services.UserService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GET /users — listagem paginada")
class ListUsersTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("retorna 200 e a página de usuários pedida")
    void listaUsuariosDaPagina() {
        Response response = userService.listUsers(2);

        assertThat(response.statusCode()).isEqualTo(200);

        ListUsersResponse body = response.as(ListUsersResponse.class);
        assertThat(body.getPage()).isEqualTo(2);
        assertThat(body.getData()).isNotEmpty();

        // Todo usuário da lista deve vir com os campos básicos preenchidos —
        // uma checagem "de contrato", não de valores exatos (que podem mudar).
        body.getData().forEach(user -> {
            assertThat(user.getId()).isPositive();
            assertThat(user.getEmail()).contains("@");
            assertThat(user.getFirstName()).isNotBlank();
        });
    }
}
