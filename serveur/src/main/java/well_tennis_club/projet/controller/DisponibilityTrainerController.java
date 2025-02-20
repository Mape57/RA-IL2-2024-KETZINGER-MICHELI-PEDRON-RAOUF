package well_tennis_club.projet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.DisponibilityTrainerDto;
import well_tennis_club.projet.entity.DisponibilityTrainerEntity;
import well_tennis_club.projet.key.DisponibilityTrainerKey;
import well_tennis_club.projet.mapper.DisponibilityTrainerMapper;
import well_tennis_club.projet.service.DisponibilityTrainerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("disponibilityTrainer")
@Transactional
public class DisponibilityTrainerController {
	private final DisponibilityTrainerService disponibilityTrainerService;

	@Autowired
	public DisponibilityTrainerController(DisponibilityTrainerService disponibilityTrainerService) {
		this.disponibilityTrainerService = disponibilityTrainerService;
	}

	@CrossOrigin
	@Operation(summary = "Get all disponibilities trainer", description = "Returns all disponibilities")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
	})
	@GetMapping
	public List<DisponibilityTrainerDto> getAllDisponibilitiesTrainer() {
		List<DisponibilityTrainerDto> list = DisponibilityTrainerMapper.INSTANCE.mapToListDTO(disponibilityTrainerService.getAllDisponibilitiesTrainer());
		if (list == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Disponibilities not found"
			);
		} else {
			return list;
		}
	}

	@CrossOrigin
	@Operation(summary = "Add disponibility for trainer", description = "Add disponibility for trainer with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not create")
	})
	@PostMapping
	public DisponibilityTrainerDto createDisponibilityTrainer(@RequestBody DisponibilityTrainerDto dto) {
		return DisponibilityTrainerMapper.INSTANCE.mapToDTO(disponibilityTrainerService.createDisponibilityTrainer(DisponibilityTrainerMapper.INSTANCE.mapToEntity(dto)));
	}

	@CrossOrigin
	@Operation(summary = "Delete disponibility for trainer", description = "Delete disponibility for trainer with their id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibility was not delete")
	})
	@DeleteMapping("/{idTrainer}/{idDisponibility}")
	public void deleteDisponibilityTrainer(@PathVariable UUID idTrainer, @PathVariable UUID idDisponibility) {
		DisponibilityTrainerEntity entity = new DisponibilityTrainerEntity();
		DisponibilityTrainerKey key = new DisponibilityTrainerKey();
		key.setIdTrainer(idTrainer);
		key.setIdDisponibility(idDisponibility);
		entity.setDisponibilityTrainerKey(key);
		disponibilityTrainerService.deleteDisponibilityTrainer(entity);
	}

	@CrossOrigin
	@Operation(summary = "Get all disponibilities for trainer", description = "Return all disponibilities for trainer with his id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Disponibilities were not found")
	})
	@GetMapping("/{id}")
	public List<DisponibilityTrainerDto> getDisponibilitiesTrainer(@PathVariable UUID id) {
		List<DisponibilityTrainerDto> dtos = DisponibilityTrainerMapper.INSTANCE.mapToListDTO(disponibilityTrainerService.getDisponibilityForTrainer(id));
		if (dtos == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Disponibilities not found"
			);
		} else {
			return dtos;
		}
	}
}
