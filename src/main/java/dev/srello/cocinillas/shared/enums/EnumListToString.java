package dev.srello.cocinillas.shared.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.springframework.util.CollectionUtils.isEmpty;

@Converter
@RequiredArgsConstructor
public class EnumListToString<E extends Enum<E>> implements AttributeConverter<List<E>, String> {

    private static final String DELIMITER = ",";
    private final Class<E> enumClass;

    @Override
    public String convertToDatabaseColumn(List<E> eList) {
        if (isEmpty(eList)) return "";

        return eList.stream()
                .map(Enum::name)
                .collect(joining(DELIMITER));
    }

    @Override
    public List<E> convertToEntityAttribute(String s) {
        if (!StringUtils.hasLength(s))
            return emptyList();

        return Arrays.stream(s.split(DELIMITER))
                .map(name -> Enum.valueOf(enumClass, name))
                .toList();
    }
}
