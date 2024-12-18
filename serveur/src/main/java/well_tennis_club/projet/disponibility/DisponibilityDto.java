package well_tennis_club.projet.disponibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisponibilityDto {
    @Schema(name = "id",example = "1")
    private Long id;
    @Schema(name = "day",example = "Monday")
    private String day;
    @Schema(name = "open",example = "8:00")
    private String open;
    @Schema(name = "close",example = "18:00")
    private String close;
}
