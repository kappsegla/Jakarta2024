package se.iths.restdemo.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidPersonDto() {
        // Create a valid PersonDto instance
        PersonDto person = new PersonDto("John Doe", 30);

        // Validate the record
        var violations = validator.validate(person);

        // Assert that there are no validation violations
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidPersonDto() {
        // Create an invalid PersonDto instance (e.g., empty name)
        PersonDto person = new PersonDto("Kalle", 151);

        // Validate the record
        var violations = validator.validate(person);

        // Assert that there is a validation violation for the name field
        assertEquals(1, violations.size());
        assertEquals("Custom error message for age", violations.iterator().next().getMessage());
    }
}
