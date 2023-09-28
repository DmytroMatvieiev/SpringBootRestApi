package org.dmdev.springbootrestapi.models.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dmdev.springbootrestapi.models.validations.Validators.AdultValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {



    String message() default "Users under the age of 18 are not allowed to register";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
