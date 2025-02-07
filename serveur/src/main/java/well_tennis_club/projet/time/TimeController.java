package well_tennis_club.projet.time;

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
@RequestMapping("time")
@Transactional
public class TimeController {
    private final TimeService timeService;
    @Autowired
    public TimeController(TimeService timeService){this.timeService = timeService;}

    @CrossOrigin
    @Operation(summary = "Get all times",description = "Returns all times")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Times were not found")
    })
    @GetMapping
    public List<TimeDto> getAllTime(){
        List<TimeDto> list = TimeMapper.INSTANCE.mapToListDTO(timeService.getAllTimes());
        if (list == null || list.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Times not found"
            );
        }else {
            return list;
        }
    }

    @CrossOrigin
    @Operation(summary = "Create time",description = "Create time with day, start and stop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Time was not create")
    })
    @PostMapping
    public TimeDto createTime(@RequestBody TimeDto timeDto){
        TimeDto time = timeDto;
        time.setId(UUID.randomUUID());
        return TimeMapper.INSTANCE.mapToDTO(timeService.createTime(TimeMapper.INSTANCE.mapToEntity(time)));
    }

    @CrossOrigin
    @Operation(summary = "Update time",description = "Update time with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Internal server error - Time was not update")
    })
    @PatchMapping("/{id}")
    public TimeDto updateTime(@PathVariable UUID id, @RequestBody TimeDto timeDto){
        TimeDto dto = TimeMapper.INSTANCE.mapToDTO(timeService.getTimeById(id));
        if (dto == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Time not found"
            );
        }else {
            TimeDto modif = timeDto;
            modif.setId(id);
            return TimeMapper.INSTANCE.mapToDTO(timeService.updateTime(TimeMapper.INSTANCE.mapToEntity(modif)));
        }
    }

    @CrossOrigin
    @Operation(summary = "Delete time",description = "Delete time with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Time was not delete")
    })
    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable UUID id){
        TimeEntity entity = new TimeEntity();
        entity.setId(id);
        timeService.deleteTime(entity);
    }

    @CrossOrigin
    @Operation(summary = "Get one time",description = "Get one time with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Time was not found")
    })
    @GetMapping("/{id}")
    public TimeDto getTime(@PathVariable UUID id){
        TimeDto dto = TimeMapper.INSTANCE.mapToDTO(timeService.getTimeById(id));
        if (dto == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Time not found"
            );
        }else {
            return dto;
        }
    }
}
