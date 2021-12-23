package com.github.alllef.model.dao;

import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.utils.enums.RequestStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TourRequestDAO extends AbstractDAO<TourRequest> {
    public TourRequestDAO(Connection connection) {
        super("tour_requests", connection);
    }

    @Override
    public void update(TourRequest entity) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void create(TourRequest entity) {
        String insertSQL = String.format("""
                insert into %s(user_id,request_status,discount,tour_id)
                  values(?,?,?,?)""", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setLong(1, entity.getUserId());
            pstmt.setString(2, entity.getRequestStatus().toString());
            pstmt.setInt(3, entity.getDiscount());
            pstmt.setLong(4, entity.getTourId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = String.format("DELETE FROM %s where tour_request_id=?", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setLong(1,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
