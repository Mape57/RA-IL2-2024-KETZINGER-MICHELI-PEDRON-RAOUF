package well_tennis_club.projet.disponibilityPlayer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisponibilityPlayerDto {
    @Schema(name = "idPlayer", example = "1")
    private Long idPlayer;

    @Schema(name = "idDisponibility", example = "1")
    private Long idDisponibility;

}
