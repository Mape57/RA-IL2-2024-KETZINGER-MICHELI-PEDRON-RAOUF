package well_tennis_club.projet.disponibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class DisponibilityDto implements Serializable {
    @Schema(name = "id",example = "1")
    private UUID id;
    @Schema(name = "dayWeek",example = "5")
    private int dayWeek;
    @Schema(name = "open",example = "8:00")
    private String open;
    @Schema(name = "close",example = "18:00")
    private String close;
}
