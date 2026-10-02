package com.luisrew.reqres.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Centraliza a configuração compartilhada por todas as chamadas de API:
 * URL base e logs automáticos em caso de falha. Cada Service usa essa
 * "RequestSpecification" em vez de repetir `.baseUri(...)` em todo lugar.
 */
public final class ApiConfig {

    private static final String BASE_URI = "https://reqres.in/api";

    private ApiConfig() {
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                // Só imprime a requisição/resposta no console quando um
                // assert falha — mantém o log limpo quando está tudo bem,
                // mas dá contexto imediato quando algo quebra.
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)
                .build();
    }
}
