package well_tennis_club.projet.timeCourt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TimeCourtDto {
    @Schema(name = "idCourt",example = "1")
    private UUID idCourt;
    @Schema(name = "idTime",example = "1")
    private UUID idTime;
}
