package well_tennis_club.projet.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("players")
@Transactional
public class PlayerController {
    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService){this.playerService = playerService;}

    @CrossOrigin
    @Operation(summary = "Get all players", description = "Returns all players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Players were not found")
    })
    @GetMapping("/all")
    public List<PlayerDto> getAllPlayers(){
        List<PlayerDto> list = PlayerMapper.INSTANCE.mapToListDTO(playerService.getAllPlayers());
        if (list == null || list.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Players not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Create player",description = "Create player with name, surname, age, level and courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not create")
    })
    @PostMapping
    public PlayerDto createPlayer(@RequestBody PlayerDto playerDto){
        return PlayerMapper.INSTANCE.mapToDTO(playerService.createPlayer(PlayerMapper.INSTANCE.mapToEntity(playerDto)));
    }

    @CrossOrigin
    @Operation(summary = "Update player",description = "Update player with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not update")
    })
    @PutMapping
    public PlayerDto updatePlayer(@RequestBody PlayerDto playerDto){
        return PlayerMapper.INSTANCE.mapToDTO(playerService.updatePlayer(PlayerMapper.INSTANCE.mapToEntity(playerDto)));
    }

    @CrossOrigin
    @Operation(summary = "Delete player",description = "Delete player with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not delete")
    })
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id){
        PlayerEntity entity = new PlayerEntity();
        entity.setId(id);
        playerService.deletePlayer(entity);
    }
}