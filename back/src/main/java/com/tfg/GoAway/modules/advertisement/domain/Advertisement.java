package com.tfg.GoAway.modules.advertisement.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class Advertisement {

    private final String furnitureId;

    private final String description;

    private final AdvertisementCategory advertisementCategory;

    private final String photoUrls;

    private final AdvertisementCondition advertisementCondition;

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Advertisement other = (Advertisement) obj;
        if (furnitureId == null) {
            if (other.furnitureId != null)
                return false;
        } else if (!furnitureId.equals(other.furnitureId))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (advertisementCategory != other.advertisementCategory)
            return false;
        if (photoUrls == null) {
            if (other.photoUrls != null)
                return false;
        } else if (!photoUrls.equals(other.photoUrls))
            return false;
        if (advertisementCondition != other.advertisementCondition)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((furnitureId == null) ? 0 : furnitureId.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((advertisementCategory == null) ? 0 : advertisementCategory.hashCode());
        result = prime * result + ((photoUrls == null) ? 0 : photoUrls.hashCode());
        result = prime * result + ((advertisementCondition == null) ? 0 : advertisementCondition.hashCode());
        return result;
    }



    
}

