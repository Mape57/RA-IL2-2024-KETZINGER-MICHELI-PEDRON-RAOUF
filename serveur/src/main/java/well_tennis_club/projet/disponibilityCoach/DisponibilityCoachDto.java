package well_tennis_club.projet.disponibilityCoach;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DisponibilityCoachDto {
    @Schema(name = "idCoach", example = "1")
    private UUID idCoach;

    @Schema(name = "idDisponibility", example = "1")
    private UUID idDisponibility;
}
