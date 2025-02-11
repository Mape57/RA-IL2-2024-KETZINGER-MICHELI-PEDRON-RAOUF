package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.disponibility.DisponibilityEntity;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DisponibilityEntityConverter {
	public static DisponibilityEntity from(Timeslot timeslot) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		DisponibilityEntity disponibility = new DisponibilityEntity();
		disponibility.setId(UUID.randomUUID());
		disponibility.setDayWeek(timeslot.day().getValue());
		disponibility.setOpen(timeslot.startTime().format(formatter));
		disponibility.setClose(timeslot.endTime().format(formatter));

		return disponibility;
	}
}
