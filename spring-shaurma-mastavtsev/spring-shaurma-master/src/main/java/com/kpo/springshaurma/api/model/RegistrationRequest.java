package com.kpo.springshaurma.api.model;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String username;

    private String fullName;

    private String password;

    private String email;

    private String phone;
}
