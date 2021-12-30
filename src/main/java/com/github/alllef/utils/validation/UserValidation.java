package com.github.alllef.utils.validation;

import com.github.alllef.model.entity.User;
import com.github.alllef.model.exception.ValidationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserValidation implements Validatable {
    private final User user;

    @Override
    public void validate() {
        if (!user.getEmail().contains("@")) throw new ValidationException();
        if (user.getFirstName().isEmpty()) throw new ValidationException();
        if (user.getLastName().isEmpty()) throw new ValidationException();
    }
}
