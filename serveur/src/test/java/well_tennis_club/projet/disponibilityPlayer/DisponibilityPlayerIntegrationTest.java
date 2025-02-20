package well_tennis_club.projet.disponibilityPlayer;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.entity.DisponibilityPlayerEntity;
import well_tennis_club.projet.key.DisponibilityPlayerKey;
import well_tennis_club.projet.service.DisponibilityPlayerService;

import java.util.List;
import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class DisponibilityPlayerIntegrationTest {
    @Autowired
    private DisponibilityPlayerService service;

    @Test
    @Transactional
    void testDisponibilityPlayerService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetDisponibilityPlayer(){
        List<DisponibilityPlayerEntity> disponibilityPlayer = service.getDisponibilitiesForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(disponibilityPlayer).isNotNull();
        Assertions.assertThat(disponibilityPlayer.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testCreateDisponibilityPlayer(){
        DisponibilityPlayerEntity newDisponibilityPlayer = new DisponibilityPlayerEntity();
        DisponibilityPlayerKey key = new DisponibilityPlayerKey();
        key.setIdPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        key.setIdDisponibility(UUID.fromString("77fb89b2-a0a8-4242-a82f-9ec15cbdb934"));
        newDisponibilityPlayer.setDisponibilityPlayerKey(key);
        DisponibilityPlayerEntity disponibilityPlayerEntity = service.createDisponibilityPlayer(newDisponibilityPlayer);

        List<DisponibilityPlayerEntity> disponibilityPlayer = service.getDisponibilitiesForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(disponibilityPlayer).isNotNull();
        Assertions.assertThat(disponibilityPlayer.size()).isEqualTo(2);

        service.deleteDisponibilityPlayer(disponibilityPlayerEntity);
    }

    @Test
    @Transactional
    void testDeleteDisponibilityPlayer(){
        DisponibilityPlayerEntity newDisponibilityPlayer = new DisponibilityPlayerEntity();
        DisponibilityPlayerKey key = new DisponibilityPlayerKey();
        key.setIdPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        key.setIdDisponibility(UUID.fromString("77fb89b2-a0a8-4242-a82f-9ec15cbdb934"));
        newDisponibilityPlayer.setDisponibilityPlayerKey(key);
        DisponibilityPlayerEntity disponibilityPlayerEntity = service.createDisponibilityPlayer(newDisponibilityPlayer);

        List<DisponibilityPlayerEntity> disponibilityPlayer = service.getDisponibilitiesForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(disponibilityPlayer).isNotNull();
        Assertions.assertThat(disponibilityPlayer.size()).isEqualTo(2);

        service.deleteDisponibilityPlayer(disponibilityPlayerEntity);

        disponibilityPlayer = service.getDisponibilitiesForPlayer(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(disponibilityPlayer).isNotNull();
        Assertions.assertThat(disponibilityPlayer.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllDisponibilitiesPlayer(){
        List<DisponibilityPlayerEntity> disponibilityPlayer = service.getAllDisponibilitiesPlayer();
        Assertions.assertThat(disponibilityPlayer).isNotNull();
        Assertions.assertThat(disponibilityPlayer.size()).isEqualTo(1);
    }
}
