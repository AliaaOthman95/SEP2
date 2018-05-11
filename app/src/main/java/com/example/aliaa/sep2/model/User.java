package com.example.aliaa.sep2.model;

public class User {
    private String name;
    private String email;
    private String password;
    private String creditCard;
    private Integer authorization ;



    public User(String userName, String userEmail, String userPassword,
                String userCreditCard , Integer authorization) {
        this.name = userName;
        this.email = userEmail;
        this.password = userPassword;
        this.creditCard = userCreditCard;

        this.authorization = authorization ;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }
}

