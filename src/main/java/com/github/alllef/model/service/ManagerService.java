package com.github.alllef.model.service;

import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.utils.enums.TourType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ManagerService {
    protected final TourDAO tourDAO;
    protected final TourRequestDAO tourRequestDAO;

    public void setBurningTour(Tour tour) {
        Tour burningTour = tour.toBuilder()
                .isBurning(true)
                .build();
        tourDAO.update(burningTour);
    }

    public void changeTourType(Tour tour, TourType tourType){
        Tour tourWithChangedType = tour.toBuilder()
                .tourType(tourType)
                .build();
        tourDAO.update(tourWithChangedType);
    }

    public void setTourRequestDiscount(TourRequest tourRequest, int discount) {
        TourRequest tourRequestDiscount = tourRequest.toBuilder()
                .discount(discount)
                .build();

        tourRequestDAO.update(tourRequestDiscount);
    }

    public void setTourMaxDiscount(Tour tour,int maxDiscount){
        Tour tourWithDiscount = tour.toBuilder()
                .maxDiscount(maxDiscount)
                .build();

        tourDAO.update(tourWithDiscount);
    }


}
