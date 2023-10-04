package com.javarush.model;

import static java.util.Objects.isNull;

public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String value;

    SpecialFeature(String feature) {
        this.value = feature;
    }

    public String getValue() {
        return value;
    }

    public static SpecialFeature getFeatureByValue(String value) {
        if (isNull(value) || value.isEmpty())
            return null;

        for (SpecialFeature feature : SpecialFeature.values()) {
            if (feature.value.equals(value)) {
                return feature;
            }
        }
        return null;
    }


}
