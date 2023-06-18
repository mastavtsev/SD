package com.kpo.springshaurma.filter;

import com.kpo.springshaurma.model.ShaurmaUser;
import com.kpo.springshaurma.repository.UserRepository;
import com.kpo.springshaurma.util.SessionHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
//@Component
@RequiredArgsConstructor
@Order(100)
public class LegacyAuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    private static final String HEADER_ACCESS_TOKEN = "Access-Token";

    private static final List<String> ignored =
            Arrays.asList(
                    "/user/login",
                    "/user/activate/**",
                    "/user/reset-password",
                    "/user/registration",
                    "/user/check-token"
            );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);

            return;
        }

        String path = req.getServletPath().toLowerCase();

        String userAccessToken = req.getHeader(HEADER_ACCESS_TOKEN);

        boolean tokenIgnore = ignored.contains(path);

        for (String el : ignored) {
            if (el.endsWith("/**")
                    && path.startsWith(
                    el.replaceFirst("/\\*\\*", ""))) {
                tokenIgnore = true;
            }
        }

        if (tokenIgnore) {
            log.debug("Не проверям для " + path);
            chain.doFilter(request, response);
            return;
        }

        Optional<ShaurmaUser> byAuthKey = userRepository.findByAccessTokenAndActive(userAccessToken, true);

        if (byAuthKey.isPresent()) {
            SessionHelper.setUser(byAuthKey.get());
            chain.doFilter(request, response);
            log.debug("Query for user " + byAuthKey.get().getEmail());
        } else {
            error401(response);
        }
    }

    private void error401(ServletResponse response) throws IOException {
        error(response, HttpServletResponse.SC_UNAUTHORIZED, err401);
    }

    private void error(ServletResponse response, int code, String text) throws IOException {
        HttpServletResponse httpResponse = ((HttpServletResponse) response);
        httpResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpResponse.setHeader("Transfer-Encoding", "chunked");
        httpResponse.setStatus(code);
        httpResponse.getWriter().write(text);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    private String err401 =
            ""
                    + "{\n"
                    + "  \"name\": \"Unauthorized\",\n"
                    + "  \"message\": \"Попытка соединения с неверным ключом.\",\n"
                    + "  \"code\": 0,\n"
                    + "  \"status\": 401\n"
                    + "}";
}

