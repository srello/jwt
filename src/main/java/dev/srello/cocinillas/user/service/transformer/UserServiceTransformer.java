package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import dev.srello.cocinillas.user.model.User;
import lombok.NonNull;

public interface UserServiceTransformer {
    UserODTO toODTO(@NonNull User user);

    User toUser(@NonNull UserIDTO userIDTO);

    User toUser(@NonNull UserUpdateIDTO userUpdateIDTO);
}
