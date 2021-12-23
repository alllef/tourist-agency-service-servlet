package com.github.alllef.model.service;

import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;

public class AdminService extends ManagerService {
    private final UserDAO userDAO;

    public AdminService(TourDAO tourDAO, TourRequestDAO tourRequestDAO, UserDAO userDAO) {
        super(tourDAO, tourRequestDAO);
        this.userDAO = userDAO;
    }

    public void createTour(Tour tour) {
        tourDAO.create(tour);
    }

    public void deleteTour(Tour tour) {
        tourDAO.delete(tour.getTourId());
    }

    public void setUserBlocked(User user, boolean isBlocked) {
        User user1 = user.toBuilder()
                .isBlocked(isBlocked)
                .build();
    }
}
