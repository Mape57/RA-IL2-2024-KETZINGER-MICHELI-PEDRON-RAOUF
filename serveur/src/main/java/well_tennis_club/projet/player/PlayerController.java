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
import java.util.UUID;
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
    @GetMapping()
    public List<PlayerDto> getAllPlayers(){
        List<PlayerDto> list = PlayerMapper.INSTANCE.mapToListDTO(playerService.getAllPlayers());
        if (list == null){
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
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not create")
    })
    @PostMapping
    public PlayerDto createPlayer(@RequestBody PlayerDto playerDto){
        PlayerDto player = playerDto;
        player.setId(UUID.randomUUID());
        return PlayerMapper.INSTANCE.mapToDTO(playerService.createPlayer(PlayerMapper.INSTANCE.mapToEntity(player)));
    }

    @CrossOrigin
    @Operation(summary = "Update player",description = "Update player with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully patched"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Player was not update")
    })
    @PatchMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable UUID id, @RequestBody PlayerDto playerDto){
        PlayerDto player = PlayerMapper.INSTANCE.mapToDTO(playerService.getPlayerById(id));
        if (player == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Player not found"
            );
        }else{
            PlayerDto modif = playerDto;
            modif.setId(id);
            return PlayerMapper.INSTANCE.mapToDTO(playerService.updatePlayer(PlayerMapper.INSTANCE.mapToEntity(modif)));
        }
    }

    @CrossOrigin
    @Operation(summary = "Delete player",description = "Delete player with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not delete")
    })
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable UUID id){
        PlayerEntity entity = new PlayerEntity();
        entity.setId(id);
        playerService.deletePlayer(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get one player",description = "Return player with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not found")
    })
    @GetMapping("/{id}")
    public PlayerDto getPlayer(@PathVariable UUID id){
        PlayerDto player = PlayerMapper.INSTANCE.mapToDTO(playerService.getPlayerById(id));
        if (player == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Player not found"
            );
        }else {
            return player;
        }
    }

    @CrossOrigin
    @Operation(summary = "Return only players of the specified validate status",description = "Return players of this status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Player was not found")
    })
    @GetMapping("/validate/{validate}")
    public List<PlayerDto> getPlayerValidate(@PathVariable boolean validate){
        List<PlayerDto> players = PlayerMapper.INSTANCE.mapToListDTO(playerService.getPlayerValidate(validate));
        if (players == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Players not found"
            );
        }else {
            return players;
        }
    }
}
