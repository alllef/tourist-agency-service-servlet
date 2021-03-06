package com.github.alllef.model.service;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.exception.BackEndException;
import com.github.alllef.utils.enums.UserType;
import com.github.alllef.utils.validation.TourRequestValidation;
import com.github.alllef.utils.validation.TourValidation;

import java.sql.SQLException;
import java.util.List;

public class AdminService extends ManagerService {
    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            adminService = new AdminService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        }
        return adminService;
    }

    private final UserDAO userDAO;

    public AdminService(TourDAO tourDAO, TourRequestDAO tourRequestDAO, UserDAO userDAO) {
        super(tourDAO, tourRequestDAO);
        this.userDAO = userDAO;
    }

    public void createTour(Tour tour) {
        new TourValidation(tour).validate();
        try {
            tourDAO.create(tour);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public void updateTour(Tour tour) {
        try {
            tourDAO.findById(tour.getTourId())
                    .orElseThrow();

            new TourValidation(tour).validate();
            tourDAO.update(tour);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public void deleteTour(long tourId) {
        try {
            tourDAO.findById(tourId)
                    .orElseThrow();

            tourDAO.delete(tourId);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public void setUserBlocked(long userId) throws SQLException {
        User user = userDAO.findById(userId)
                .orElseThrow();

        User updated = user.toBuilder()
                .isBlocked(!user.isBlocked())
                .build();
        userDAO.update(updated);
    }

    public List<User> getClients() {
        try {
            return userDAO.findUsersByType(UserType.CLIENT);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }
}
