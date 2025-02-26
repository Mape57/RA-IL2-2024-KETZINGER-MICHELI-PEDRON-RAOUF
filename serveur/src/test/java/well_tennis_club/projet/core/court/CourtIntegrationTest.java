package well_tennis_club.projet.core.court;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.WellTennisClubApplication;
import well_tennis_club.projet.core.court.CourtEntity;
import well_tennis_club.projet.core.court.CourtService;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.SessionService;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class CourtIntegrationTest {
    @Autowired
    private CourtService service;
    @Autowired
    private SessionService sessionService;

    @Test
    @Transactional
    void testCourtService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetCourt(){
        CourtEntity court = service.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat(court.getName()).isEqualTo("Court 1");
    }

    @Test
    @Transactional
    void testCreateCourt(){
        CourtEntity newCourt = new CourtEntity();
        newCourt.setName("Court 3");
        CourtEntity courtEntity = service.createCourt(newCourt);

        CourtEntity court = service.getCourtById(courtEntity.getId());
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat(court.getName()).isEqualTo("Court 3");

        service.deleteById(courtEntity.getId());
    }

    @Test
    @Transactional
    void testUpdateCourt(){
        CourtEntity updatedCourt = new CourtEntity();
        CourtEntity court = service.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        updatedCourt.setId(court.getId());
        updatedCourt.setName("Court 4");
        service.updateCourt(updatedCourt);

        CourtEntity courtUpdated = service.getCourtById(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(courtUpdated).isNotNull();
        Assertions.assertThat(courtUpdated.getName()).isEqualTo("Court 4");

        updatedCourt.setName("Court 1");
        service.updateCourt(updatedCourt);
    }

    @Test
    @Transactional
    void testDeleteCourt(){
        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setName("Court 3");
        courtEntity = service.createCourt(courtEntity);

        CourtEntity court = service.getCourtById(courtEntity.getId());
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat(court.getName()).isEqualTo("Court 3");

        service.deleteById(court.getId());
        court = service.getCourtById(id);
        Assertions.assertThat(court).isNull();
    }

    @Test
    @Transactional
    void testGetAllCourts(){
        Assertions.assertThat(service.getAllCourts()).isNotNull();
        Assertions.assertThat(service.getAllCourts().size()).isEqualTo((2));
    }

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<SessionEntity> sessions = sessionService.getAllSessions();
        for (SessionEntity session : sessions){
            sessionService.deleteById(session.getId());
        }

        List<CourtEntity> courts = service.getAllCourts();
        for (CourtEntity court : courts){
            service.deleteCourt(court);
        }

        Assertions.assertThat(service.getAllCourts()).isNotNull();
        Assertions.assertThat(service.getAllCourts().size()).isEqualTo((0));

        for (CourtEntity court : courts){
            service.createCourt(court);
        }

        courts = service.getAllCourts();

        for (SessionEntity session : sessions){
            session.setIdCourt(courts.get(0));
            sessionService.createSession(session);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<CourtEntity> courts = service.getAllCourts();
        int size = courts.size();

        UUID id = UUID.randomUUID();
        CourtEntity court = new CourtEntity();
        court.setId(id);
        service.deleteCourt(court);

        courts = service.getAllCourts();
        Assertions.assertThat(courts.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testUpdateInexistant(){
        List<CourtEntity> courts = service.getAllCourts();

        UUID id = UUID.randomUUID();
        CourtEntity court = new CourtEntity();
        court.setName("Ceci est un test");
        court.setId(id);
        service.updateCourt(court);

        for (CourtEntity c : courts){
            Assertions.assertThat(c.getName()).isNotEqualTo("Ceci est un test");
        }
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        CourtEntity court = service.getCourtById(id);
        Assertions.assertThat(court).isNull();
    }
}
