package well_tennis_club.projet.core.trainer;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.WellTennisClubApplication;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.SessionService;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.service.TrainerService;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class TrainerIntegrationTest {

    @Autowired
    private TrainerService service;
    @Autowired
    private SessionService sessionService;

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
        TrainerEntity trainerEntity = service.createTrainer(newTrainer);

        TrainerEntity trainer = service.getTrainerById(trainerEntity.getId());
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Micheli");
        Assertions.assertThat(trainer.getSurname()).isEqualTo("Thomas");

        service.deleteById(trainerEntity.getId());
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
        trainerEntity = service.createTrainer(trainerEntity);

        TrainerEntity trainer = service.getTrainerById(trainerEntity.getId());
        Assertions.assertThat(trainer).isNotNull();
        Assertions.assertThat(trainer.getName()).isEqualTo("Jung");

        service.deleteById(trainerEntity.getId());
        trainer = service.getTrainerById(trainerEntity.getId());
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

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<SessionEntity> sessions = sessionService.getAllSessions();
        for (SessionEntity session : sessions){
            sessionService.deleteById(session.getId());
        }

        List<TrainerEntity> trainers = service.getAllTrainers();
        for (TrainerEntity trainer : trainers){
            service.deleteById(trainer.getId());
        }

        Assertions.assertThat(service.getAllTrainers()).isNotNull();
        Assertions.assertThat(service.getAllTrainers().size()).isEqualTo(0);

        for (TrainerEntity trainer : trainers){
            service.createTrainer(trainer);
        }

        trainers = service.getAllTrainers();

        for (SessionEntity session : sessions){
            session.setIdTrainer(trainers.get(0));
            sessionService.createSession(session);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<TrainerEntity> trainers = service.getAllTrainers();
        int size = trainers.size();

        UUID id = UUID.randomUUID();
        TrainerEntity trainer = new TrainerEntity();
        trainer.setId(id);
        service.deleteById(trainer.getId());

        trainers = service.getAllTrainers();
        Assertions.assertThat(trainers.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        TrainerEntity trainer = service.getTrainerById(id);
        Assertions.assertThat(trainer).isNull();
    }
}
