package well_tennis_club.projet.disponibilityTrainer;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.entity.DisponibilityTrainerEntity;
import well_tennis_club.projet.key.DisponibilityTrainerKey;
import well_tennis_club.projet.service.DisponibilityTrainerService;

import java.util.List;
import java.util.UUID;

@Disabled
@SpringBootTest
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
}
