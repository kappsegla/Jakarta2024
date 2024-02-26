package se.iths.restdemo.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import se.iths.restdemo.dto.PersonDto;
import se.iths.restdemo.dto.Persons;
import se.iths.restdemo.service.PersonService;

import java.net.URI;
import java.time.LocalDateTime;

@Path("persons")
public class PersonResource {

    @Context
    UriInfo uriInfo;

    private PersonService personService;

    public PersonResource() {
    }

    @Inject
    public PersonResource(PersonService personRepository) {
        this.personService = personRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Persons all() {
        return personService.all();
    }

    //Don't use primary key as id. Use nanoid or UUID
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public PersonDto one(@PathParam("id") long id) {
        return personService.one(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid PersonDto personDto) {
        var p = personService.add(personDto);
        return Response.created(
                        //Ask Jakarta application server for hostname and url path
                        URI.create(uriInfo.getAbsolutePath().toString() + "/"+ p.getId()))
                .build();
    }
}
