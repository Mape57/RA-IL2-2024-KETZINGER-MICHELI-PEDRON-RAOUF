package well_tennis_club.data_converter.to_domain;

import org.springframework.stereotype.Component;
import well_tennis_club.projet.entity.PlayerEntity;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.domain.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
public class PlayerConverter {
	/**
	 * Convert a PlayerEntity to a Player
	 *
	 * @param playerEntity PlayerEntity to convert
	 * @return Player object with no session constraints
	 */
	public static Player from(PlayerEntity playerEntity) {
		UUID id = playerEntity.getId();
		LocalDate birthDate = parseBirthDate(playerEntity.getBirthday());
		int level = playerEntity.getLevel().intValue();
		int sessionPerWeek = playerEntity.getCourses().intValue();
		List<Timeslot> availability = playerEntity.getDisponibilities().stream().map(TimeslotConverter::from).toList();
		return new Player(id, birthDate, level, sessionPerWeek, availability, null);
	}

	private static LocalDate parseBirthDate(String birthDate) {
		return LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
