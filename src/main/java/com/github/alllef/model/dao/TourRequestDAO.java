package com.github.alllef.model.dao;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.entity.User;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.RequestStatus;
import com.github.alllef.utils.enums.TourType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TourRequestDAO extends AbstractDAO<TourRequest> {
    private static TourRequestDAO tourDAO = null;

    protected static TourRequestDAO getInstance() {
        if (tourDAO == null)
            tourDAO = new TourRequestDAO(ConnectionSingleton.getConnection());

        return tourDAO;
    }

    private TourRequestDAO(Connection connection) {
        super("tour_requests", connection);
    }

    @Override
    public Optional<TourRequest> findById(long id) throws SQLException {
        String findById = "select * from tour_requests where tour_request_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(findById)) {
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                return Optional.of(mapToEntity(resultSet));
        }
        return Optional.empty();
    }

    @Override
    public void update(TourRequest entity) throws SQLException {
        String updatePositionSql = String.format("""
                UPDATE %s
                 SET user_id=?,
                 request_status=?,
                 discount=?,
                 tour_id=?
                 WHERE tour_request_id=?""", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(updatePositionSql)) {
            pstmt.setLong(1, entity.getUserId());
            pstmt.setString(2, entity.getRequestStatus().toString());
            pstmt.setInt(3, entity.getDiscount());
            pstmt.setLong(4, entity.getTourId());
            pstmt.setLong(5, entity.getTourRequestId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void create(TourRequest entity) throws SQLException {
        String insertSQL = String.format("""
                insert into %s(user_id,request_status,discount,tour_id)
                  values(?,?,?,?)""", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setLong(1, entity.getUserId());
            pstmt.setString(2, entity.getRequestStatus().toString());
            pstmt.setInt(3, 0);
            pstmt.setLong(4, entity.getTourId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String deleteQuery = String.format("DELETE FROM %s where tour_request_id=?", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }


    public Map<TourRequest, Tour> findTourRequestsWithToursByUser(long userId) throws SQLException {
        String joinSql = """
                select * from tour_requests tr
                 join tours t using(tour_id)
                  where tr.user_id =?""";

        Map<TourRequest, Tour> tourMap = new HashMap<>();

        try (PreparedStatement pstmt = con.prepareStatement(joinSql)) {
            pstmt.setLong(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    TourRequest tourRequest = mapToEntity(resultSet);
                    Tour tour = Tour.builder()
                            .tourId(resultSet.getLong("tour_id"))
                            .maxDiscount(resultSet.getInt("max_discount"))
                            .tourType(TourType.valueOf(resultSet.getString("tour_type")))
                            .hotelType(HotelType.valueOf(resultSet.getString("hotel_type")))
                            .peopleNumber(resultSet.getInt("people_number"))
                            .price(resultSet.getInt("tour_price"))
                            .isBurning(resultSet.getBoolean("is_burning"))
                            .build();
                    tourMap.put(tourRequest, tour);
                }
            }
        }
        return tourMap;
    }

    public Map<TourRequest, Tour> findTourRequestsWithTours() throws SQLException {
        String joinSql = """
                select * from tour_requests tr
                 join tours t using(tour_id)""";

        Map<TourRequest, Tour> tourMap = new HashMap<>();

        try (PreparedStatement pstmt = con.prepareStatement(joinSql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    TourRequest tourRequest = mapToEntity(resultSet);
                    Tour tour = Tour.builder()
                            .tourId(resultSet.getLong("tour_id"))
                            .maxDiscount(resultSet.getInt("max_discount"))
                            .tourType(TourType.valueOf(resultSet.getString("tour_type")))
                            .hotelType(HotelType.valueOf(resultSet.getString("hotel_type")))
                            .peopleNumber(resultSet.getInt("people_number"))
                            .price(resultSet.getInt("tour_price"))
                            .isBurning(resultSet.getBoolean("is_burning"))
                            .build();
                    tourMap.put(tourRequest, tour);
                }
            }
        }
        return tourMap;
    }

    @Override
    public TourRequest mapToEntity(ResultSet resultSet) throws SQLException {
        return TourRequest.builder()
                .userId(resultSet.getLong("user_id"))
                .requestStatus(RequestStatus.valueOf(resultSet.getString("request_status")))
                .discount(resultSet.getInt("discount"))
                .tourId(resultSet.getLong("tour_id"))
                .tourRequestId(resultSet.getLong("tour_request_id"))
                .build();
    }
}
