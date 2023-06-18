package com.kpo.springshaurma.util;

import com.kpo.springshaurma.model.ShaurmaUser;
import lombok.Data;

public class SessionHelper {

    private SessionHelper() {
    }

    private static final ThreadLocal<ShaurmaUser> THREAD_LOCAL_USER = new ThreadLocal<>();

    public static void setUser(ShaurmaUser shaurmaUser) {
        THREAD_LOCAL_USER.set(shaurmaUser);
    }

    public static ShaurmaUser getUser() {
        return THREAD_LOCAL_USER.get();
    }

    @Data
    public static class HttpInfo {

        private String scheme;

        private String host;

        private Integer port;

        private String remoteIp;
    }
}
