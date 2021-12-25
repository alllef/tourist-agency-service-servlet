package com.github.alllef.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mappable<T> {
    T mapToEntity(ResultSet resultSet) throws SQLException;
}
