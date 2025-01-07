package well_tennis_club.projet.participation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ParticipationDto {
    @Schema(name = "idPlayer",example = "1")
    private UUID idPlayer;
    @Schema(name = "idSession",example = "1")
    private UUID idSession;
}
