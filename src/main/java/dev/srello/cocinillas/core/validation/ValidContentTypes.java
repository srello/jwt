package dev.srello.cocinillas.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageContentTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContentTypes {
    String message() default "Invalid image content type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
