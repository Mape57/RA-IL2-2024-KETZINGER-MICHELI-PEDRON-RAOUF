package well_tennis_club.projet.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerService(PlayerRepository playerRepository){this.playerRepository = playerRepository;}

    public List<PlayerEntity> getAllPlayers(){
        return playerRepository.findAll();
    }

    public PlayerEntity createPlayer(PlayerEntity entity){return playerRepository.save(entity);}

    public PlayerEntity updatePlayer(PlayerEntity entity){return playerRepository.save(entity);}

    public void deletePlayer(PlayerEntity entity){playerRepository.delete(entity);}

    public PlayerEntity getPlayerById(UUID id){return playerRepository.findById(id).orElse(null);}
}
