package dev.srello.cocinillas.core.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Error {
        public static final String LOGIN_CREDENTIALS_WRONG = "Username/password doesn't match";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String USER_PARAMETERS_ERROR = "Error in user parameters";
        public static final String USER_PASSWORD_DO_NOT_MATCH = "User+password doesn't match";
        public static final String USER_NOT_ENABLED = "User not enabled";
        public static final String USER_ALREADY_ENABLED = "User already enabled";
        public static final String USER_ADMIN_CANNOT_DELETE_OWN_ACCOUNT = "Admin cannot delete their own account";
        public static final String USER_WITH_THIS_EMAIL_EXISTS = "User with this email already exists";
        public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
        public static final String RESOURCE_DELETION_NOT_ALLOWED = "User can not delete this resource.";
        public static final String TOKEN_GENERATION_ERROR = "Error generating token. Try again later.";
        public static final String TOKEN_VALIDATION_ERROR = "Error generating token. Try again later.";
        public static final String TOKEN_INVALID = "Token invalid";
        public static final String TOKEN_NOT_FOUND = "Token not found";
        public static final String TOKEN_NOT_PARSEABLE = "Token not parseable";
        public static final String ERROR_TOKEN_NOT_PROVIDED = "Please provide auth token";
        public static final String ERROR_CALLING_API = "Error calling API";
        public static final String ERROR_PROCESSING_DATA = "Error processing data";
        public static final String RUNTIME_EXCEPTION = "Runtime exception";
        public static final String HASH_ALGORITHM_ERROR = "Hash algorithm error";
        public static final String SHIFT_LIST_NULL_OR_EMPTY = "Shift list is null or empty";
        public static final String GENERIC_ERROR = "Something went wrong. Please try again later, or contact support.";
        public static final String ORIGIN_OR_REFERER_HEADER_MISSING = "Requests without Origin or Referer are not allowed";
        public static final String EMAIL_SEND_ERROR = "There was an error while trying to send the email. Try again later.";

        //RECIPES
        public static final String RECIPE_NOT_FOUND = "Recipe with id %s not found.";
        public static final String RECIPE_INTERACTION_ALREADY_EXISTS = "Recipe interaction already exists.";
        public static final String RECIPE_INTERACTION_NOT_FOUND = "Recipe interaction not found.";

        //MEALS
        public static final String MEAL_NOT_FOUND = "Meal not found.";

        //TAGS
        public static final String TAG_NOT_FOUND = "Tag not found.";

        //MENUS
        public static final String MENU_NOT_FOUND = "Menu not found.";


    }
}
