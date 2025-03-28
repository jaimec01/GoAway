package com.tfg.GoAway.user.advertisement.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Builder
@Data
@Getter
@Setter
public class Advertisement {

    private final String id;

    private final String title;

    private final String description;

    private final AdvertisementCategory advertisementCategory;

    private  List<AdvertisementPhoto> photos; 

    private final AdvertisementCondition advertisementCondition;

    private String userEmail;

    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isFavorite;

    private Boolean active;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Advertisement other = (Advertisement) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
