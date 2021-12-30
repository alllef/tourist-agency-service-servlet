package com.github.alllef.servlet.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandList {
    BLOCKED_CLIENTS_LIST(new AdminClientCommand()),
    CLIENT_TOUR_CATALOGUE(new ClientTourCatalogueCommand()),
    CLIENT_TOUR_REQUEST_LIST(new ClientTourRequestCommand()),
    REGISTRATION(new RegistrationCommand()),
    ADMIN_TOUR_CATALOGUE(new AdminTourCatalogueCommand()),
    TOUR_FORM(new AdminTourCommand()),
    MANAGER_TOUR_CATALOGUE(new ManagerTourCommand()),
    MANAGER_TOUR_REQUEST_COMMAND(new ManagerTourRequestCommand());

    private final ServletCommand servletCommand;

    public static CommandList findCommand(String path) {
        return switch (path) {
            case "/admin/tour" -> TOUR_FORM;
            case "/admin/tour-catalogue" -> ADMIN_TOUR_CATALOGUE;
            case "/admin/clients" -> BLOCKED_CLIENTS_LIST;
            case "/register" -> REGISTRATION;
            case "/managing/tours" -> MANAGER_TOUR_CATALOGUE;
            case "/managing/tour-requests" -> MANAGER_TOUR_REQUEST_COMMAND;
            case "/my-account" -> CLIENT_TOUR_REQUEST_LIST;
            case "/tours/catalogue" ->CLIENT_TOUR_CATALOGUE;
        };
    }
}
