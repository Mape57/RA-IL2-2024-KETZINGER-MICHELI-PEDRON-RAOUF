package well_tennis_club.projet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.DisponibilityPlayerDto;
import well_tennis_club.projet.entity.DisponibilityPlayerEntity;
import well_tennis_club.projet.key.DisponibilityPlayerKey;
import well_tennis_club.projet.mapper.DisponibilityPlayerMapper;
import well_tennis_club.projet.service.DisponibilityPlayerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("disponibilityPlayer")
@Transactional
public class DisponibilityPlayerController {
	private final DisponibilityPlayerService disponibilityPlayerService;

	@Autowired
	public DisponibilityPlayerController(DisponibilityPlayerService service) {
		this.disponibilityPlayerService = service;
	}

	@CrossOrigin
	@Operation(summary = "Get all disponibilities player", description = "Returns all disponibilities for all players")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
	})
	@GetMapping
	public List<DisponibilityPlayerDto> getAllDisponibilitiesPlayer() {
		List<DisponibilityPlayerDto> list = DisponibilityPlayerMapper.INSTANCE.mapToListDTO(disponibilityPlayerService.getAllDisponibilitiesPlayer());
		if (list == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Disponibilities not found"
			);
		} else {
			return list;
		}
	}

	@CrossOrigin
	@Operation(summary = "Add disponibility for player", description = "Add disponibility for player with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not create")
	})
	@PostMapping
	public DisponibilityPlayerDto createDisponibilityPlayer(@RequestBody DisponibilityPlayerDto dto) {
		return DisponibilityPlayerMapper.INSTANCE.mapToDTO(disponibilityPlayerService.createDisponibilityPlayer(DisponibilityPlayerMapper.INSTANCE.mapToEntity(dto)));
	}

	@CrossOrigin
	@Operation(summary = "Delete disponibility for player", description = "Delete disponibility for player with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not delete")
	})
	@DeleteMapping("/{idPlayer}/{idDisponibility}")
	public void deleteDisponibilityPlayer(@PathVariable UUID idPlayer, @PathVariable UUID idDisponibility) {
		DisponibilityPlayerEntity entity = new DisponibilityPlayerEntity();
		DisponibilityPlayerKey key = new DisponibilityPlayerKey();
		key.setIdPlayer(idPlayer);
		key.setIdDisponibility(idDisponibility);
		entity.setDisponibilityPlayerKey(key);
		disponibilityPlayerService.deleteDisponibilityPlayer(entity);
	}

	@CrossOrigin
	@Operation(summary = "Get all disponibilities for player", description = "Return all disponibilities for player with his id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
	})
	@GetMapping("/{id}")
	public List<DisponibilityPlayerDto> getDisponibilitiesPlayer(@PathVariable UUID id) {
		List<DisponibilityPlayerDto> dtos = DisponibilityPlayerMapper.INSTANCE.mapToListDTO(disponibilityPlayerService.getDisponibilitiesForPlayer(id));
		if (dtos == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Disponibilities not found"
			);
		} else {
			return dtos;
		}
	}
}
