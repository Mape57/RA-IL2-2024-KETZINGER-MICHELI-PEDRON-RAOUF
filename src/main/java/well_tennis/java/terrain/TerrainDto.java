package well_tennis.java.terrain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis.java.horaire.HoraireDto;
import well_tennis.java.seance.SeanceDto;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TerrainDto {
    @Schema(name = "nom", example = "Terrain 1")
    private String nom;

    private List<HoraireDto> horaires;
    private List<SeanceDto> seances;
}
