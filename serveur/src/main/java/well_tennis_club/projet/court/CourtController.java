package well_tennis_club.projet.court;

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
@RequestMapping("courts")
@Transactional
public class CourtController {
    private final CourtService courtService;
    @Autowired
    public CourtController(CourtService courtService){this.courtService = courtService;}

    @CrossOrigin
    @Operation(summary = "Get all courts", description = "Returns all courts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Courts were not found")
    })
    @GetMapping()
    public List<CourtDto> getAllCourts(){
        List<CourtDto> list = CourtMapper.INSTANCE.mapToListDTO(courtService.getAllCourts());
        if (list == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Courts not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Create court",description = "Create court with name, start and end from the dto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Court was not create")
    })
    @PostMapping
    public CourtDto createCourt(@RequestBody CourtDto courtDto){
        CourtDto court = courtDto;
        court.setId(UUID.randomUUID());
        return CourtMapper.INSTANCE.mapToDTO(courtService.createCourt(CourtMapper.INSTANCE.mapToEntity(court)));
    }

    @CrossOrigin
    @Operation(summary = "Update court",description = "Update court with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully patched"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Court was not update")
    })
    @PatchMapping("/{id}")
    public CourtDto updateCourt(@PathVariable UUID id, @RequestBody CourtDto courtDto){
        CourtDto court = CourtMapper.INSTANCE.mapToDTO(courtService.getCourtById(id));
        if (court == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainer not found"
            );
        }else {
            CourtDto modif = courtDto;
            modif.setId(id);
            return CourtMapper.INSTANCE.mapToDTO(courtService.updateCourt(CourtMapper.INSTANCE.mapToEntity(modif)));
        }
    }

    @CrossOrigin
    @Operation(summary = "Delete court",description = "Delete court with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Session was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteCourt(@PathVariable UUID id){
        CourtEntity entity = new CourtEntity();
        entity.setId(id);
        courtService.deleteCourt(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get one court", description = "Return court with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Court was not found")
    })
    @GetMapping("/{id}")
    public CourtDto getCourt(@PathVariable UUID id){
        CourtDto modif = CourtMapper.INSTANCE.mapToDTO(courtService.getCourtById(id));
        if (modif == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Court not found"
            );
        }else {
            return modif;
        }
    }
}
