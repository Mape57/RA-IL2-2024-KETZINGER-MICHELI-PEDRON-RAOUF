package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;

import java.util.List;
import java.util.UUID;

public class TrainerEntityConverter {
	public static TrainerEntity from(String name, String surname, ValueRange agePreference, ValueRange levelPreference, ValueRange weeklyMinutes, List<Timeslot> availability, boolean partTime) {
		TrainerEntity playerEntity = new TrainerEntity();
		playerEntity.setId(UUID.randomUUID());
		playerEntity.setName(name);
		playerEntity.setSurname(surname);
		playerEntity.setInfLevel(levelPreference.getMin());
		playerEntity.setSupLevel(levelPreference.getMax());
		playerEntity.setInfAge(agePreference.getMin());
		playerEntity.setSupAge(agePreference.getMax());
		playerEntity.setInfWeeklyMinutes(weeklyMinutes.getMin());
		playerEntity.setSupWeeklyMinutes(weeklyMinutes.getMax());
		playerEntity.setEmail("");
		playerEntity.setPassword("");
		playerEntity.setPartTime(partTime);
		playerEntity.setAdmin(false);
		playerEntity.setDisponibilities(availability.stream().map(DisponibilityEntityConverter::from).toList());

		return playerEntity;
	}
}
