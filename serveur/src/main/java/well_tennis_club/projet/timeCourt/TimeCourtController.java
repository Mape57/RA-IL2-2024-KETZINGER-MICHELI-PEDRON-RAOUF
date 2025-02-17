package well_tennis_club.projet.timeCourt;

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
@RequestMapping("timeCourt")
@Transactional
public class TimeCourtController {
    private final TimeCourtService timeCourtService;
    @Autowired
    public TimeCourtController(TimeCourtService timeCourtService){this.timeCourtService = timeCourtService;}

    @CrossOrigin
    @Operation(summary = "Get all times court",description = "Returns all times for all courts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Times were not found")
    })
    @GetMapping
    public List<TimeCourtDto>  getAllTimeCourt(){
        List<TimeCourtDto> list = TimeCourtMapper.INSTANCE.mapToListDTO(timeCourtService.getAllTimeCourt());
        if(list == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Times not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Add time for court", description = "Add time for court with their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Time was not create")
    })
    @PostMapping
    public TimeCourtDto createTimeCourt(@RequestBody TimeCourtDto dto){
        return TimeCourtMapper.INSTANCE.mapToDTO(timeCourtService.createTimeCourt(TimeCourtMapper.INSTANCE.mapToEntity(dto)));
    }

    @CrossOrigin
    @Operation(summary = "Delete time for court",description = "Delete time for court with their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Time was not delete")
    })
    @DeleteMapping("/{idCourt}/{idTime}")
    public void deleteTimeCourt(@PathVariable UUID idCourt, @PathVariable UUID idTime){
        TimeCourtEntity entity = new TimeCourtEntity();
        TimeCourtKey key = new TimeCourtKey();
        key.setIdCourt(idCourt);
        key.setIdTime(idTime);
        entity.setTimeCourtKey(key);
        timeCourtService.deleteTimeCourt(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get all times for court", description = "Returns all times for court with his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Times were not found")
    })
    @GetMapping("/{id}")
    public List<TimeCourtDto> getTimeCourt(@PathVariable UUID id){
        List<TimeCourtDto> dtos = TimeCourtMapper.INSTANCE.mapToListDTO(timeCourtService.getTimeCourtForCourt(id));
        if (dtos == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Times not found"
            );
        }else {
            return dtos;
        }
    }
}
