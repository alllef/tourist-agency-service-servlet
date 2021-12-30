package com.github.alllef.model.dao;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.entity.Tour;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.TourType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TourDAO extends AbstractDAO<Tour> {
    private static TourDAO tourDAO = null;

    protected static TourDAO getInstance() {
        if (tourDAO == null)
            tourDAO = new TourDAO(ConnectionSingleton.getConnection());

        return tourDAO;
    }

    private TourDAO(Connection connection) {
        super("tours", connection);
    }

    @Override
    public Optional<Tour> findById(long id) throws SQLException {
        String findById = "select * from tours where tour_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(findById)) {
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                return Optional.of(mapToEntity(resultSet));
        }
        return Optional.empty();
    }

    @Override
    public void update(Tour entity) throws SQLException {
        String updatePositionSql = String.format("""
                UPDATE %s
                 SET max_discount=?,
                 tour_type=?,
                 hotel_type=?,
                 people_number=?,
                 tour_price=?,
                 is_burning=?
                 WHERE tour_id=?""", tableName);

        try (PreparedStatement pstmt = con.prepareStatement(updatePositionSql)) {
            pstmt.setInt(1, entity.getMaxDiscount());
            pstmt.setString(2, entity.getTourType().toString());
            pstmt.setString(3, entity.getHotelType().toString());
            pstmt.setInt(4,entity.getPeopleNumber());
            pstmt.setInt(5, entity.getPrice());
            pstmt.setBoolean(6, entity.isBurning());
            pstmt.setLong(7, entity.getTourId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void create(Tour entity) throws SQLException {
        String insertSQL = String.format("""
                insert into %s(max_discount,tour_type,hotel_type,price,is_burning)
                  values(?,?,?,?,?)""", tableName);

        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setInt(1, entity.getMaxDiscount());
            pstmt.setString(2, entity.getTourType().toString());
            pstmt.setString(3, entity.getHotelType().toString());
            pstmt.setInt(4, entity.getPrice());
            pstmt.setBoolean(5, entity.isBurning());
            ;
            pstmt.executeUpdate();
        }

    }

    @Override
    public void delete(Long id) throws SQLException {
        String deleteQuery = String.format("DELETE FROM %s where tour_id=?", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Tour mapToEntity(ResultSet resultSet) throws SQLException {
        return Tour.builder()
                .tourId(resultSet.getLong("tour_id"))
                .maxDiscount(resultSet.getInt("max_discount"))
                .tourType(TourType.valueOf(resultSet.getString("tour_type")))
                .hotelType(HotelType.valueOf(resultSet.getString("hotel_type")))
                .peopleNumber(resultSet.getInt("people_number"))
                .price(resultSet.getInt("tour_price"))
                .isBurning(resultSet.getBoolean("is_burning"))
                .build();
    }
}
