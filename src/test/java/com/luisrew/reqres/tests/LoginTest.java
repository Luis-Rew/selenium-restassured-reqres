package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.AuthRequest;
import com.luisrew.reqres.models.AuthResponse;
import com.luisrew.reqres.services.AuthService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("POST /login — autenticação")
class LoginTest {

    private final AuthService authService = new AuthService();
    private static final String EMAIL_VALIDO_PARA_LOGIN = "eve.holt@reqres.in";

    @Test
    @DisplayName("login com credenciais válidas retorna 200 com token")
    void logaComSucesso() {
        AuthRequest request = new AuthRequest(EMAIL_VALIDO_PARA_LOGIN, "cityslicka");

        Response response = authService.login(request);

        assertThat(response.statusCode()).isEqualTo(200);

        AuthResponse body = response.as(AuthResponse.class);
        assertThat(body.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("login sem senha retorna 400 com mensagem de erro")
    void logaSemSenhaRetornaErro() {
        AuthRequest request = new AuthRequest(EMAIL_VALIDO_PARA_LOGIN, null);

        Response response = authService.login(request);

        assertThat(response.statusCode()).isEqualTo(400);

        AuthResponse body = response.as(AuthResponse.class);
        assertThat(body.getError()).isEqualTo("Missing password");
    }
}
