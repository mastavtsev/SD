package com.kpo.springshaurma.api.model;

import lombok.Data;

@Data
public class User {

    private String fullName;

    private String username;

    private String email;

    private String phone;

    private String accessToken;
}
