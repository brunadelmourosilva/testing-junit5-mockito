package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteTest() {
        service.delete(new Speciality());
    }

    @Test
    void deleteById(){
        service.deleteById(1l);
        service.deleteById(1l);

        verify(repository, times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeast(){
        service.deleteById(1l);
        service.deleteById(1l);

        verify(repository, atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost(){
        service.deleteById(1l);
        service.deleteById(1l);

        verify(repository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdAtNever(){
        service.deleteById(1l);
        service.deleteById(1l);

        verify(repository, atLeastOnce()).deleteById(1l);

        verify(repository, atLeastOnce()).deleteById(1l);
    }
}