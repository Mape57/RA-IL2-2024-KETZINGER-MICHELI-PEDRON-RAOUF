package well_tennis_club.projet.coach;

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
@RequestMapping("coachs")
@Transactional
public class CoachController {
    private final CoachService coachService;
    @Autowired
    public CoachController(CoachService coachService){this.coachService = coachService;}

    @CrossOrigin
    @Operation(summary = "Get all coachs", description = "Returns all coachs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Coachs were not found")
    })
    @GetMapping("/all")
    public List<CoachDto> getAllCoachs(){
        List<CoachDto> list = CoachMapper.INSTANCE.mapToListDTO(coachService.getAllCoachs());
        if (list == null || list.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Coachs not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Create coach", description = "Create coach with name, surname, levels and ages from the dto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Coach was not create")
    })
    @PostMapping
    public CoachDto createCoach(@RequestBody CoachDto coachDto){
        return CoachMapper.INSTANCE.mapToDTO(coachService.createCoach(CoachMapper.INSTANCE.mapToEntity(coachDto)));
    }

    @CrossOrigin
    @Operation(summary = "Update coach",description = "Update coach with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Coach was not update")
    })
    @PutMapping
    public CoachDto updateCoach(@RequestBody CoachDto coachDto){
        return CoachMapper.INSTANCE.mapToDTO(coachService.updateCoach(CoachMapper.INSTANCE.mapToEntity(coachDto)));
    }

    @CrossOrigin
    @Operation(summary = "Delete coach",description = "Delete coach with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Coach was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable Long id){
        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setId(id);
        coachService.deleteCoach(coachEntity);
    }
}