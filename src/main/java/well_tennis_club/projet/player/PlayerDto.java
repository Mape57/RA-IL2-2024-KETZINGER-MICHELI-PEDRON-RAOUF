package well_tennis_club.projet.player;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerDto implements Serializable{
    @Schema(name="id",example = "1")
    private Long id;
    @Schema(name = "name",example = "Nadal")
    private String name;
    @Schema(name = "surname",example = "Rafael")
    private String surname;
    @Schema(name = "birthday",example = "03/06/1986")
    private String birthday;
    @Schema(name = "courses",example = "4")
    private Long courses;
    @Schema(name = "level",example = "19")
    private Long level;
}
