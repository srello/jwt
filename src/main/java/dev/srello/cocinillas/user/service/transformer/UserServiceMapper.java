package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.AuthorODTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static java.util.Objects.nonNull;

@Mapper
public interface UserServiceMapper {
    UserODTO toUserODTO(User user);

    User toUser(UserIDTO userIDTO);

    User toUser(UserODTO userUpdateIDTO);

    @Mapping(target = "isUserAuthor", source = "author.id", qualifiedByName = "getIsUserAuthor")
    AuthorODTO toAuthorODTO(User author, @Context Long userId);

    @Named("getIsUserAuthor")
    default Boolean getIsUserAuthor(Long authorId, @Context Long userId) {
        return nonNull(userId) && userId.equals(authorId);
    }
}
