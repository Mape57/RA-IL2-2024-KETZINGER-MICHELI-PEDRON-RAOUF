package well_tennis_club.projet.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.coach.CoachDto;
import well_tennis_club.projet.court.CourtDto;
import well_tennis_club.projet.player.PlayerDto;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SessionDto {
    @Schema(name = "id",example = "1")
    private UUID id;
    @Schema(name = "day",example = "Monday")
    private String day;
    @Schema(name = "start",example = "8:00")
    private String start;
    @Schema(name = "stop",example = "10:00")
    private String stop;

    private CourtDto idCourt;

    private CoachDto idCoach;

    private List<PlayerDto> players;
}
