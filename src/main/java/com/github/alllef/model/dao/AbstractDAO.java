package com.github.alllef.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class AbstractDAO<T> {
    protected Connection con;
    protected String tableName;

    AbstractDAO(String tableName, Connection connection) {
        this.con = connection;
        this.tableName=tableName;
    }

    public List<T> findAll() {
        List<T> tourList = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet resultSet = stmt.executeQuery("select * from " + tableName)) {
            while (resultSet.next())
                tourList.add(mapToEntity(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tourList;
    }

    public abstract Optional<T> findById(long id) throws SQLException;
    public abstract void update(T entity) throws SQLException;

    public abstract void create(T entity) throws SQLException;

    public abstract void delete(Long id) throws SQLException;

    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;
}
