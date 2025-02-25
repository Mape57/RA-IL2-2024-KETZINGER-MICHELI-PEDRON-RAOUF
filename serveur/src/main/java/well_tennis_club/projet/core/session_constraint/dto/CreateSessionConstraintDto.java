package well_tennis_club.projet.core.session_constraint.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateSessionConstraintDto {
	@Schema(name = "inf_age", example = "1")
	@NotNull(message = "L'age minimum (inf_age) est obligatoire")
	@Min(value = 1, message = "L'age minimum (inf_age) doit être supérieur ou égal à 1")
	private int infAge;

	@Schema(name = "sup_age", example = "10")
	@NotNull(message = "L'age maximum (sup_age) est obligatoire")
	@Min(value = 1, message = "L'age maximum (sup_age) doit être supérieur ou égal à 1")
	private int supAge;

	@Schema(name = "inf_level", example = "1")
	@NotNull(message = "Le niveau minimum (inf_level) est obligatoire")
	@Min(value = 0, message = "Le niveau minimum (inf_level) doit être supérieur ou égal à 0")
	private int infLevel;

	@Schema(name = "sup_level", example = "10")
	@NotNull(message = "Le niveau maximum (sup_level) est obligatoire")
	@Max(value = 20, message = "Le niveau maximum (sup_level) doit être inférieur ou égal à 20")
	private int supLevel;

	@Schema(name = "inf_group", example = "2")
	@NotNull(message = "La taille de groupe minimum (inf_group) est obligatoire")
	@Min(value = 1, message = "La taille de groupe minimum (inf_group) doit être supérieur ou égal à 1")
	private int infGroup;

	@Schema(name = "sup_group", example = "4")
	@NotNull(message = "La taille de groupe maximum (sup_group) est obligatoire")
	@Min(value = 1, message = "La taille de groupe maximum (sup_group) doit être supérieur ou égal à 1")
	private int supGroup;

	@Schema(name = "age_diff", example = "3")
	@NotNull(message = "La différence d'age (age_diff) est obligatoire")
	@Min(value = 0, message = "La différence d'age (age_diff) doit être supérieur ou égal à 0")
	private int ageDiff;

	@Schema(name = "level_diff", example = "2")
	@NotNull(message = "La différence de niveau (level_diff) est obligatoire")
	@Min(value = 0, message = "La différence de niveau (level_diff) doit être supérieur ou égal à 0")
	private int levelDiff;

	@Schema(name = "duration", example = "60")
	@NotNull(message = "La durée (duration) est obligatoire")
	@Min(value = 1, message = "La durée (duration) doit être supérieur ou égal à 1")
	private int duration;
}
