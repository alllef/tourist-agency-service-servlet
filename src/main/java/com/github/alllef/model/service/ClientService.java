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
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ClientService {
    private final TourDAO tourDAO;
    private final TourRequestDAO tourRequestDAO;
    private final UserDAO userDAO;

    public void createUser(User user) {
        userDAO.create(user);
    }

    public void orderTour(long tourId, long userId) {
        TourRequest tourRequest = TourRequest.builder()
                .tourId(tourId)
                .userId(userId)
                .requestStatus(RequestStatus.REGISTERED)
                .build();

        tourRequestDAO.create(tourRequest);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userDAO.findUserByEmail(email));
    }

    public List<Tour> filterTours(Map<String, String[]> parameters) {
        ToIntFunction<String> toInt = (str) -> Integer.parseInt(parameters.get(str)[0]);
        Predicate<String> containsParam = (str) -> parameters.containsKey(str) && !parameters.get(str)[0].isEmpty();

        Stream<Tour> tmpTourStream = tourDAO.findAll()
                .stream();

        boolean byTourType = containsParam.test("tour-type");
        boolean byMinPrice = containsParam.test("min-price");
        boolean byMaxPrice = containsParam.test("max-price");
        boolean byMinPeopleNumber = containsParam.test("min-people-number");
        boolean byMaxPeopleNumber = containsParam.test("max-people-number");
        boolean byHotelType = containsParam.test("hotel-type");

        if (byTourType)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getTourType().equals(TourType.valueOf(parameters.get("tour-type")[0])));

        if (byMinPrice)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getPrice() >= toInt.applyAsInt("min-price"));

        if (byMaxPrice)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getPrice() <= toInt.applyAsInt("max-price"));

        if (byMinPeopleNumber)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getPeopleNumber() >= toInt.applyAsInt("min-people-number"));

        if (byMaxPeopleNumber)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getPeopleNumber() <= toInt.applyAsInt("max-people-number"));

        if (byHotelType)
            tmpTourStream = tmpTourStream.filter(tour -> tour.getHotelType().equals(HotelType.valueOf(parameters.get("hotel-type")[0])));

        return tmpTourStream.sorted()
                .collect(Collectors.toList());
    }

    public Map<TourRequest, Tour> getRequestsWithToursByUser(User user) {
        return tourRequestDAO.findTourRequestsWithToursByUser(user.getUserId());
    }

    public Map<TourRequest,Tour> getRequestsWithTours(){
        return tourRequestDAO.findTourRequestsWithTours();
    }
}