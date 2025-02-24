package well_tennis_club.projet.disponibility;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.WellTennisClubApplication;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerEntity;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerService;
import well_tennis_club.projet.disponibilityTrainer.DisponibilityTrainerEntity;
import well_tennis_club.projet.disponibilityTrainer.DisponibilityTrainerService;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class DisponibilityIntegrationTest {
    @Autowired
    private DisponibilityService service;
    @Autowired
    private DisponibilityPlayerService disponibilityPlayerService;
    @Autowired
    private DisponibilityTrainerService disponibilityTrainerService;

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

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<DisponibilityPlayerEntity> disponibilityPlayers = disponibilityPlayerService.getAllDisponibilitiesPlayer();
        for (DisponibilityPlayerEntity disponibilityPlayer : disponibilityPlayers) {
            disponibilityPlayerService.deleteDisponibilityPlayer(disponibilityPlayer);
        }

        List<DisponibilityTrainerEntity> disponibilityTrainer = disponibilityTrainerService.getAllDisponibilitiesTrainer();
        for (DisponibilityTrainerEntity disponibilityTrainerEntity : disponibilityTrainer) {
            disponibilityTrainerService.deleteDisponibilityTrainer(disponibilityTrainerEntity);
        }

        List<DisponibilityEntity> disponibilities = service.getAllDisponibilities();
        for (DisponibilityEntity disponibility : disponibilities) {
            service.deleteDisponibility(disponibility);
        }

        Assertions.assertThat(service.getAllDisponibilities()).isNotNull();
        Assertions.assertThat(service.getAllDisponibilities().size()).isEqualTo(0);

        for (DisponibilityEntity disponibility : disponibilities) {
            service.createDisponibility(disponibility);
        }

        for (DisponibilityTrainerEntity disponibilityTrainerEntity : disponibilityTrainer) {
            disponibilityTrainerService.createDisponibilityTrainer(disponibilityTrainerEntity);
        }

        for (DisponibilityPlayerEntity disponibilityPlayer : disponibilityPlayers) {
            disponibilityPlayerService.createDisponibilityPlayer(disponibilityPlayer);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<DisponibilityEntity> disponibilities = service.getAllDisponibilities();
        int size = disponibilities.size();

        UUID id = UUID.randomUUID();
        DisponibilityEntity disponibility = new DisponibilityEntity();
        disponibility.setId(id);
        service.deleteDisponibility(disponibility);

        disponibilities = service.getAllDisponibilities();
        Assertions.assertThat(disponibilities.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testUpdateInexistant(){
        List<DisponibilityEntity> disponibilities = service.getAllDisponibilities();

        UUID id = UUID.randomUUID();
        DisponibilityEntity disponibility = new DisponibilityEntity();
        disponibility.setId(id);
        disponibility.setDayWeek(5);
        disponibility.setOpen("Ceci est une heure");
        disponibility.setClose("20:00");
        service.updateDisponibility(disponibility);

        service.updateDisponibility(disponibility);
        for (DisponibilityEntity disponibilityEntity : disponibilities) {
            Assertions.assertThat(disponibilityEntity.getOpen()).isNotEqualTo("Ceci est une heure");
        }
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        DisponibilityEntity disponibility = service.getDisponibilityById(id);
        Assertions.assertThat(disponibility).isNull();
    }
}
