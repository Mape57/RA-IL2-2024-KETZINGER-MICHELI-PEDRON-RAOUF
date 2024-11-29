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
    @GetMapping("/all")
    public List<CourtDto> getAllCourts(){
        List<CourtDto> list = CourtMapper.INSTANCE.mapToListDTO(courtService.getAllCourts());
        if (list == null || list.size() == 0){
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
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Court was not create")
    })
    @PostMapping
    public CourtDto createCourt(@RequestBody CourtDto courtDto){
        return CourtMapper.INSTANCE.mapToDTO(courtService.createCourt(CourtMapper.INSTANCE.mapToEntity(courtDto)));
    }

    @CrossOrigin
    @Operation(summary = "Update court",description = "Update court with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Court was not create")
    })
    @PutMapping
    public CourtDto updateCourt(@RequestBody CourtDto courtDto){
        return CourtMapper.INSTANCE.mapToDTO(courtService.updateCourt(CourtMapper.INSTANCE.mapToEntity(courtDto)));
    }

    @CrossOrigin
    @Operation(summary = "Delete court",description = "Delete court with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Court was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteCourt(@PathVariable Long id){
        CourtEntity entity = new CourtEntity();
        entity.setId(id);
        courtService.deleteCourt(entity);
    }
}
