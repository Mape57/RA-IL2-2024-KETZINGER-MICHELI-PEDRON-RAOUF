package well_tennis_club.projet.disponibilityCoach;

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
@RequestMapping("disponibilityCoach")
@Transactional
public class DisponibilityCoachController {
    private final DisponibilityCoachService disponibilityCoachService;
    @Autowired
    public DisponibilityCoachController(DisponibilityCoachService disponibilityCoachService){this.disponibilityCoachService = disponibilityCoachService;}

    @CrossOrigin
    @Operation(summary = "Get all disponibilities coach",description = "Returns all disponibilities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
    })
    @GetMapping
    public List<DisponibilityCoachDto> getAllDisponibilitiesCoach(){
        List<DisponibilityCoachDto> list = DisponibilityCoachMapper.INSTANCE.mapToListDTO(disponibilityCoachService.getAllDisponibilitiesCoach());
        if(list == null || list.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Disponibilities not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Add disponibility for coach", description = "Add disponibility for coach with their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not create")
    })
    @PostMapping
    public DisponibilityCoachDto createDisponibilityCoach(@RequestBody DisponibilityCoachDto dto){
        return DisponibilityCoachMapper.INSTANCE.mapToDTO(disponibilityCoachService.createDisponibilityCoach(DisponibilityCoachMapper.INSTANCE.mapToEntity(dto)));
    }

    @CrossOrigin
    @Operation(summary = "Delete disponibility for coach",description = "Delete disponibility for coach with their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not delete")
    })
    @DeleteMapping("/{idCoach}/{idDisponibility}")
    public void deleteDisponibilityCoach(@PathVariable Long idCoach,@PathVariable Long idDisponibility){
        DisponibilityCoachEntity entity = new DisponibilityCoachEntity();
        DisponibilityCoachKey key = new DisponibilityCoachKey();
        key.setIdCoach(idCoach);
        key.setIdDisponibility(idDisponibility);
        entity.setDisponibilityCoachKey(key);
        disponibilityCoachService.deleteDisponibilityCoach(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get all disponibilities for coach",description = "Return all disponibilities for coach with his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
    })
    @GetMapping("/{id}")
    public List<DisponibilityCoachDto> getDisponibilitiesCoach(@PathVariable Long id){
        List<DisponibilityCoachDto> dtos = DisponibilityCoachMapper.INSTANCE.mapToListDTO(disponibilityCoachService.getDisponibilityForCoach(id));
        if (dtos == null || dtos.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Disponibilities not found"
            );
        }else {
            return dtos;
        }
    }
}
