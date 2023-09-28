package org.dmdev.springbootrestapi.models.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import org.dmdev.springbootrestapi.exceptions.IllegalBirthdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator
        implements ConstraintValidator<Adult, LocalDate> {

    @Value("${user.required-age}")
    private Integer requiredAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(Period.between(value, LocalDate.now()).getYears() <= requiredAge)
            throw new IllegalBirthdate("Users under the age of 18 are not allowed to register");
        return true;
    }
}
