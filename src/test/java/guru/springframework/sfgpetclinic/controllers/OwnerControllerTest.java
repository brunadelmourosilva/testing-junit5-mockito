package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.springdatajpa.OwnerSDJpaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService service;

    @Mock
    BindingResult result;
    Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(5L, "Bruna", "Delmouro");
    }

    @AfterEach
    void tearDown() {
        reset(service);
    }

    @Test
    void processCreationFormHasErrors() {
        //given
        given(result.hasErrors()).willReturn(true);

        //when
        String response = ownerController.processCreationForm(owner, result);

        //then
        then(result).should().hasErrors();

        assertEquals("owners/createOrUpdateOwnerForm", response);
    }

    @Test
    void processCreationFormHasNoErrors() {
        //given
        given(result.hasErrors()).willReturn(false);
        given(service.save(owner)).willReturn(owner);

        //when
        String response = ownerController.processCreationForm(owner, result);

        //then
        then(result).should().hasErrors();
        then(service).should().save(owner);

        assertEquals("redirect:/owners/5", response);
    }
}