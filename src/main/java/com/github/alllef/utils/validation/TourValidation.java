package com.github.alllef.utils.validation;

import com.github.alllef.model.entity.Tour;
import com.github.alllef.model.exception.ValidationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TourValidation implements Validatable {
    private final Tour tour;

    @Override
    public void validate() {
        if (tour.getPrice() == null || tour.getPrice() < 0 || tour.getPrice() > 1000000)
            throw new ValidationException();
        if (tour.getMaxDiscount() == null || tour.getMaxDiscount() < 0 || tour.getMaxDiscount() > 100)
            throw new ValidationException();
        if (tour.getPeopleNumber() == null || tour.getPeopleNumber() < 1 || tour.getPeopleNumber() > 1000)
            throw new ValidationException();
    }
}
