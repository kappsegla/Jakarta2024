package se.iths.restdemo.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.restdemo.dto.Persons;
import se.iths.restdemo.entity.Person;
import se.iths.restdemo.service.PersonService;
import se.iths.restdemo.validate.ConstraintViolationExceptionMapper;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonResourceTest {

    @Mock
    PersonService personService;

    ObjectMapper objectMapper;

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        dispatcher = MockDispatcherFactory.createDispatcher();
        var resource = new PersonResource(personService);
        dispatcher.getRegistry().addSingletonResource(resource);

        dispatcher.getProviderFactory().registerProvider(ConstraintViolationExceptionMapper.class);
    }

    @Test
    public void personsReturnsWithStatus200() throws Exception {

        when(personService.all()).thenReturn(new Persons(List.of(), LocalDateTime.now()));

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/persons");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals("{\"personDtos\":[],\"updated\"}", response.getContentAsString());
    }

    @Test
    @DisplayName("createWithInvalidValuesReturnsStatus403")
    void createWithInvalidValuesReturnsStatus403() throws URISyntaxException, UnsupportedEncodingException {
        when(personService.add(Mockito.any())).thenReturn(new Person());
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.post("/persons");
        request.contentType(MediaType.APPLICATION_JSON);
        request.content("{\"name\":\"Test\",\"age\":151}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(403, response.getStatus());
        assertEquals("", response.getContentAsString());
    }


}
