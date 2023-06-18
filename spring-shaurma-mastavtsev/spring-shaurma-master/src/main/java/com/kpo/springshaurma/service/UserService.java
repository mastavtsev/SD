package com.kpo.springshaurma.service;


import com.kpo.springshaurma.api.model.LoginRequest;
import com.kpo.springshaurma.api.model.RegistrationRequest;
import com.kpo.springshaurma.api.model.Response;
import com.kpo.springshaurma.api.model.User;

public interface UserService {

    Response createUser(RegistrationRequest user);

    Response activateUser(String code);

    Response resetPassword(String email);

    User login(LoginRequest loginRequest);

    User checkToken(String token);
}
