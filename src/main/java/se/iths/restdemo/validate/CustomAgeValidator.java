package se.iths.restdemo.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomAgeValidator implements ConstraintValidator<CustomAge, Integer> {

    @Override
    public void initialize(CustomAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong >= 0 && aLong <= 150;
    }
}
