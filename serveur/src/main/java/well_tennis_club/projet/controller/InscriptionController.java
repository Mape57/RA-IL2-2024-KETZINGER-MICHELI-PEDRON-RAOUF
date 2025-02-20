package well_tennis_club.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import well_tennis_club.projet.dto.DisponibilityDto;
import well_tennis_club.projet.dto.DisponibilityInscriptionDto;
import well_tennis_club.projet.dto.PlayerDto;
import well_tennis_club.projet.dto.PlayerInscriptionDto;
import well_tennis_club.projet.entity.DisponibilityEntity;
import well_tennis_club.projet.entity.DisponibilityPlayerEntity;
import well_tennis_club.projet.entity.PlayerEntity;
import well_tennis_club.projet.key.DisponibilityPlayerKey;
import well_tennis_club.projet.mapper.DisponibilityMapper;
import well_tennis_club.projet.mapper.PlayerMapper;
import well_tennis_club.projet.service.DisponibilityPlayerService;
import well_tennis_club.projet.service.DisponibilityService;
import well_tennis_club.projet.service.InscriptionTokenService;
import well_tennis_club.projet.service.PlayerService;
import well_tennis_club.projet.tool.MailFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("inscription")
public class InscriptionController {
	private final PlayerService playerService;
	private final DisponibilityService disponibilityService;
	private final DisponibilityPlayerService disponibilityPlayerService;
	private final InscriptionTokenService inscriptionTokenService;
	private final MailFactory mailFactory;
	private final MailSender mailSender;

	@Autowired
	public InscriptionController(PlayerService playerService, DisponibilityService disponibilityService,
								 DisponibilityPlayerService disponibilityPlayerService, InscriptionTokenService inscriptionTokenService,
								 MailFactory mailFactory, MailSender mailSender) {
		this.playerService = playerService;
		this.disponibilityService = disponibilityService;
		this.disponibilityPlayerService = disponibilityPlayerService;
		this.inscriptionTokenService = inscriptionTokenService;
		this.mailFactory = mailFactory;
		this.mailSender = mailSender;
	}

	@PostMapping("/sendInscriptionToken")
	public ResponseEntity<String> inscrirePlayer(@RequestBody PlayerInscriptionDto playerInscriptionDto) {
		String token = UUID.randomUUID().toString();
		inscriptionTokenService.createInscriptionTokenForEmail(playerInscriptionDto.getEmail(), token);
		mailSender.send(mailFactory.constructInscriptionMail(token, playerInscriptionDto));

		return ResponseEntity.ok("Mail de confirmation envoyé");
	}

	@CrossOrigin
	@Operation(summary = "Create a player", description = "Create a player with name, surname, age, level and courses")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "500", description = "Internal server error - Players were not found")
	})
	@PostMapping
	public PlayerDto inscrirePlayer(@RequestParam("player") String player, @RequestParam("token") String token) {
		if (!inscriptionTokenService.isTokenValid(token)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token is not valid");
		}

		ObjectMapper objectMapper = new ObjectMapper();
		PlayerInscriptionDto playerInscriptionDto;
		try {
			playerInscriptionDto = objectMapper.readValue(player, PlayerInscriptionDto.class);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid player");
		}

		PlayerDto playerDto = new PlayerDto();
		playerDto.setName(playerInscriptionDto.getName());
		playerDto.setSurname(playerInscriptionDto.getSurname());
		playerDto.setBirthday(playerInscriptionDto.getBirthday());
		playerDto.setCourses(playerInscriptionDto.getCourses());
		playerDto.setLevel(playerInscriptionDto.getLevel());
		playerDto.setEmail(playerInscriptionDto.getEmail());
		playerDto.setId(UUID.randomUUID());
		playerDto.setValidate(false);
		playerDto.setDisponibilities(new ArrayList<>());

		List<DisponibilityInscriptionDto> lists = playerInscriptionDto.getDisponibilities();
		List<DisponibilityDto> disponibilityDtos = new ArrayList<>();
		for (DisponibilityInscriptionDto disponibilityInscriptionDto : lists) {
			DisponibilityDto disponibilityDto = new DisponibilityDto();
			disponibilityDto.setOpen(disponibilityInscriptionDto.getOpen());
			disponibilityDto.setClose(disponibilityInscriptionDto.getClose());
			disponibilityDto.setDayWeek(disponibilityInscriptionDto.getDayWeek());
			disponibilityDto.setId(UUID.randomUUID());
			disponibilityDtos.add(disponibilityDto);

			DisponibilityEntity entity = disponibilityService.createDisponibility(DisponibilityMapper.INSTANCE.mapToEntity(disponibilityDto));
			disponibilityDtos.add(DisponibilityMapper.INSTANCE.mapToDTO(entity));
		}

		PlayerEntity entity = playerService.createPlayer(PlayerMapper.INSTANCE.mapToEntity(playerDto));
		playerDto = PlayerMapper.INSTANCE.mapToDTO(entity);

		for (DisponibilityDto disponibilityDto : disponibilityDtos) {
			DisponibilityPlayerEntity disponibility = new DisponibilityPlayerEntity();
			DisponibilityPlayerKey key = new DisponibilityPlayerKey();
			key.setIdPlayer(entity.getId());
			key.setIdDisponibility(disponibilityDto.getId());
			disponibility.setDisponibilityPlayerKey(key);
			disponibilityPlayerService.createDisponibilityPlayer(disponibility);
		}

		UUID id = playerDto.getId();
		inscriptionTokenService.deleteByToken(token);
		return PlayerMapper.INSTANCE.mapToDTO(playerService.getPlayerById(id));
	}
}
