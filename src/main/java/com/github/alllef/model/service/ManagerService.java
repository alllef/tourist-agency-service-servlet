package com.github.alllef.model.service;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.utils.enums.RequestStatus;
import com.github.alllef.utils.enums.TourType;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class ManagerService {
    private static ManagerService managerService;

    public static ManagerService getInstance() {
        if (managerService == null) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            managerService = new ManagerService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO());
        }
        return managerService;
    }

    protected final TourDAO tourDAO;
    protected final TourRequestDAO tourRequestDAO;


    public void updateTour(long tourId, int maxDiscount, boolean isBurning) throws SQLException {
        Tour tour = tourDAO.findById(tourId).orElseThrow();
        Tour updated = tour.toBuilder()
                .maxDiscount(maxDiscount)
                .isBurning(isBurning)
                .build();
        tourDAO.update(updated);
    }

    public Tour getTourById(long tourId) throws SQLException {
        return tourDAO.findById(tourId).orElseThrow();
    }

    public void updateTourRequest(long tourRequestId, int discount, RequestStatus requestStatus) throws SQLException {
        TourRequest tourRequest = tourRequestDAO.findById(tourRequestId).orElseThrow();

        TourRequest tourRequestUpdated = tourRequest.toBuilder()
                .discount(discount)
                .requestStatus(requestStatus)
                .build();

        tourRequestDAO.update(tourRequestUpdated);
    }

}
