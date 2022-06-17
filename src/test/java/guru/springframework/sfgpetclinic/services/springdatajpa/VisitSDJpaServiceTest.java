package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    Visit visit;
    Set<Visit> visits;

    @BeforeEach
    void setUp() {
        visit = new Visit(1L, LocalDate.now());
    }

    @Test
    void findAll() {
        visits = new HashSet<>();
        visits.add(visit);

        //given
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> response = visitSDJpaService.findAll();

        //then
        then(visitRepository).should().findAll();

        assertEquals(1, response.size());
    }

    @Test
    void findById() {

        //given
        given(visitRepository.findById(anyLong())).willReturn(Optional.ofNullable(visit));

        //when
        Visit response = visitSDJpaService.findById(anyLong());

        //then
        then(visitRepository).should(times(1)).findById(anyLong());

        assertEquals(visit.getId(), response.getId());
        assertEquals(visit.getDate(), response.getDate());
    }

    @Test
    void save() {

        //given
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //when
        Visit response = visitSDJpaService.save(visit);

        //then
        then(visitRepository).should().save(visit);

        assertThat(response).isNotNull();
    }

    @Test
    void delete() {
        visitSDJpaService.delete(visit);

        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        visitSDJpaService.deleteById(anyLong());

        then(visitRepository).should().deleteById(anyLong());
    }
}