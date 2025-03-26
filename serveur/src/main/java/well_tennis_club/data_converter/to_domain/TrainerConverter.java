package well_tennis_club.data_converter.to_domain;

import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.Trainer;

import java.util.List;
import java.util.UUID;

public class TrainerConverter {
	public static Trainer from(TrainerEntity trainerEntity) {
		UUID id = trainerEntity.getId();

		int ageMin = trainerEntity.getInfAge();
		int ageMax = trainerEntity.getSupAge();
		ValueRange agePreference = new ValueRange(ageMin, ageMax);

		int levelMin = trainerEntity.getInfLevel();
		int levelMax = trainerEntity.getSupLevel();
		ValueRange levelPreference = new ValueRange(levelMin, levelMax);

		int weeklyMinutesMin = trainerEntity.getInfWeeklyMinutes();
		int weeklyMinutesMax = trainerEntity.getSupWeeklyMinutes();
		ValueRange weeklyMinutes = new ValueRange(weeklyMinutesMin, weeklyMinutesMax);

		List<Timeslot> availability = trainerEntity.getDisponibilities().stream().map(TimeslotConverter::from).toList();

		boolean isPartTime = trainerEntity.isPartTime();

		String name = trainerEntity.getName();
		String surname = trainerEntity.getSurname();

		return new Trainer(id, name, surname, agePreference, levelPreference, weeklyMinutes, availability, isPartTime);
	}
}
