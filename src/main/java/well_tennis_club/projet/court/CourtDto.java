package well_tennis_club.projet.court;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourtDto {
    @Schema(name = "id",example = "1")
    private Long id;
    @Schema(name = "name",example = "Philippe Chatrier")
    private String name;
    @Schema(name = "start",example = "9:00")
    private String start;
    @Schema(name = "end",example = "21:00")
    private String end;
}
