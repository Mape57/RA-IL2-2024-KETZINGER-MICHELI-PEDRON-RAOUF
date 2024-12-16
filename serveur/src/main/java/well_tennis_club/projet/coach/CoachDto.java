package well_tennis_club.projet.coach;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CoachDto implements Serializable {
    @Schema(name = "id",example = "1")
    private Long id;
    @Schema(name = "name",example = "Nadal")
    private String name;
    @Schema(name = "surname",example = "Toni")
    private String surname;
    @Schema(name = "levels",example = "17-19")
    private String levels;
    @Schema(name = "ages",example = "15-40")
    private String ages;
}
