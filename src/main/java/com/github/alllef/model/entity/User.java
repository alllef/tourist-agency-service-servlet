package com.github.alllef.model.entity;

import com.github.alllef.utils.enums.UserType;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder(toBuilder = true)
public class User{
    private Long userId;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isBlocked;
}
