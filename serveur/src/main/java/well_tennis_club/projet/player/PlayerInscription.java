package well_tennis_club.projet.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import well_tennis_club.projet.disponibility.DisponibilityDto;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PlayerInscription implements Serializable {
    @Schema(name = "name",example = "Nadal")
    private String name;
    @Schema(name = "surname",example = "Rafael")
    private String surname;
    @Schema(name = "birthday",example = "1986-06-03")
    private String birthday;
    @Schema(name = "courses",example = "4")
    private Long courses;
    @Schema(name = "level",example = "19")
    private Long level;
    @Schema(name = "email",example = "test@email.fr")
    private String email;
    @Schema(name = "validate",example = "true")
    private Boolean validate;

    private List<DisponibilityInscription> disponibilities;
}
