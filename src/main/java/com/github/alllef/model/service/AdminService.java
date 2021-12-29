package com.github.alllef.model.service;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.User;
import com.github.alllef.utils.enums.UserType;

import java.sql.SQLException;
import java.util.List;

public class AdminService extends ManagerService {
    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            adminService = new AdminService(daoFactory.getTourDAO(),daoFactory.getTourRequestDAO(),daoFactory.getUserDAO());
        }
        return adminService;
    }

    private final UserDAO userDAO;

    public AdminService(TourDAO tourDAO, TourRequestDAO tourRequestDAO, UserDAO userDAO) {
        super(tourDAO, tourRequestDAO);
        this.userDAO = userDAO;
    }

    public void createTour(Tour tour) {
        tourDAO.create(tour);
    }

    public void updateTour(Tour tour) {
        tourDAO.update(tour);
    }

    public void deleteTour(long tourId) {
        tourDAO.delete(tourId);
    }

    public void setUserBlocked(long userId) throws SQLException {
        User user = userDAO.findById(userId).orElseThrow();
        User updated = user.toBuilder()
                .isBlocked(!user.isBlocked())
                .build();
        userDAO.update(updated);
    }

    public List<User> getClients() {
        return userDAO.findUsersByType(UserType.CLIENT);
    }
}
