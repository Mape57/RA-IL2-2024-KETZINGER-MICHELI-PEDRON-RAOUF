package well_tennis_club.projet.court;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import well_tennis_club.projet.time.TimeDto;

import java.util.List;

@Data
@NoArgsConstructor
public class CourtDto {
    @Schema(name = "id",example = "1")
    private Long id;
    @Schema(name = "name",example = "Philippe Chatrier")
    private String name;

    private List<TimeDto> times;
}
