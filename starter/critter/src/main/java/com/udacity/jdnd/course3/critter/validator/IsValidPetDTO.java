package com.udacity.jdnd.course3.critter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsValidPetDTOValidator.class })
public @interface IsValidPetDTO {
    String message() default "Address is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}