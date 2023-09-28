package org.dmdev.springbootrestapi.models.validations.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dmdev.springbootrestapi.exceptions.IllegalBirthdateException;
import org.dmdev.springbootrestapi.models.validations.Adult;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator
        implements ConstraintValidator<Adult, LocalDate> {

    @Value("${user.required-age}")
    private Integer requiredAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(Period.between(value, LocalDate.now()).getYears() <= requiredAge)
            throw new IllegalBirthdateException("Users under the age of 18 are not allowed to register");
        return true;
    }
}
