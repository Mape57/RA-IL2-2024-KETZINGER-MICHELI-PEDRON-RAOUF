package well_tennis_club.projet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.ParticipationDto;
import well_tennis_club.projet.entity.ParticipationEntity;
import well_tennis_club.projet.key.ParticipationKey;
import well_tennis_club.projet.mapper.ParticipationMapper;
import well_tennis_club.projet.service.ParticipationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("participations")
@Transactional
public class ParticipationController {

	private final ParticipationService participationService;

	@Autowired
	public ParticipationController(ParticipationService participationService) {
		this.participationService = participationService;
	}

	@CrossOrigin
	@Operation(summary = "Get all participation", description = "Returns all participation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Participations were not found")
	})
	@GetMapping
	public List<ParticipationDto> getAllParticipation() {
		List<ParticipationDto> list = ParticipationMapper.INSTANCE.mapToListDTO(participationService.getAllParticipation());
		if (list == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Participations not found"
			);
		} else {
			return list;
		}
	}

	@CrossOrigin
	@Operation(summary = "Add participation", description = "Add participation with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Participation was not create")
	})
	@PostMapping
	public ParticipationDto createParticipation(@RequestBody ParticipationDto participationDto) {
		return ParticipationMapper.INSTANCE.mapToDTO(participationService.createParticipation(ParticipationMapper.INSTANCE.mapToEntity(participationDto)));
	}

	@CrossOrigin
	@Operation(summary = "Delete participation", description = "Delete participation with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Participation was not delete")
	})
	@DeleteMapping("/{idPlayer}/{idSession}")
	public void deleteParticipation(@PathVariable UUID idPlayer, @PathVariable UUID idSession) {
		ParticipationEntity participationEntity = new ParticipationEntity();
		ParticipationKey key = new ParticipationKey();
		key.setIdPlayer(idPlayer);
		key.setIdSession(idSession);
		participationEntity.setParticipationKey(key);
		participationService.deleteParticipation(participationEntity);
	}

	@CrossOrigin
	@Operation(summary = "Get all participation for player", description = "Return all participation for player with his id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Participations were not found")
	})
	@GetMapping("/{id}")
	public List<ParticipationDto> getParticipationPlayer(@PathVariable UUID id) {
		List<ParticipationDto> dtos = ParticipationMapper.INSTANCE.mapToListDTO(participationService.getParticipationForPlayer(id));
		if (dtos == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Participations not found"
			);
		} else {
			return dtos;
		}
	}
}
