package com.luisrew.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa um usuário como a API do reqres.in devolve.
 *
 * @JsonIgnoreProperties(ignoreUnknown = true) é importante aqui: a API às
 * vezes inclui campos extras na resposta (ex.: metadados promocionais) que
 * não nos interessam. Sem essa anotação, o Jackson falharia ao encontrar um
 * campo no JSON que não existe nesta classe.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private int id;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
