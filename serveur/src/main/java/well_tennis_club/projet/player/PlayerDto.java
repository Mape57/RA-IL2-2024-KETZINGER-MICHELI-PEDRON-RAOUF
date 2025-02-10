package well_tennis_club.projet.player;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import well_tennis_club.projet.disponibility.DisponibilityDto;

@Data
@NoArgsConstructor
public class PlayerDto implements Serializable{
    @Schema(name="id",example = "1")
    private UUID id;
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

    private List<DisponibilityDto> disponibilities;
}
