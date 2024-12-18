package well_tennis_club.projet.time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeDto {
    @Schema(name = "id",example = "1")
    private Long id;
    @Schema(name = "day",example = "Monday")
    private String day;
    @Schema(name = "start",example = "8:00")
    private String start;
    @Schema(name = "stop",example = "20:00")
    private String stop;
}
