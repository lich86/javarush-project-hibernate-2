package com.javarush.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (rating == null)
            return null;
        return rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String value) {
        return Rating.getByValue(value);
    }
}
