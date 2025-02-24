package well_tennis_club.projet.participation;

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
public class ParticipationIntegrationTest {
    @Autowired
    private ParticipationService service;

    @Test
    @Transactional
    void testParticipationService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetParticipation(){
        List<ParticipationEntity> participation = service.getParticipationForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testCreateParticipation(){
        ParticipationEntity newParticipation = new ParticipationEntity();
        ParticipationKey key = new ParticipationKey();
        key.setIdPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        key.setIdSession(UUID.fromString("b955580b-b0e5-4c9d-985f-46ec6653f316"));
        newParticipation.setParticipationKey(key);
        ParticipationEntity participationEntity = service.createParticipation(newParticipation);

        List<ParticipationEntity> participation = service.getParticipationForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(2);

        service.deleteParticipation(participationEntity);
    }

    @Test
    @Transactional
    void testDeleteParticipation(){
        ParticipationEntity newParticipation = new ParticipationEntity();
        ParticipationKey key = new ParticipationKey();
        key.setIdPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        key.setIdSession(UUID.fromString("b955580b-b0e5-4c9d-985f-46ec6653f316"));
        newParticipation.setParticipationKey(key);
        ParticipationEntity participationEntity = service.createParticipation(newParticipation);

        List<ParticipationEntity> participation = service.getParticipationForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(2);

        service.deleteParticipation(participationEntity);

        participation = service.getParticipationForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllParticipation(){
        List<ParticipationEntity> participation = service.getAllParticipation();
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<ParticipationEntity> participation = service.getAllParticipation();
        for (ParticipationEntity participationEntity : participation) {
            service.deleteParticipation(participationEntity);
        }

        Assertions.assertThat(service.getAllParticipation()).isNotNull();
        Assertions.assertThat(service.getAllParticipation().size()).isEqualTo(0);

        for (ParticipationEntity participationEntity : participation) {
            service.createParticipation(participationEntity);
        }
    }

    @Test
    @Transactional
    void testDeleteInexistant(){
        List<ParticipationEntity> participation = service.getAllParticipation();
        int size = participation.size();

        UUID id = UUID.randomUUID();
        UUID id1 = UUID.randomUUID();
        ParticipationKey key = new ParticipationKey();
        key.setIdPlayer(id);
        key.setIdSession(id1);
        ParticipationEntity newParticipation = new ParticipationEntity();
        newParticipation.setParticipationKey(key);
        service.deleteParticipation(newParticipation);

        participation = service.getAllParticipation();
        Assertions.assertThat(participation.size()).isEqualTo(size);
    }

    @Test
    @Transactional
    void testGetInexistant(){
        UUID id = UUID.randomUUID();
        List<ParticipationEntity> participation = service.getParticipationForPlayer(id);
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void testGetByPlayer(){
        List<ParticipationEntity> participation = service.getParticipationForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(participation).isNotNull();
        Assertions.assertThat(participation.size()).isEqualTo(1);
    }
}
