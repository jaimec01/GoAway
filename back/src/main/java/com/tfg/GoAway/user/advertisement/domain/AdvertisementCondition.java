package com.tfg.GoAway.user.advertisement.domain;

public enum AdvertisementCondition {
    GOOD("Good"),
    FAIR("Fair"),
    EXCELLENT("Excellent");

    private final String value;

    AdvertisementCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value; 
    }

    /**
     * Converts a string value to the corresponding AdvertisementCondition enum.
     * Throws an exception if the value is invalid.
     *
     * @param value the string value to map
     * @return the corresponding AdvertisementCondition enum
     */
    public static AdvertisementCondition fromValue(String value) {
        for (AdvertisementCondition condition : AdvertisementCondition.values()) {
            if (condition.value.equalsIgnoreCase(value)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Invalid AdvertisementCondition value: " + value);
    }
}
