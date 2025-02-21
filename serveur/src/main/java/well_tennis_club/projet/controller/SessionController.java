package well_tennis_club.projet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.SessionDto;
import well_tennis_club.projet.entity.SessionEntity;
import well_tennis_club.projet.mapper.SessionMapper;
import well_tennis_club.projet.service.SessionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("sessions")
@Transactional
@CrossOrigin
public class SessionController {
	private final SessionService sessionService;

	@Autowired
	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Operation(summary = "Get all sessions", description = "Returns all sessions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Sessions were not found")
	})
	@GetMapping
	public List<SessionDto> getAllSessions() {
		List<SessionDto> list = SessionMapper.INSTANCE.mapToListDTO(sessionService.getAllSessions());
		if (list == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Sessions not found"
			);
		} else {
			return list;
		}
	}

	@Operation(summary = "Create session", description = "Create session with day, start, stop, idTrainer and idCourt")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Session was not create")
	})
	@PostMapping
	public SessionDto createSession(@RequestBody SessionDto sessionDto) {
		SessionDto session = sessionDto;
		session.setId(UUID.randomUUID());
		return SessionMapper.INSTANCE.mapToDTO(sessionService.createSession(SessionMapper.INSTANCE.mapToEntity(session)));
	}

	@Operation(summary = "Update session", description = "Update session with id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated"),
			@ApiResponse(responseCode = "404", description = "Internal server error - Session was not update")
	})
	@PatchMapping("/{id}")
	public SessionDto updateSession(@PathVariable UUID id, @RequestBody SessionDto sessionDto) {
		SessionDto session = SessionMapper.INSTANCE.mapToDTO(sessionService.getSessionById(id));
		if (session == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Session not found"
			);
		} else {
			SessionDto modif = sessionDto;
			modif.setId(id);
			return SessionMapper.INSTANCE.mapToDTO(sessionService.updateSession(SessionMapper.INSTANCE.mapToEntity(modif)));
		}
	}

	@Operation(summary = "Delete session", description = "Delete session with id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Court was not delete")
	})
	@DeleteMapping("/{id}")
	public void deleteSession(@PathVariable UUID id) {
		SessionEntity entity = new SessionEntity();
		entity.setId(id);
		sessionService.deleteSession(entity);
	}

	// ========================= DEPRECATED ========================= //

	@Deprecated(forRemoval = true)
	@Operation(summary = "Get one session", description = "Return session with id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Session was not found")
	})
	@GetMapping("/{id}")
	public SessionDto getSession(@PathVariable UUID id) {
		SessionDto modif = SessionMapper.INSTANCE.mapToDTO(sessionService.getSessionById(id));
		if (modif == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Session not found"
			);
		} else {
			return modif;
		}
	}
}
