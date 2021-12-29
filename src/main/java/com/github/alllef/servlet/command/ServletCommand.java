package com.github.alllef.servlet.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface ServletCommand {
    Optional<String> execute(HttpServletRequest request, HttpServletResponse response);
}
