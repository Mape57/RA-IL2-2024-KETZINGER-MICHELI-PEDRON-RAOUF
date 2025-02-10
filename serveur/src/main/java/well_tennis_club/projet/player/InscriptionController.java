package well_tennis_club.projet.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Transactional
@RequestMapping("inscription")
public class InscriptionController {
    private final PlayerService playerService;
    @Autowired
    public InscriptionController(PlayerService playerService){this.playerService = playerService;}

    @CrossOrigin
    @Operation(summary = "Create a player",description = "Create a player with name, surname, age, level and courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Players were not found")
    })
    @PostMapping
    public PlayerDto inscrirePlayer(@RequestBody PlayerDto playerDto){
        PlayerDto player = playerDto;
        player.setId(UUID.randomUUID());
        player.setValidate(false);
        return PlayerMapper.INSTANCE.mapToDTO(playerService.createPlayer(PlayerMapper.INSTANCE.mapToEntity(player)));
    }
}
