package well_tennis_club.projet.court;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.security.JwtUtils;
import well_tennis_club.projet.security.SecurityConfig;

import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class CourtIntegrationTest {
    @Autowired
    private CourtService service;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SecurityConfig securityConfig;

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
        UUID id = UUID.randomUUID();

        CourtEntity newCourt = new CourtEntity();
        newCourt.setName("Court 3");
        newCourt.setId(id);
        CourtEntity courtEntity = service.createCourt(newCourt);

        CourtEntity court = service.getCourtById(id);
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat(court.getName()).isEqualTo("Court 3");

        service.deleteCourt(courtEntity);
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
        UUID id = UUID.randomUUID();

        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setName("Court 3");
        courtEntity.setId(id);
        service.createCourt(courtEntity);

        CourtEntity court = service.getCourtById(id);
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat(court.getName()).isEqualTo("Court 3");

        service.deleteCourt(court);
        court = service.getCourtById(id);
        Assertions.assertThat(court).isNull();
    }

    @Test
    @Transactional
    void testGetAllCourts(){
        Assertions.assertThat(service.getAllCourts()).isNotNull();
        Assertions.assertThat(service.getAllCourts().size()).isEqualTo((5));
    }
}
