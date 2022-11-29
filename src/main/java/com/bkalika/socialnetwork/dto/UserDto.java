package com.bkalika.socialnetwork.dto;

/**
 * @author @bkalika
 */
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String login;
    String token;

    public UserDto(Long id, String firstName, String login) {
        this.id = id;
        this.firstName = firstName;
        this.login = login;
    }

    public UserDto(Long id, String firstName, String lastName, String login, String token) {
        this(id, firstName, login);
        this.lastName = lastName;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
