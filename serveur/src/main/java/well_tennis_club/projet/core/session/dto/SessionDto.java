package well_tennis_club.projet.core.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.court.dto.CourtDto;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionDto {
	@Schema(name = "id", example = "1")
	private UUID id;
	@Schema(name = "dayWeek", example = "4")
	private int dayWeek;
	@Schema(name = "start", example = "8:00")
	private String start;
	@Schema(name = "stop", example = "10:00")
	private String stop;

	private CourtDto idCourt;

	private TrainerDto idTrainer;

	private List<PlayerDto> players;
}
