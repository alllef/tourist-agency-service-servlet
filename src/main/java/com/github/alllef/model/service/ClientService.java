package com.github.alllef.model.service;

import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.RequestStatus;
import com.github.alllef.utils.enums.TourType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientService {
    private final TourDAO tourDAO;
    private final TourRequestDAO tourRequestDAO;
    private final UserDAO userDAO;

    public void createUser(User user) {
        userDAO.create(user);
    }

    public void orderTour(Tour tour) {
        TourRequest tourRequest = TourRequest.builder()
                .tourId(tour.getTourId())
                .requestStatus(RequestStatus.REGISTERED)
                .build();

        tourRequestDAO.create(tourRequest);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userDAO.findUserByEmail(email));
    }

    public List<Tour> filterTours(Map<String, String[]> parameters) {
        boolean byPrice = parameters.containsKey("min-price") && parameters.containsKey("max-price");
        ToIntFunction<String> toInt = (str) ->Integer.parseInt(parameters.get(str)[0]);

        if (byPrice) {
            return filterByPrice(toInt.applyAsInt("min-price"), toInt.applyAsInt("max-price"));
        } else
            return findAllTours();
    }

    private List<Tour> findAllTours() {
        return tourDAO.findAll();
    }

    private List<Tour> filterByType(TourType tourType) {
        return tourDAO.findAll()
                .stream()
                .filter(tour -> tour.getTourType().equals(tourType))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Tour> filterByPrice(int minPrice, int maxPrice) {
        return tourDAO.findAll()
                .stream()
                .filter(tour -> tour.getPrice() >= minPrice && tour.getPrice() <= maxPrice)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Tour> filterByPeopleNumber(int minPeopleNumber, int maxPeopleNumber) {
        return tourDAO.findAll()
                .stream()
                .filter(tour -> tour.getPeopleNumber() >= minPeopleNumber && tour.getPeopleNumber() <= maxPeopleNumber)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Tour> filterByHotelType(HotelType hotelType) {
        return tourDAO.findAll()
                .stream()
                .filter(tour -> tour.getHotelType().equals(hotelType))
                .sorted()
                .collect(Collectors.toList());
    }
}