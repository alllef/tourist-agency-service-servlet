package com.github.alllef.utils.security;

import com.github.alllef.utils.enums.UserType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SecurityUtils {
    private static Map<UserType, List<String>> rolesWithServlets = new EnumMap<>(UserType.class);

    static {
        rolesWithServlets.put(UserType.MANAGER, List.of("/manager"));
        rolesWithServlets.put(UserType.ADMINISTRATOR, List.of("/manager","/administrator","/logged"));
        rolesWithServlets.put(UserType.CLIENT, List.of("/client"));
    }

    public static boolean hasPermission(UserType userType, String servletPath){
        return rolesWithServlets.get(userType).contains(servletPath);
    }
}
