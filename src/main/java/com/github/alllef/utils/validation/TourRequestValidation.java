package com.github.alllef.utils.validation;

import com.github.alllef.model.entity.TourRequest;
import com.github.alllef.model.exception.ValidationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TourRequestValidation implements Validatable {
    private final TourRequest tourRequest;
    private final int maxDiscount;

    @Override
    public void validate() {
        if (tourRequest.getDiscount() == null || tourRequest.getDiscount() < 0
                || tourRequest.getDiscount() > 100 || tourRequest.getDiscount() > maxDiscount)
            throw new ValidationException();
    }
}
