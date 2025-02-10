package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.player.PlayerEntity;
import well_tennis_club.projet.sessionConstraint.SessionConstraintEntity;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class PlayerEntityConverter {
	public static PlayerEntity from(String name, String surname, int age, int level, int weeklySession, List<Timeslot> availability, List<SessionConstraintEntity> sessionConstraint) {
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setId(UUID.randomUUID());
		playerEntity.setName(name);
		playerEntity.setSurname(surname);
		playerEntity.setBirthday(LocalDate.of(LocalDate.now().getYear() - age, 1, 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		playerEntity.setCourses((long) weeklySession);
		playerEntity.setLevel((long) level);
		playerEntity.setEmail("");
		if (availability == null) availability = List.of();
		playerEntity.setDisponibilities(availability.stream().map(DisponibilityEntityConverter::from).toList());
		return playerEntity;
	}
}
