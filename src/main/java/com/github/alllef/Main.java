package com.github.alllef;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.TourType;

public class Main {

    public static void main(String[] args) {
        TourDAO tourDAO = new TourDAO(ConnectionSingleton.getConnection());
        tourDAO.create(new Tour(3L, TourType.REST,2,2, HotelType.ALL_INCLUSIVE,3,false));
        System.out.println(tourDAO.findAll());
    }
}
