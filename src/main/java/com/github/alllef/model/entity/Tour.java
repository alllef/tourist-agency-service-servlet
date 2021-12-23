package com.github.alllef.model.entity;

import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.TourType;
import lombok.*;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tour implements Comparable<Tour> {
    private Long tourId;
    private TourType tourType;
    private Integer price;
    private Integer maxDiscount;
    private HotelType hotelType;
    private Integer peopleNumber;
    private boolean isBurning;

    @Override
    public int compareTo(Tour o) {
        if (this.isBurning && !o.isBurning) return 1;
        else if (!this.isBurning && o.isBurning) return -1;
        else return 0;
    }
}
