package com.github.alllef.servlet.command;

import com.github.alllef.utils.enums.HtmlTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Getter
public class HtmlCommand implements ServletCommand {
    private String template;

   protected HtmlCommand(HtmlTemplate htmlTemplate) {
        this.template = htmlTemplate.getTemplate();
    }

    @Override
    public Optional<String> execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        return Optional.empty();
    }

}
