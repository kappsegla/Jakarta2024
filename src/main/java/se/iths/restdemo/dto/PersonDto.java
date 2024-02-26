package se.iths.restdemo.dto;

import jakarta.validation.constraints.*;
import se.iths.restdemo.entity.Person;
import se.iths.restdemo.validate.Age;
import se.iths.restdemo.validate.CustomAge;

public record PersonDto(@NotEmpty String name,
                        @CustomAge(message = "Custom error message for age") int age) {

    public static PersonDto map(Person person) {
        return new PersonDto(person.getName(), person.getAge());
    }

    public static Person map(PersonDto personDto) {
        var person = new Person();
        person.setName(personDto.name);
        person.setAge(personDto.age);
        return person;
    }
}
