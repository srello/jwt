package dev.srello.jwt.cookie.service;

import jakarta.servlet.http.Cookie;

import java.util.Map;

public interface CookieService {
    Cookie createCookie(String key, String value);

    Map<String, String> getCookiesMap(Cookie[] cookies);

    Cookie deleteCookie(String name);
}
