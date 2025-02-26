package well_tennis_club.projet.core.session_constraint.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionConstraintDto {
	@Schema(name = "id", example = "1")
	private UUID id;

	@Schema(name = "inf_age", example = "1")
	private int infAge;

	@Schema(name = "sup_age", example = "10")
	private int supAge;

	@Schema(name = "inf_level", example = "1")
	private int infLevel;

	@Schema(name = "sup_level", example = "10")
	private int supLevel;

	@Schema(name = "inf_group", example = "2")
	private int infGroup;

	@Schema(name = "sup_group", example = "4")
	private int supGroup;

	@Schema(name = "age_diff", example = "3")
	private int ageDiff;

	@Schema(name = "level_diff", example = "2")
	private int levelDiff;

	@Schema(name = "duration", example = "1.5")
	private int duration;
}
