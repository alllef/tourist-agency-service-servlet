package com.github.alllef.model.service;

import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.utils.enums.TourType;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class ManagerService {
    protected final TourDAO tourDAO;
    protected final TourRequestDAO tourRequestDAO;

    public void updateTour(long id, int maxDiscount, boolean isBurning) throws SQLException {
        Tour tour = tourDAO.findById(id).orElseThrow();
        Tour updated = tour.toBuilder()
                .maxDiscount(maxDiscount)
                .isBurning(isBurning)
                .build();
        tourDAO.update(updated);
    }

    public void setTourRequestDiscount(TourRequest tourRequest, int discount) {
        TourRequest tourRequestDiscount = tourRequest.toBuilder()
                .discount(discount)
                .build();

        tourRequestDAO.update(tourRequestDiscount);
    }

}
