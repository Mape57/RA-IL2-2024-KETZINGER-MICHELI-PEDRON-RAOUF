package well_tennis_club.projet.session;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.service.CourtService;
import well_tennis_club.projet.entity.SessionEntity;
import well_tennis_club.projet.service.SessionService;
import well_tennis_club.projet.service.TrainerService;

import java.util.UUID;

@Disabled
@SpringBootTest
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
        UUID id = UUID.randomUUID();

        SessionEntity newSession = new SessionEntity();
        newSession.setDayWeek(2);
        newSession.setStart("10:00");
        newSession.setStop("12:00");
        newSession.setIdTrainer(trainerService.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137")));
        newSession.setIdCourt(courtService.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83")));
        newSession.setId(id);
        SessionEntity sessionEntity = service.createSession(newSession);

        SessionEntity session = service.getSessionById(id);
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(2);

        service.deleteSession(sessionEntity);
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
        UUID id = UUID.randomUUID();

        SessionEntity newSession = new SessionEntity();
        newSession.setDayWeek(2);
        newSession.setStart("10:00");
        newSession.setStop("12:00");
        newSession.setIdTrainer(trainerService.getTrainerById(UUID.fromString("4d970d71-4ff9-4435-8860-435c12434137")));
        newSession.setIdCourt(courtService.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83")));
        newSession.setId(id);
        SessionEntity sessionEntity = service.createSession(newSession);

        SessionEntity session = service.getSessionById(id);
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getDayWeek()).isEqualTo(2);

        service.deleteSession(sessionEntity);
        session = service.getSessionById(id);
        Assertions.assertThat(session).isNull();
    }

    @Test
    @Transactional
    void testGetAllSessions(){
        Assertions.assertThat(service.getAllSessions()).isNotNull();
        Assertions.assertThat(service.getAllSessions().size()).isEqualTo(2);
    }
}
