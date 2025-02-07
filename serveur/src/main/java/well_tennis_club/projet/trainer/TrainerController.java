package well_tennis_club.projet.trainer;

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
@RequestMapping("trainers")
@Transactional
public class TrainerController {
    private final TrainerService trainerService;
    @Autowired
    public TrainerController(TrainerService trainerService){this.trainerService = trainerService;}

    @CrossOrigin
    @Operation(summary = "Get all trainers", description = "Returns all trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Trainers were not found")
    })
    @GetMapping
    public List<TrainerDto> getAllTrainers(){
        List<TrainerDto> list = TrainerMapper.INSTANCE.mapToListDTO(trainerService.getAllTrainers());
        if (list == null || list.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainers not found"
            );
        }else {
            return list;
        }
    }


    @CrossOrigin
    @Operation(summary = "Create trainer", description = "Create trainer with name, surname, levels and ages from the dto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Trainer was not create")
    })
    @PostMapping
    public TrainerDto createTrainer(@RequestBody TrainerDto trainerDto){
        TrainerDto trainer = trainerDto;
        trainer.setId(UUID.randomUUID());
        return TrainerMapper.INSTANCE.mapToDTO(trainerService.createTrainer(TrainerMapper.INSTANCE.mapToEntity(trainer)));
    }

    @CrossOrigin
    @Operation(summary = "Update trainer",description = "Update trainer with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully patched"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Trainer was not update")
    })
    @PatchMapping("/{id}")
    public TrainerDto updateTrainer(@PathVariable UUID id, @RequestBody TrainerDto trainerDto){
        TrainerDto trainer = TrainerMapper.INSTANCE.mapToDTO(trainerService.getTrainerById(id));
        if (trainer == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainer not found"
            );
        }else{
            TrainerDto modif = trainerDto;
            modif.setId(trainer.getId());
            return TrainerMapper.INSTANCE.mapToDTO(trainerService.updateTrainer(TrainerMapper.INSTANCE.mapToEntity(modif)));
        }
    }

    @CrossOrigin
    @Operation(summary = "Delete trainer",description = "Delete trainer with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Trainer was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable UUID id){
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setId(id);
        trainerService.deleteTrainer(trainerEntity);
    }

    @CrossOrigin
    @Operation(summary = "Get one trainer", description = "Return trainer with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Trainer was not found")
    })
    @GetMapping("/{id}")
    public TrainerDto getTrainer(@PathVariable UUID id){
        TrainerDto trainer = TrainerMapper.INSTANCE.mapToDTO(trainerService.getTrainerById(id));
        if (trainer == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainer not found"
            );
        }else {
            return trainer;
        }
    }
}
