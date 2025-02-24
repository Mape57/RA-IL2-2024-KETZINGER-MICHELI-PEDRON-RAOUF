package well_tennis_club.projet.disponibilityTrainer;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.WellTennisClubApplication;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class DisponibilityTrainerIntegrationTest {
    @Autowired
    private DisponibilityTrainerService service;

    @Test
    @Transactional
    void testDisponibilityTrainerService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetDisponibilityTrainer(){
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getDisponibilityForTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testCreateDisponibilityTrainer(){
        DisponibilityTrainerEntity newDisponibilityTrainer = new DisponibilityTrainerEntity();
        DisponibilityTrainerKey key = new DisponibilityTrainerKey();
        key.setIdTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        key.setIdDisponibility(UUID.fromString("77fb89b2-a0a8-4242-a82f-9ec15cbdb934"));
        newDisponibilityTrainer.setDisponibilityTrainerKey(key);
        DisponibilityTrainerEntity disponibilityTrainerEntity = service.createDisponibilityTrainer(newDisponibilityTrainer);

        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getDisponibilityForTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(2);

        service.deleteDisponibilityTrainer(disponibilityTrainerEntity);
    }

    @Test
    @Transactional
    void testDeleteDisponibilityTrainer(){
        DisponibilityTrainerEntity newDisponibilityTrainer = new DisponibilityTrainerEntity();
        DisponibilityTrainerKey key = new DisponibilityTrainerKey();
        key.setIdTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        key.setIdDisponibility(UUID.fromString("77fb89b2-a0a8-4242-a82f-9ec15cbdb934"));
        newDisponibilityTrainer.setDisponibilityTrainerKey(key);
        DisponibilityTrainerEntity disponibilityTrainerEntity = service.createDisponibilityTrainer(newDisponibilityTrainer);

        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getDisponibilityForTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(2);

        service.deleteDisponibilityTrainer(disponibilityTrainerEntity);

        disponibilityTrainer = service.getDisponibilityForTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllDisponibilitiesTrainer(){
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getAllDisponibilitiesTrainer();
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getAllDisponibilitiesTrainer();
        for (DisponibilityTrainerEntity disponibilityTrainerEntity : disponibilityTrainer) {
            service.deleteDisponibilityTrainer(disponibilityTrainerEntity);
        }

        Assertions.assertThat(service.getAllDisponibilitiesTrainer()).isNotNull();
        Assertions.assertThat(service.getAllDisponibilitiesTrainer().size()).isEqualTo(0);

        for (DisponibilityTrainerEntity disponibilityTrainerEntity : disponibilityTrainer) {
            service.createDisponibilityTrainer(disponibilityTrainerEntity);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getAllDisponibilitiesTrainer();
        int size = disponibilityTrainer.size();

        UUID id = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        DisponibilityTrainerKey key = new DisponibilityTrainerKey();
        key.setIdTrainer(id);
        key.setIdDisponibility(id1);
        DisponibilityTrainerEntity newDisponibilityTrainer = new DisponibilityTrainerEntity();
        newDisponibilityTrainer.setDisponibilityTrainerKey(key);
        service.deleteDisponibilityTrainer(newDisponibilityTrainer);

        disponibilityTrainer = service.getAllDisponibilitiesTrainer();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getDisponibilityForTrainer(id);
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void testGetByTrainer(){
        List<DisponibilityTrainerEntity> disponibilityTrainer = service.getDisponibilityForTrainer(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(disponibilityTrainer).isNotNull();
        Assertions.assertThat(disponibilityTrainer.size()).isEqualTo(1);
    }
}
