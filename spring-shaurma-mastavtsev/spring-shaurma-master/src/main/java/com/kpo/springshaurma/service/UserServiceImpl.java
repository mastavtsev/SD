package com.kpo.springshaurma.service;

import com.kpo.springshaurma.api.model.ApiProblemException;
import com.kpo.springshaurma.api.model.LoginRequest;
import com.kpo.springshaurma.api.model.RegistrationRequest;
import com.kpo.springshaurma.api.model.Response;
import com.kpo.springshaurma.mapper.UserMapper;
import com.kpo.springshaurma.model.ShaurmaUser;
import com.kpo.springshaurma.repository.UserRepository;
import com.kpo.springshaurma.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Response SIMPLE_SUCCESS_RESPONSE = createSimpleSuccessResponse();

    private static final int BCRYPT_ROUNDS = 13;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Value("${server.port}")
    private String serverPort;

    @Transactional
    public Response createUser(RegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "Registration Error",
                    null,
                    Collections.singletonMap("username",
                            "Пользователь с таким именем существует"));
        }

        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "Registration Error",
                    null,
                    Collections.singletonMap("email",
                            "Пользователь с такой почтой уже зарегестрирован"));
        }

        if (registrationRequest.getUsername().isEmpty() || registrationRequest.getPassword().isEmpty()) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "Registration Error",
                    null,
                    Collections.singletonMap("error",
                            "Нет логина или пароля"));
        }

        Response response = new Response();
        ShaurmaUser newShaurmaUser = new ShaurmaUser();

        newShaurmaUser.setPhone(registrationRequest.getPhone());
        newShaurmaUser.setUsername(registrationRequest.getUsername());
        newShaurmaUser.setPassword(BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt(BCRYPT_ROUNDS)));
        newShaurmaUser.setFullName(registrationRequest.getFullName());
        newShaurmaUser.setEmail(registrationRequest.getEmail());
        newShaurmaUser.setActive(false);
        newShaurmaUser.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(newShaurmaUser);


        response.setMessage(String.format(
                        "Hello, %s!\n" +
                        "Welcome to Shaurma Order. Please, visit next" +
                        " link: http://localhost:%s/user/activate/%s to activate your profile",
                newShaurmaUser.getFullName(),
                serverPort,
                newShaurmaUser.getActivationCode()
        ));

        response.setSuccess(true);
        return response;
    }

    @Transactional
    public Response activateUser(String code) {
        ShaurmaUser shaurmaUser = userRepository.findByActivationCode(code).orElseThrow(
                () -> new ApiProblemException(
                                HttpStatus.NOT_FOUND, "Not found user", null, Collections.emptyMap()));

        shaurmaUser.setActivationCode(null);
        shaurmaUser.setActive(true);

        return SIMPLE_SUCCESS_RESPONSE;
    }

    @Override
    public Response resetPassword(String email) {
        return null;
    }

    @Transactional
    @Override
    public com.kpo.springshaurma.api.model.User login(LoginRequest loginRequest) {
        ShaurmaUser shaurmaUser = userRepository.findByEmail(loginRequest.getLogin()).orElseGet(
                () -> userRepository.findByUsername(loginRequest.getLogin()).orElseThrow(
                        () ->
                                new ApiProblemException(
                                        HttpStatus.CONFLICT,
                                        "User Error",
                                        null,
                                        Collections.singletonMap("error", "Неверный логин или пароль"))
                )
        );

        if (!checkPassword(shaurmaUser.getPassword(), loginRequest.getPassword())) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "User Error",
                    null,
                    Collections.singletonMap("error", "Неверный логин или пароль"));
        }

        if (!shaurmaUser.getActive()) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "User Error",
                    null,
                    Collections.singletonMap("error", "Пользователь не активирован"));

        }

        shaurmaUser.setAccessToken(UUID.randomUUID().toString());
        shaurmaUser.setActivationCode(null);

        return userMapper.userModel2UserDto(shaurmaUser);
    }

    @Override
    // Создаёт прокси для класса и реализует логику rollback'ов, если в процессе транзакции возникнет ошибка
    @Transactional(readOnly = true)
    public com.kpo.springshaurma.api.model.User checkToken(String token) {
        return userMapper.userModel2UserDto(
                userRepository.findByAccessTokenAndActive(token, true).orElseThrow(
                        () -> new ApiProblemException(
                                HttpStatus.NOT_FOUND,
                                "User Error",
                                null,
                                Collections.singletonMap("error", "Пользователь не найден"))
                ));
    }

    private static Response createSimpleSuccessResponse() {
        Response returnValue = new Response();

        returnValue.setSuccess(true);
        return returnValue;
    }

    public static boolean checkPassword(String passwordHash, String password) {
        String salt = passwordHash.substring(0, 30);

        String newHash = BCrypt.hashpw(password, salt);
        return passwordHash.equals(newHash);
    }
}
