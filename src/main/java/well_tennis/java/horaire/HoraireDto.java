package well_tennis.java.horaire;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoraireDto {
    @Schema(name = "jour", example = "lundi")
    private String jour;

    @Schema(name = "ouverture", example = "18:00")
    private String ouverture;

    @Schema(name = "fermeture", example = "22:30")
    private String fermeture;
}