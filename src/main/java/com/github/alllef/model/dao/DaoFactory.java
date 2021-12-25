package com.github.alllef.model.dao;

import com.github.alllef.model.ConnectionSingleton;

public class DaoFactory {
    private static DaoFactory daoFactory = null;

    public static DaoFactory getInstance() {
        if (daoFactory == null)
            daoFactory = new DaoFactory();

        return daoFactory;
    }

    private DaoFactory(){}

    public TourDAO getTourDAO() {
        return TourDAO.getInstance();
    }

    public TourRequestDAO getTourRequestDAO() {
        return TourRequestDAO.getInstance();
    }

    public UserDAO getUserDAO() {
        return UserDAO.getInstance();
    }
}
