package com.tfg.GoAway.modules.advertisement.domain;

public enum AdvertisementCategory {
    CHAIR("chair"),
    TABLE("table"),
    TV("TV"),
    CHESTOFDRAWERS("chestOfDrawers"),
    SOFA("sofa"),
    BOOKSHELF("bookshelf"),
    OTHER("other");

    private final String value;

    AdvertisementCategory(String value) {
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
     * Converts a string value to the corresponding AdvertisementCategory enum.
     * Throws an exception if the value is invalid.
     *
     * @param value the string value to map
     * @return the corresponding AdvertisementCategory enum
     */
    public static AdvertisementCategory fromValue(String value) {
        for (AdvertisementCategory category : AdvertisementCategory.values()) {
            if (category.value.equalsIgnoreCase(value) || category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid AdvertisementCategory value: " + value);
    }
    
    
}
