package com.luisrew.reqres.tests;

import com.luisrew.reqres.models.AuthRequest;
import com.luisrew.reqres.models.AuthResponse;
import com.luisrew.reqres.services.AuthService;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("POST /register — criação de cadastro com e-mail/senha")
class RegisterTest {

    private final AuthService authService = new AuthService();

    // Único e-mail com o qual a API de testes do reqres.in aceita registro
    // com sucesso — está documentado assim de propósito pela própria API,
    // não é uma credencial "secreta" nem específica deste projeto.
    private static final String EMAIL_VALIDO_PARA_REGISTRO = "eve.holt@reqres.in";

    @Test
    @DisplayName("registro com e-mail e senha válidos retorna 200 com id e token")
    void registraComSucesso() {
        AuthRequest request = new AuthRequest(EMAIL_VALIDO_PARA_REGISTRO, "pistol");

        Response response = authService.register(request);

        assertThat(response.statusCode()).isEqualTo(200);

        AuthResponse body = response.as(AuthResponse.class);
        assertThat(body.getId()).isNotNull();
        assertThat(body.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("registro sem senha retorna 400 com mensagem de erro")
    void registraSemSenhaRetornaErro() {
        AuthRequest request = new AuthRequest(EMAIL_VALIDO_PARA_REGISTRO, null);

        Response response = authService.register(request);

        assertThat(response.statusCode()).isEqualTo(400);

        AuthResponse body = response.as(AuthResponse.class);
        assertThat(body.getError()).isEqualTo("Missing password");
    }
}
