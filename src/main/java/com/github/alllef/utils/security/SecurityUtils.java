package com.github.alllef.utils.security;

import com.github.alllef.utils.enums.UserType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SecurityUtils {
    private static Map<UserType, List<String>> rolesWithServlets = new EnumMap<>(UserType.class);

    static {
        rolesWithServlets.put(UserType.MANAGER, List.of("/manager", "/managing/tours", "/managing/tour-requests","/logged","/logout"));
        rolesWithServlets.put(UserType.ADMINISTRATOR, List.of("/administrator", "/logged", "/admin/tour", "/admin/tour-catalogue", "/admin/clients","/logout"));
        rolesWithServlets.put(UserType.CLIENT, List.of("/client", "/my-account", "/tours/catalogue","/logged","/logout","/main/tour-catalogue.jsp","/main/my-account.jsp","/main/tour-catalogue.jsp"));
    }

    public static boolean hasPermission(UserType userType, String servletPath) {
        if (userType.equals(UserType.ADMINISTRATOR))
            return rolesWithServlets.get(userType).contains(servletPath) ||
                    rolesWithServlets.get(UserType.MANAGER).contains(servletPath);

        return rolesWithServlets.get(userType).contains(servletPath);
    }
}
