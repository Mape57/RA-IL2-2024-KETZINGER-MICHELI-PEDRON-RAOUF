package well_tennis.java.seance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SeanceDto {
    @Schema(name = "jour", example = "lundi")
    private String jour;

    @Schema(name = "debut", example = "18:00")
    private String debut;

    @Schema(name = "fin", example = "19:00")
    private String fin;

    @Schema(name = "entraineur", example = "ID")
    private String entraineur;

    @Schema(name = "joueurs", example = "[ID, ID, ...]")
    private List<String> joueurs;
}