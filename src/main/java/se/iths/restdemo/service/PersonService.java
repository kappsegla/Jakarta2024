package se.iths.restdemo.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import se.iths.restdemo.dto.PersonDto;
import se.iths.restdemo.dto.Persons;
import se.iths.restdemo.entity.Person;
import se.iths.restdemo.repository.PersonRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class PersonService {

    PersonRepository personRepository;

    public PersonService() {
    }

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Persons all() {
        return new Persons(
                personRepository.all().stream().map(PersonDto::map).toList(),
                LocalDateTime.now());
    }

    public PersonDto one(long id) {
        var person = personRepository.findById(id);
        if( person == null)
            throw new NotFoundException("Invalid id " + id);
        return PersonDto.map(person);
    }

    public Person add(PersonDto personDto) {
        //Save to database
        var p = personRepository.add(PersonDto.map(personDto));
        return p;
    }
}
