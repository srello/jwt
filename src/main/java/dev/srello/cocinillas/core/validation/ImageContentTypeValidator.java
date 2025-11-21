package dev.srello.cocinillas.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

public class ImageContentTypeValidator implements ConstraintValidator<ValidContentTypes, List<String>> {

    private static final Set<String> allowedContentTypes = Set.of("image/jpeg", "image/png");

    @Override
    public boolean isValid(List<String> contentTypes, ConstraintValidatorContext context) {
        return isNull(contentTypes) || allowedContentTypes.containsAll(contentTypes);
    }
}

