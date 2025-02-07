package well_tennis_club.projet.court;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import well_tennis_club.projet.time.TimeDto;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CourtDto {
    @Schema(name = "id",example = "1")
    private UUID id;
    @Schema(name = "name",example = "Philippe Chatrier")
    private String name;

    private List<TimeDto> times;
}
