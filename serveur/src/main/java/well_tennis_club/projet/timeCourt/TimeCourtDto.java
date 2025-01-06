package well_tennis_club.projet.timeCourt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeCourtDto {
    @Schema(name = "idCourt",example = "1")
    private Long idCourt;
    @Schema(name = "idTime",example = "1")
    private Long idTime;
}
