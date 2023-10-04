package com.javarush.model;

import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
public enum Rating {
    PG ("PG"),
    G ("G"),
    NC17 ("NC-17"),
    PG13 ("PG-13"),
    R ("R");

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    public static Rating getByValue(String value) {
        if (isNull(value) || value.isEmpty())
            return null;

        for (Rating rating : Rating.values()) {
            if (rating.value.equals(value)) {
                return rating;
            }
        }

        return null;
    }
}
