package dev.srello.cocinillas.auth.service.transformer;

import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
import lombok.NonNull;

public interface AuthServiceTransformer {
    UserIDTO toUserIDTO(@NonNull RegisterIDTO registerIDTO);
}
