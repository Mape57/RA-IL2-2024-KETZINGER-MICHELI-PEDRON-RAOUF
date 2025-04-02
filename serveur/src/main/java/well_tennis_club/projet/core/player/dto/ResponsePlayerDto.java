package well_tennis_club.projet.core.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.disponibility.dto.DisponibilityDto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponsePlayerDto implements Serializable {
	@Schema(name = "id", example = "1")
	private UUID id;

	@Schema(name = "name", example = "Nadal")
	private String name;

	@Schema(name = "surname", example = "Rafael")
	private String surname;

	@Schema(name = "birthday", example = "1986-06-03")
	private String birthday;

	@Schema(name = "courses", example = "4")
	private Long courses;

	@Schema(name = "level", example = "19")
	private Long level;

	@Schema(name = "email", example = "example@mail.fr")
	private String email;

	@Schema(name = "validate", example = "true")
	private Boolean validate;

	@Schema(name = "numberOfSessions", example = "4")
	private Long numberOfSessions;

	@Schema(name = "phone", example = "0606060606")
	private String phone;

	@Schema(name = "phone2", example = "0606060606")
	private String phone2;

	@Schema(name="photo",example = "true")
	private boolean photo;

	private List<DisponibilityDto> disponibilities;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResponsePlayerDto that = (ResponsePlayerDto) o;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
