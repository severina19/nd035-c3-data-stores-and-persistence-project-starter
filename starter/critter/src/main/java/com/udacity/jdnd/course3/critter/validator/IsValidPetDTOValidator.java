package com.udacity.jdnd.course3.critter.validator;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidPetDTOValidator implements ConstraintValidator<IsValidPetDTO, PetDTO> {

    @Override
    public void initialize(IsValidPetDTO constraintAnnotation) {

    }
    @Override
    public boolean isValid(PetDTO petDTO, ConstraintValidatorContext context) {
        if (petDTO == null) return true;

        if (petDTO.getOwnerId() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Owner Id is required!")
                    .addPropertyNode("ownerId").addConstraintViolation();
            return false;
        }

        return true;
    }
}