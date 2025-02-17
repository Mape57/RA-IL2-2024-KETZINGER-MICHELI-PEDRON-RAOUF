package well_tennis_club.projet.time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TimeDto {
    @Schema(name = "id",example = "1")
    private UUID id;
    @Schema(name = "dayWeek",example = "3")
    private int dayWeek;
    @Schema(name = "start",example = "8:00")
    private String start;
    @Schema(name = "stop",example = "20:00")
    private String stop;
}
