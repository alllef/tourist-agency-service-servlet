package com.github.alllef.model.dao;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.utils.enums.HotelType;
import com.github.alllef.utils.enums.TourType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TourDAO extends AbstractDAO<Tour> {
    public TourDAO(Connection connection) {
        super("tours", connection);
    }

    @Override
    public void update(Tour entity) {
        String updatePositionSql = String.format("""
                UPDATE %s
                 SET discount_max=?,
                 tour_type=?,
                 hotel_type=?,
                 people_number=?,
                 price=?,
                 is_burning=?
                 WHERE tour_request_id=?""", tableName);

        try (PreparedStatement pstmt = con.prepareStatement(updatePositionSql)) {
            pstmt.setInt(1, entity.getMaxDiscount());
            pstmt.setString(2, entity.getTourType().toString());
            pstmt.setString(3, entity.getHotelType().toString());
            pstmt.setInt(4, entity.getPrice());
            pstmt.setBoolean(5, entity.isBurning());
            pstmt.setLong(6,entity.getTourId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void create(Tour entity) {
        String insertSQL = String.format("""
                insert into %s(max_discount,tour_type,hotel_type,price,is_burning)
                  values(?,?,?,?,?)""", tableName);

        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setInt(1, entity.getMaxDiscount());
            pstmt.setString(2, entity.getTourType().toString());
            pstmt.setString(3, entity.getHotelType().toString());
            pstmt.setInt(4, entity.getPrice());
            pstmt.setBoolean(5, entity.isBurning());;
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(Long id) {
        String deleteQuery = String.format("DELETE FROM %s where tour_id=?", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setLong(1,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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