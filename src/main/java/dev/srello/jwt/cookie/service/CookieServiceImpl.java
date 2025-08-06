package dev.srello.jwt.cookie.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.ofNullable;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {
    public static final String SAME_SITE_COOKIE_ATTRIBUTE = "SameSite";
    @Value("${server.servlet.session.cookie.same-site:Lax}")
    private String sameSiteCookies;
    @Value("${server.servlet.session.cookie.secure:true}")
    private boolean securedCookies;
    @Value("${server.servlet.session.cookie.max-age:86400}")
    private Integer maxAgeCookies;
    @Override
    public Cookie createCookie(String key, String value) {
        var cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setAttribute(SAME_SITE_COOKIE_ATTRIBUTE, sameSiteCookies);
        cookie.setSecure(securedCookies);
        cookie.setPath("/api/v1");
        cookie.setMaxAge(maxAgeCookies);
        return cookie;
    }

    @Override
    public Map<String, String> getCookiesMap(Cookie[] cookies){
        return ofNullable(cookies)
                .flatMap(Arrays::stream)
                .collect(toMap(Cookie::getName, Cookie::getValue));
    }

    @Override
    public Cookie deleteCookie(String key) {
        Cookie cookie = createCookie(key, null);
        cookie.setMaxAge(0);
        return cookie;
    }
}
