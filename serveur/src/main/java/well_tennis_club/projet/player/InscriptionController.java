package well_tennis_club.projet.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import well_tennis_club.projet.disponibility.DisponibilityDto;
import well_tennis_club.projet.disponibility.DisponibilityEntity;
import well_tennis_club.projet.disponibility.DisponibilityMapper;
import well_tennis_club.projet.disponibility.DisponibilityService;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerEntity;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerKey;
import well_tennis_club.projet.disponibilityPlayer.DisponibilityPlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("inscription")
public class InscriptionController {
    private final PlayerService playerService;
    private final DisponibilityService disponibilityService;
    private final DisponibilityPlayerService disponibilityPlayerService;
    @Autowired
    public InscriptionController(PlayerService playerService,DisponibilityService disponibilityService,DisponibilityPlayerService disponibilityPlayerService){
        this.playerService = playerService;
        this.disponibilityService = disponibilityService;
        this.disponibilityPlayerService = disponibilityPlayerService;
    }

    @CrossOrigin
    @Operation(summary = "Create a player",description = "Create a player with name, surname, age, level and courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Players were not found")
    })
    @PostMapping
    public PlayerDto inscrirePlayer(@RequestBody PlayerInscription playerInscription){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(playerInscription.getName());
        playerDto.setSurname(playerInscription.getSurname());
        playerDto.setBirthday(playerInscription.getBirthday());
        playerDto.setCourses(playerInscription.getCourses());
        playerDto.setLevel(playerInscription.getLevel());
        playerDto.setEmail(playerInscription.getEmail());
        playerDto.setId(UUID.randomUUID());
        playerDto.setValidate(false);
        playerDto.setDisponibilities(new ArrayList<>());

        List<DisponibilityInscription> lists = playerInscription.getDisponibilities();
        List<DisponibilityDto> disponibilityDtos = new ArrayList<>();
        for (DisponibilityInscription disponibilityInscription : lists){
            DisponibilityDto disponibilityDto = new DisponibilityDto();
            disponibilityDto.setOpen(disponibilityInscription.getOpen());
            disponibilityDto.setClose(disponibilityInscription.getClose());
            disponibilityDto.setDayWeek(disponibilityInscription.getDayWeek());
            disponibilityDto.setId(UUID.randomUUID());
            disponibilityDtos.add(disponibilityDto);

            DisponibilityEntity entity = disponibilityService.createDisponibility(DisponibilityMapper.INSTANCE.mapToEntity(disponibilityDto));
            disponibilityDtos.add(DisponibilityMapper.INSTANCE.mapToDTO(entity));
        }

        PlayerEntity entity = playerService.createPlayer(PlayerMapper.INSTANCE.mapToEntity(playerDto));
        playerDto = PlayerMapper.INSTANCE.mapToDTO(entity);

        for (DisponibilityDto disponibilityDto : disponibilityDtos){
            DisponibilityPlayerEntity disponibility = new DisponibilityPlayerEntity();
            DisponibilityPlayerKey key = new DisponibilityPlayerKey();
            key.setIdPlayer(entity.getId());
            key.setIdDisponibility(disponibilityDto.getId());
            disponibility.setDisponibilityPlayerKey(key);
            disponibilityPlayerService.createDisponibilityPlayer(disponibility);
        }

        UUID id = playerDto.getId();
        return PlayerMapper.INSTANCE.mapToDTO(playerService.getPlayerById(id));
    }
}
