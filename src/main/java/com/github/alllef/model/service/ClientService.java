package com.github.alllef.model.service;

import com.github.alllef.model.dao.DaoFactory;
import com.github.alllef.model.dao.TourDAO;
import com.github.alllef.model.dao.TourRequestDAO;
import com.github.alllef.model.dao.UserDAO;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.model.exception.BackEndException;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.RequestStatus;
import com.github.alllef.utils.enums.TourType;
import com.github.alllef.utils.validation.UserValidation;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ClientService {
    private static ClientService clientService;

    public static ClientService getInstance() {
        if (clientService == null) {
            DaoFactory daoFactory = DaoFactory.getInstance();
            clientService = new ClientService(daoFactory.getTourDAO(), daoFactory.getTourRequestDAO(), daoFactory.getUserDAO());
        }
        return clientService;
    }

    private final TourDAO tourDAO;
    private final TourRequestDAO tourRequestDAO;
    private final UserDAO userDAO;

    public void createClient(User client) {
        new UserValidation(client).validate();
        try {
            userDAO.create(client);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public void orderTour(long tourId, long userId) {
        try {
            tourDAO.findById(tourId).orElseThrow();
            userDAO.findById(userId).orElseThrow();

            TourRequest tourRequest = TourRequest.builder()
                    .tourId(tourId)
                    .userId(userId)
                    .requestStatus(RequestStatus.REGISTERED)
                    .build();

            tourRequestDAO.create(tourRequest);
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(userDAO.findUserByEmail(email));
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
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
        try {
            userDAO.findById(user.getUserId()).orElseThrow();
            return tourRequestDAO.findTourRequestsWithToursByUser(user.getUserId());
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }

    public Map<TourRequest, Tour> getRequestsWithTours() {
        try {
            return tourRequestDAO.findTourRequestsWithTours();
        } catch (SQLException e) {
            throw new BackEndException(e.getMessage());
        }
    }
}