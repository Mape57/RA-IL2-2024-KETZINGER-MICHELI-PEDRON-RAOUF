package well_tennis_club.projet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.SessionConstraintDto;
import well_tennis_club.projet.entity.SessionConstraintEntity;
import well_tennis_club.projet.mapper.SessionConstraintMapper;
import well_tennis_club.projet.service.SessionConstraintService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("constraints")
@Transactional
@CrossOrigin
public class SessionConstraintController {
	private final SessionConstraintService sessionConstraintService;

	@Autowired
	public SessionConstraintController(SessionConstraintService sessionConstraintService) {
		this.sessionConstraintService = sessionConstraintService;
	}

	@Operation(summary = "Get all constraints", description = "Returns all constraints")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Constraints were not found")
	})
	@GetMapping
	public List<SessionConstraintDto> getAllConstraints() {
		List<SessionConstraintDto> list = SessionConstraintMapper.INSTANCE.mapToListDTO(sessionConstraintService.getAllConstraints());
		if (list == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Constraints not found"
			);
		} else {
			return list;
		}
	}

	@Operation(summary = "Create constraint", description = "Create constraint with name, start and end from the dto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Constraint was not create")
	})
	@PostMapping
	public SessionConstraintDto createConstraint(@RequestBody SessionConstraintDto sessionconstraintDto) {
		SessionConstraintDto sessionConstraint = sessionconstraintDto;
		return SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.createConstraint(SessionConstraintMapper.INSTANCE.mapToEntity(sessionConstraint)));
	}

	@Operation(summary = "Update constraint", description = "Update constraint with id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully patched"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Constraint was not update")
	})
	@PatchMapping("/{id}")
	public SessionConstraintDto updateConstraint(@PathVariable UUID id, @RequestBody SessionConstraintDto sessionconstraintDto) {
		SessionConstraintDto constraint = SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.getConstraintById(id));
		if (constraint == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Constraint not found"
			);
		} else {
			SessionConstraintDto modif = sessionconstraintDto;
			modif.setId(id);
			return SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.updateConstraint(SessionConstraintMapper.INSTANCE.mapToEntity(modif)));
		}
	}

	@Operation(summary = "Delete constraint", description = "Delete constraint with id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Constraint was not delete")
	})
	@DeleteMapping("/{id}")
	public void deleteConstraint(@PathVariable UUID id) {
		SessionConstraintEntity constraint = new SessionConstraintEntity();
		constraint.setId(id);
		sessionConstraintService.deleteConstraint(constraint);
	}

	// ========================= DEPRECATED ========================= //

	@Deprecated(forRemoval = true)
	@Operation(summary = "Get constraint by id", description = "Returns constraint by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Constraint was not found")
	})
	@GetMapping("/{id}")
	public SessionConstraintDto getConstraintById(@PathVariable UUID id) {
		SessionConstraintDto constraint = SessionConstraintMapper.INSTANCE.mapToDTO(sessionConstraintService.getConstraintById(id));
		if (constraint == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Constraint not found"
			);
		} else {
			return constraint;
		}
	}
}
