package dev.srello.cocinillas.core.authorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorities {
    public static final String USER_LEVEL = "hasAnyRole('USER', 'ADMIN')";
    public static final String ADMIN_LEVEL = "hasAnyRole('ADMIN')";
}
