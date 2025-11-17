package dev.srello.cocinillas.core.codes.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Codes {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Error {
        public static final String LOGIN_CREDENTIALS_WRONG_CODE = "001";
        public static final String USER_NOT_FOUND_CODE = "002";
        public static final String USER_PARAMETERS_ERROR_CODE = "003";
        public static final String USER_PASSWORD_DO_NOT_MATCH_CODE = "004";
        public static final String USER_NOT_ENABLED_CODE = "005";
        public static final String USER_ALREADY_ENABLED_CODE = "006";
        public static final String USER_ADMIN_CANNOT_DELETE_OWN_ACCOUNT_CODE = "007";
        public static final String USER_WITH_THIS_EMAIL_EXISTS_CODE = "008";
        public static final String USER_NOT_AUTHENTICATED_CODE = "009";
        public static final String TOKEN_GENERATION_ERROR_CODE = "010";
        public static final String TOKEN_VALIDATION_ERROR_CODE = "011";
        public static final String TOKEN_INVALID_CODE = "012";
        public static final String TOKEN_NOT_FOUND_CODE = "013";
        public static final String TOKEN_NOT_PARSEABLE_CODE = "014";
        public static final String ERROR_TOKEN_NOT_PROVIDED_CODE = "015";
        public static final String RESOURCE_DELETION_NOT_ALLOWED_CODE = "016";
        public static final String ERROR_CALLING_API_CODE = "018";
        public static final String ERROR_PROCESSING_DATA_CODE = "019";
        public static final String RUNTIME_EXCEPTION_CODE = "020";
        public static final String HASH_ALGORITHM_ERROR_CODE = "021";
        public static final String SHIFT_LIST_NULL_OR_EMPTY_CODE = "022";
        public static final String GENERIC_ERROR_CODE = "023";
        public static final String ORIGIN_OR_REFERER_HEADER_MISSING_CODE = "024";
        public static final String EMAIL_SEND_ERROR_CODE = "025";

        //RECIPES
        public static final String RECIPE_NOT_FOUND_CODE = "026";
        public static final String RECIPE_INTERACTION_ALREADY_EXISTS_CODE = "027";
        public static final String RECIPE_INTERACTION_NOT_FOUND_CODE = "028";

        //MEALS
        public static final String MEAL_NOT_FOUND_CODE = "029";
        public static final String MEALS_CAN_NOT_BE_EMPTY_CODE = "030";

        //TAGS
        public static final String TAG_NOT_FOUND_CODE = "031";

        //MENUS
        public static final String MENU_NOT_FOUND_CODE = "032";
    }
}
