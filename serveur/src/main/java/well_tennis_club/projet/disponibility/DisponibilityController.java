package well_tennis_club.projet.disponibility;

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
@RequestMapping("disponibility")
@Transactional
public class DisponibilityController {
    private final DisponibilityService disponibilityService;
    @Autowired
    public DisponibilityController(DisponibilityService disponibilityService){this.disponibilityService = disponibilityService;}

    @CrossOrigin
    @Operation(summary = "Get all disponibilities",description = "Returns all disponibilities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
    })
    @GetMapping
    public List<DisponibilityDto> getAllDisponibilities(){
        List<DisponibilityDto> list = DisponibilityMapper.INSTANCE.mapToListDTO(disponibilityService.getAllDisponibilities());
        if (list == null || list.size() == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Disponibilities not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Create disponibility",description = "Create disponibility with day, open and close")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Disponibility was not create")
    })
    @PostMapping
    public DisponibilityDto createDisponibility(@RequestBody DisponibilityDto disponibilityDto){
        return DisponibilityMapper.INSTANCE.mapToDTO(disponibilityService.createDisponibility(DisponibilityMapper.INSTANCE.mapToEntity(disponibilityDto)));
    }

    @CrossOrigin
    @Operation(summary = "Update disponibility",description = "Update disponibility with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Disponibility was not update")
    })
    @PatchMapping("/{id}")
    public DisponibilityDto updateDisponibility(@PathVariable Long id,@RequestBody DisponibilityDto disponibilityDto){
        DisponibilityDto dto = DisponibilityMapper.INSTANCE.mapToDTO(disponibilityService.getDisponibilityById(id));
        if (dto == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Disponibility not found"
            );
        }else {
            DisponibilityDto modif = disponibilityDto;
            modif.setId(id);
            return DisponibilityMapper.INSTANCE.mapToDTO(disponibilityService.updateDisponibility(DisponibilityMapper.INSTANCE.
                    mapToEntity(modif)));
        }
    }

    @CrossOrigin
    @Operation(summary = "Delete disponibility",description = "Delete disponibility with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteDisponibility(@PathVariable Long id){
        DisponibilityEntity entity = new DisponibilityEntity();
        entity.setId(id);
        disponibilityService.deleteDisponibility(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get one disponibility",description = "Return disponibility with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not found")
    })
    @GetMapping("/{id}")
    public DisponibilityDto getDisponibility(@PathVariable Long id){
        DisponibilityDto modif = DisponibilityMapper.INSTANCE.mapToDTO(disponibilityService.getDisponibilityById(id));
        if (modif == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Disponibility not found"
            );
        }else {
            return modif;
        }
    }
}
