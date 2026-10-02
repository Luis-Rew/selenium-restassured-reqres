package com.luisrew.reqres.services;

import com.luisrew.reqres.config.ApiConfig;
import com.luisrew.reqres.models.CreateUserRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * "Service Object": encapsula como conversar com os endpoints de /users,
 * para que os testes não precisem conhecer URLs, verbos HTTP ou detalhes de
 * requisição — só chamam métodos com nomes de ação, igual um Page Object
 * faz para páginas de UI.
 *
 * Cada método devolve a Response crua do RestAssured (não um objeto já
 * convertido) de propósito: assim o teste decide o que verificar — status
 * code, corpo, headers — sem a Service impor isso.
 */
public class UserService {

    public Response listUsers(int page) {
        return given()
                .spec(ApiConfig.requestSpec())
                .queryParam("page", page)
                .when()
                .get("/users");
    }

    public Response getUser(int id) {
        return given()
                .spec(ApiConfig.requestSpec())
                .when()
                .get("/users/{id}", id);
    }

    public Response createUser(CreateUserRequest request) {
        return given()
                .spec(ApiConfig.requestSpec())
                .body(request)
                .when()
                .post("/users");
    }

    public Response updateUser(int id, CreateUserRequest request) {
        return given()
                .spec(ApiConfig.requestSpec())
                .body(request)
                .when()
                .put("/users/{id}", id);
    }

    public Response deleteUser(int id) {
        return given()
                .spec(ApiConfig.requestSpec())
                .when()
                .delete("/users/{id}", id);
    }
}
