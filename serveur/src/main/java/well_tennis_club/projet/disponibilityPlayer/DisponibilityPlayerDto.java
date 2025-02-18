package well_tennis_club.projet.disponibilityPlayer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DisponibilityPlayerDto {
    @Schema(name = "idPlayer", example = "1")
    private UUID idPlayer;

    @Schema(name = "idDisponibility", example = "1")
    private UUID idDisponibility;

}
