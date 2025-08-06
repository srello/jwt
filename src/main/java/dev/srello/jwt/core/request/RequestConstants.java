package dev.srello.jwt.core.request;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RequestConstants {
    public static final String AUTHORIZATION_REQUEST_ATTRIBUTE = "Authorization";
    public static final String REFRESH_REQUEST_ATTRIBUTE = "Refresh";
    public static final String ENDPOINT_ATTRIBUTE = "endpoint";
}
