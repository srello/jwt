package dev.srello.cocinillas.core.request;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RequestConstants {
    public static final String AUTHORIZATION_REQUEST_ATTRIBUTE = "Authorization";
    public static final String REFRESH_REQUEST_ATTRIBUTE = "Refresh";
    public static final String ENDPOINT_ATTRIBUTE = "endpoint";
    public static final String ID_PATH_VARIABLE = "/{id:[0-9]+}";
    public static final String MULTIPLE_PATH_VARIABLE = "/multiple";
    public static final String ME_PATH_VARIABLE = "/me";
}
