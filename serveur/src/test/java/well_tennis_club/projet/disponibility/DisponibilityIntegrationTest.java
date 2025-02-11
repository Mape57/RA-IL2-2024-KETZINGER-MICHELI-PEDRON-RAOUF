package well_tennis_club.projet.disponibility;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class DisponibilityIntegrationTest {
    @Autowired
    private DisponibilityService service;

    @Test
    @Transactional
    void testDisponibilityService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetDisponibility(){
        DisponibilityEntity disponibility = service.getDisponibilityById(UUID.fromString("36697925-5b75-4601-a88c-24e68b248d86"));
        Assertions.assertThat(disponibility).isNotNull();
        Assertions.assertThat(disponibility.getDayWeek()).isEqualTo(1);
        Assertions.assertThat(disponibility.getOpen()).isEqualTo("08:00");
    }

    @Test
    @Transactional
    void testCreateDisponibility(){
        UUID id = UUID.randomUUID();

        DisponibilityEntity newDisponibility = new DisponibilityEntity();
        newDisponibility.setDayWeek(5);
        newDisponibility.setOpen("10:00");
        newDisponibility.setClose("20:00");
        newDisponibility.setId(id);
        DisponibilityEntity disponibilityEntity = service.createDisponibility(newDisponibility);

        DisponibilityEntity disponibility = service.getDisponibilityById(id);
        Assertions.assertThat(disponibility).isNotNull();
        Assertions.assertThat(disponibility.getDayWeek()).isEqualTo(5);
        Assertions.assertThat(disponibility.getOpen()).isEqualTo("10:00");

        service.deleteDisponibility(disponibilityEntity);
    }

    @Test
    @Transactional
    void testUpdateDisponibility(){
        DisponibilityEntity updatedDisponibility = new DisponibilityEntity();
        DisponibilityEntity disponibility = service.getDisponibilityById(UUID.fromString("36697925-5b75-4601-a88c-24e68b248d86"));
        Assertions.assertThat(disponibility).isNotNull();
        Assertions.assertThat(disponibility.getClose()).isEqualTo("18:00");

        updatedDisponibility.setId(disponibility.getId());
        updatedDisponibility.setDayWeek(disponibility.getDayWeek());
        updatedDisponibility.setOpen(disponibility.getOpen());
        updatedDisponibility.setClose("23:00");
        service.updateDisponibility(updatedDisponibility);

        DisponibilityEntity updatedDisponibilityEntity = service.getDisponibilityById(disponibility.getId());
        Assertions.assertThat(updatedDisponibilityEntity).isNotNull();
        Assertions.assertThat(updatedDisponibilityEntity.getClose()).isEqualTo("23:00");
    }

    @Test
    @Transactional
    void testDeleteDisponibility(){
        UUID id = UUID.randomUUID();

        DisponibilityEntity newDisponibility = new DisponibilityEntity();
        newDisponibility.setDayWeek(5);
        newDisponibility.setOpen("10:00");
        newDisponibility.setClose("20:00");
        newDisponibility.setId(id);
        DisponibilityEntity disponibilityEntity = service.createDisponibility(newDisponibility);

        DisponibilityEntity disponibility = service.getDisponibilityById(id);
        Assertions.assertThat(disponibility).isNotNull();
        Assertions.assertThat(disponibility.getDayWeek()).isEqualTo(5);
        Assertions.assertThat(disponibility.getOpen()).isEqualTo("10:00");

        service.deleteDisponibility(disponibilityEntity);

        DisponibilityEntity deletedDisponibility = service.getDisponibilityById(id);
        Assertions.assertThat(deletedDisponibility).isNull();
    }

    @Test
    @Transactional
    void testGetAllDisponibilities(){
        Assertions.assertThat(service.getAllDisponibilities()).isNotNull();
        Assertions.assertThat(service.getAllDisponibilities().size()).isEqualTo(2);
    }
}
