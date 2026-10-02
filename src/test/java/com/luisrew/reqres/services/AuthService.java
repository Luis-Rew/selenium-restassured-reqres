package com.luisrew.reqres.services;

import com.luisrew.reqres.config.ApiConfig;
import com.luisrew.reqres.models.AuthRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public Response register(AuthRequest request) {
        return given()
                .spec(ApiConfig.requestSpec())
                .body(request)
                .when()
                .post("/register");
    }

    public Response login(AuthRequest request) {
        return given()
                .spec(ApiConfig.requestSpec())
                .body(request)
                .when()
                .post("/login");
    }
}
