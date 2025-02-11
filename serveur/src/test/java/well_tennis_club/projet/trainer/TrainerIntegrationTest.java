package well_tennis_club.projet.trainer;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.WellTennisClubApplication;

import java.util.UUID;

@Disabled
@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class TrainerIntegrationTest {

    @Autowired
    private TrainerService service;

    @Test
    @Transactional
    void testTrainerService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetTrainer(){
        TrainerEntity trainer = service.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Hubert");
        Assertions.assertThat(trainer.getSurname()).isEqualTo("Arthur");
    }

    @Test
    @Transactional
    void testCreateTrainer(){
        UUID id = UUID.randomUUID();

        TrainerEntity newTrainer = new TrainerEntity();
        newTrainer.setName("Micheli");
        newTrainer.setSurname("Thomas");
        newTrainer.setInfLevel(0);
        newTrainer.setSupLevel(5);
        newTrainer.setInfAge(18);
        newTrainer.setSupAge(99);
        newTrainer.setEmail("thomas@mail.com");
        newTrainer.setPassword("password");
        newTrainer.setPartTime(true);
        newTrainer.setAdmin(true);
        newTrainer.setId(id);
        TrainerEntity trainerEntity = service.createTrainer(newTrainer);

        TrainerEntity trainer = service.getTrainerById(id);
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Micheli");
        Assertions.assertThat(trainer.getSurname()).isEqualTo("Thomas");

        service.deleteTrainer(trainerEntity);
    }

    @Test
    @Transactional
    void testUpdateTrainer(){
        TrainerEntity updatedTrainer;
        TrainerEntity trainer = service.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getInfLevel()).isEqualTo(14);
        Assertions.assertThat(trainer.getSupLevel()).isEqualTo(19);

        updatedTrainer = trainer;
        updatedTrainer.setId(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        updatedTrainer.setInfLevel(1);
        service.updateTrainer(updatedTrainer);

        trainer = service.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137"));
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getSupLevel()).isEqualTo(19);
        Assertions.assertThat(trainer.getInfLevel()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testDeleteTrainer(){
        UUID id = UUID.randomUUID();

        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setName("Jung");
        trainerEntity.setSurname("Ines");
        trainerEntity.setInfLevel(0);
        trainerEntity.setSupLevel(5);
        trainerEntity.setInfAge(18);
        trainerEntity.setSupAge(99);
        trainerEntity.setEmail("ines@mail.com");
        trainerEntity.setPassword("password");
        trainerEntity.setPartTime(false);
        trainerEntity.setAdmin(false);
        trainerEntity.setId(id);
        service.createTrainer(trainerEntity);

        TrainerEntity trainer = service.getTrainerById(id);
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Jung");

        service.deleteTrainer(trainerEntity);
        trainer = service.getTrainerById(id);
        Assertions.assertThat(trainer).isNull();
    }

    @Test
    @Transactional
    void testGetPassword(){
        String password = service.getPassword("ines.jung@gmail.com");
        Assertions.assertThat(password).isEqualTo("password");
    }

    @Test
    @Transactional
    void testGetTrainerByEmail() {
        TrainerEntity trainer = service.getTrainerByEmail("ines.jung@gmail.com");
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Jung");
        Assertions.assertThat(trainer.getSurname()).isEqualTo("Ines");
    }

    @Test
    @Transactional
    void testGetAllTrainers(){
        Assertions.assertThat(service.getAllTrainers()).isNotNull();
        Assertions.assertThat(service.getAllTrainers().size()).isEqualTo(3);
    }
}
