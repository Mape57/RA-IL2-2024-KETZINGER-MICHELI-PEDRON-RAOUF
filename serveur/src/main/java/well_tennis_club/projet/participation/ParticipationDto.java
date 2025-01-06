package well_tennis_club.projet.participation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipationDto {
    @Schema(name = "idPlayer",example = "1")
    private Long idPlayer;
    @Schema(name = "idSession",example = "1")
    private Long idSession;
}
