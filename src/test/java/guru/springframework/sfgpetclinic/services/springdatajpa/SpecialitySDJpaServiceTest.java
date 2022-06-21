package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    public void findByIdTest(){
        Speciality speciality = new Speciality();

        when(repository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(1L);

        assertThat(foundSpeciality).isNotNull();

        verify(repository, times(1)).findById(anyLong());
    }

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
    void deleteByObject(){
        Speciality speciality = new Speciality();

        service.delete(speciality);

        verify(repository).delete(any(Speciality.class));
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

    @Test
    void testFindByIdThrow() {
        given(repository.findById(anyLong())).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(anyLong()));

        then(repository).should().findById(anyLong());
    }

    @Test
    void testDeleteThrow() {
        //to a void function
        willThrow(new RuntimeException("boom")).given(repository).delete(any());

        assertThrows(RuntimeException.class, () -> repository.delete(new Speciality()));

        then(repository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(repository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = service.save(speciality);

        //then
        assertThat(returnedSpecialty.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNoMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(repository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = service.save(speciality);

        //then
        assertNull(returnedSpecialty);
    }
}