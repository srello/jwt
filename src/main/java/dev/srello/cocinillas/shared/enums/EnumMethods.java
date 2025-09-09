package dev.srello.cocinillas.shared.enums;

public interface EnumMethods {


    default boolean equalsAny(Object... objects) {
        for (Object o : objects)
            if (this == o) return true;
        return false;
    }

}
