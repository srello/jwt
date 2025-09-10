package dev.srello.cocinillas.auth.service.transformer;

import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import lombok.NonNull;

public interface AuthServiceTransformer {
    UserIDTO toUserIDTO(@NonNull RegisterIDTO registerIDTO);

    UserUpdateIDTO toUserUpdateIDTO(@NonNull UserODTO userODTO);
}
