package well_tennis_club.projet.core.player;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.WellTennisClubApplication;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.service.PlayerService;
import well_tennis_club.timefold.domain.Player;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = WellTennisClubApplication.class)
@ActiveProfiles("test")
public class PlayerIntegrationTest {

    @Autowired
    private PlayerService service;

    @Test
    @Transactional
    void testPlayerService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetPlayer(){
        PlayerEntity player = service.getPlayerById(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getName()).isEqualTo("Djokovic");
        Assertions.assertThat(player.getSurname()).isEqualTo("Novak");
    }

    @Test
    @Transactional
    void testCreatePlayer(){
        PlayerEntity newPlayer = new PlayerEntity();
        newPlayer.setName("Federer");
        newPlayer.setSurname("Roger");
        newPlayer.setBirthday("1981-08-08");
        newPlayer.setCourses(0L);
        newPlayer.setLevel(19L);
        newPlayer.setEmail("roger.federer@gmail.com");
        newPlayer.setValidate(true);
        PlayerEntity playerEntity = service.createPlayer(newPlayer);

        PlayerEntity player = service.getPlayerById(playerEntity.getId());
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getName()).isEqualTo("Federer");
        Assertions.assertThat(player.getSurname()).isEqualTo("Roger");

        service.deleteById(playerEntity.getId());
    }

    @Test
    @Transactional
    void testUpdatePlayer(){
        PlayerEntity updatedPlayer = new PlayerEntity();
        PlayerEntity player = service.getPlayerById(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getLevel()).isEqualTo(19L);

        updatedPlayer.setId(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        updatedPlayer.setLevel(20L);
        service.updatePlayer(updatedPlayer);

        player = service.getPlayerById(UUID.fromString("dd0c3930-3e0e-4f74-bd84-e2591ea4e52c"));
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getLevel()).isEqualTo(20L);
    }

    @Test
    @Transactional
    void testDeletePlayer(){
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setName("Micheli");
        playerEntity.setSurname("Thomas");
        playerEntity.setBirthday("2004-04-13");
        playerEntity.setCourses(0L);
        playerEntity.setLevel(19L);
        playerEntity.setEmail("thomas@mail.com");
        playerEntity.setValidate(false);
        playerEntity = service.createPlayer(playerEntity);

        PlayerEntity player = service.getPlayerById(playerEntity.getId());
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getName()).isEqualTo("Micheli");

        service.deleteById(player.getId());
        player = service.getPlayerById(player.getId());
        Assertions.assertThat(player).isNull();
    }

    @Test
    @Transactional
    void testGetAllPlayers(){
        Assertions.assertThat(service.getAllPlayers()).isNotNull();
        Assertions.assertThat(service.getAllPlayers().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void testGetAllCreateVide(){
        List<PlayerEntity> players = service.getAllPlayers();
        for (PlayerEntity player : players){
            service.deleteById(player.getId());
        }

        Assertions.assertThat(service.getAllPlayers()).isNotNull();
        Assertions.assertThat(service.getAllPlayers().size()).isEqualTo(0);

        for (PlayerEntity player : players){
            service.createPlayer(player);
        }
    }
}
