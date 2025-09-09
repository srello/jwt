package dev.srello.cocinillas.cookie.service;

import dev.srello.cocinillas.BaseTestClass;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static dev.srello.cocinillas.cookie.service.CookieServiceImpl.SAME_SITE_COOKIE_ATTRIBUTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class CookieServiceTest extends BaseTestClass {

    public static final String COOKIE_MOCK_KEY = "mock_key";
    public static final String COOKIE_MOCK_VALUE = "mock_value";
    public static final String COOKIE_SAME_SITE = "Lax";
    public static final Integer COOKIE_MAX_AGE = 86400;
    @InjectMocks
    CookieServiceImpl cookieService;

    @BeforeEach
    void init() {
        setField(cookieService, "sameSiteCookies", COOKIE_SAME_SITE);
        setField(cookieService, "securedCookies", true);
        setField(cookieService, "maxAgeCookies", COOKIE_MAX_AGE);
    }

    @Test
    void shouldSucceed_createCookie() {
        Cookie cookie = cookieService.createCookie(COOKIE_MOCK_KEY, COOKIE_MOCK_VALUE);

        assertEquals(COOKIE_MOCK_VALUE, cookie.getValue());
        assertEquals(COOKIE_MOCK_KEY, cookie.getName());
        assertTrue(cookie.isHttpOnly());
        assertEquals(COOKIE_SAME_SITE, cookie.getAttribute(SAME_SITE_COOKIE_ATTRIBUTE));
        assertTrue(cookie.getSecure());
        assertEquals("/api/v1", cookie.getPath());
        assertEquals(COOKIE_MAX_AGE, cookie.getMaxAge());
    }

    @Test
    void shouldSucceed_getCookiesMap() {
        Cookie cookie1 = generateData(Cookie.class);
        Cookie cookie2 = generateData(Cookie.class);
        Cookie[] cookies = {cookie1, cookie2};

        Map<String, String> cookiesMap = cookieService.getCookiesMap(cookies);

        assertFalse(cookiesMap.isEmpty());
        assertNotNull(cookiesMap.get(cookie1.getName()));
        assertNotNull(cookiesMap.get(cookie2.getName()));
    }

    @Test
    void shouldSucceed_getCookiesMapCookieNull() {
        Cookie cookie1 = generateData(Cookie.class);
        Cookie cookie2 = generateData(Cookie.class);
        Cookie[] cookies = {cookie1, cookie2, null};

        Map<String, String> cookiesMap = cookieService.getCookiesMap(cookies);

        assertFalse(cookiesMap.isEmpty());
        assertNotNull(cookiesMap.get(cookie1.getName()));
        assertNotNull(cookiesMap.get(cookie2.getName()));
        assertEquals(2, cookiesMap.size());
    }

    @Test
    void shouldSucceed_getCookiesMapReturnsEmpty() {
        Map<String, String> cookiesMap = cookieService.getCookiesMap(null);

        assertTrue(cookiesMap.isEmpty());
    }

    @Test
    void shouldSucceed_deleteCookie() {
        Cookie cookie = cookieService.deleteCookie(COOKIE_MOCK_KEY);

        assertEquals(0, cookie.getMaxAge());
    }
}