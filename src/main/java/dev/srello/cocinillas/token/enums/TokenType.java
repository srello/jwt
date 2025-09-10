package dev.srello.cocinillas.token.enums;

import dev.srello.cocinillas.shared.enums.EnumMethods;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType implements EnumMethods {
    ANONYMOUS("x-anonymous-token"),
    AUTHORIZATION("x-auth-token"),
    REFRESH("x-refresh-token"),
    RECOVERY("x-recovery-token"),
    SIGNATURE("x-signature-token"),
    CONFIRM("x-confirm-token");

    private final String key;
}
