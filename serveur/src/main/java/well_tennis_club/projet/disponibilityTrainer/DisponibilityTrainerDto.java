package well_tennis_club.projet.disponibilityTrainer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DisponibilityTrainerDto {
    @Schema(name = "idTrainer", example = "1")
    private UUID idTrainer;

    @Schema(name = "idDisponibility", example = "1")
    private UUID idDisponibility;
}
