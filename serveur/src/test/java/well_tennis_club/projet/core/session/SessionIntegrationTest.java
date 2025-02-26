package well_tennis_club.projet.core.session;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.WellTennisClubApplication;
import well_tennis_club.projet.core.court.CourtService;
import well_tennis_club.projet.core.trainer.service.TrainerService;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class SessionIntegrationTest {
    @Autowired
    private SessionService service;
    @Autowired
    private CourtService courtService;
    @Autowired
    private TrainerService trainerService;

    @Test
    @Transactional
    void testSessionService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetSession(){
        SessionEntity session = service.getSessionById(UUID.fromString("4cf241e2-b7e4-4ae4-86c0-b15a13cfe2d9"));
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testCreateSession(){
        SessionEntity newSession = new SessionEntity();
        newSession.setDayWeek(2);
        newSession.setStart("10:00");
        newSession.setStop("12:00");
        newSession.setIdTrainer(trainerService.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137")));
        newSession.setIdCourt(courtService.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83")));
        SessionEntity sessionEntity = service.createSession(newSession);

        SessionEntity session = service.getSessionById(sessionEntity.getId());
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(2);

        service.deleteById(sessionEntity.getId());
    }

    @Test
    @Transactional
    void testUpdateSession(){
        SessionEntity updatedSession = new SessionEntity();
        SessionEntity session = service.getSessionById(UUID.fromString("4cf241e2-b7e4-4ae4-86c0-b15a13cfe2d9"));
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(1);

        updatedSession.setId(session.getId());
        updatedSession.setDayWeek(3);
        service.updateSession(updatedSession);

        session = service.getSessionById(UUID.fromString("4cf241e2-b7e4-4ae4-86c0-b15a13cfe2d9"));
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(3);
    }

    @Test
    @Transactional
    void testDeleteSession(){
        SessionEntity newSession = new SessionEntity();
        newSession.setDayWeek(2);
        newSession.setStart("10:00");
        newSession.setStop("12:00");
        newSession.setIdTrainer(trainerService.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137")));
        newSession.setIdCourt(courtService.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83")));
        SessionEntity sessionEntity = service.createSession(newSession);

        SessionEntity session = service.getSessionById(sessionEntity.getId());
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(2);

        service.deleteById(sessionEntity.getId());
        session = service.getSessionById(sessionEntity.getId());
        Assertions.assertThat(session).isNull();
    }
    @Test
    @Transactional
    void testGetAllSessions(){
        Assertions.assertThat(service.getAllSessions()).isNotNull();
        Assertions.assertThat(service.getAllSessions().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<SessionEntity> sessions = service.getAllSessions();
        for (SessionEntity session : sessions){
            service.deleteById(session.getId());
        }

        Assertions.assertThat(service.getAllSessions()).isNotNull();
        Assertions.assertThat(service.getAllSessions().size()).isEqualTo(0);

        for (SessionEntity session : sessions){
            service.createSession(session);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<SessionEntity> sessions = service.getAllSessions();
        int size = sessions.size();

        UUID id = UUID.randomUUID();
        SessionEntity session = new SessionEntity();
        session.setId(id);
        service.deleteById(session.getId());

        sessions = service.getAllSessions();
        Assertions.assertThat(sessions.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        SessionEntity session = service.getSessionById(id);
        Assertions.assertThat(session).isNull();
    }
}
