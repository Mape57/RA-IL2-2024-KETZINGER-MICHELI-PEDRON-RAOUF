package well_tennis_club.data_converter.to_domain;

import well_tennis_club.projet.disponibility.DisponibilityEntity;
import well_tennis_club.projet.time.TimeEntity;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeslotConverter {
	public static Timeslot from(DisponibilityEntity disponibilityEntity) {
		DayOfWeek day = DayOfWeek.of(disponibilityEntity.getDayWeek());
		LocalTime start = parseHour(disponibilityEntity.getOpen());
		LocalTime end = parseHour(disponibilityEntity.getClose());

		return new Timeslot(day, start, end);
	}

	public static Timeslot from(TimeEntity timeEntity) {
		DayOfWeek day = DayOfWeek.of(timeEntity.getDayWeek());
		LocalTime start = parseHour(timeEntity.getStart());
		LocalTime end = parseHour(timeEntity.getStop());

		return new Timeslot(day, start, end);
	}

	private static LocalTime parseHour(String hour) {
		String[] hourString = hour.split(":");
		int[] hour_split = new int[2];
		hour_split[0] = Integer.parseInt(hourString[0]);
		hour_split[1] = Integer.parseInt(hourString[1]);
		return LocalTime.of(hour_split[0], hour_split[1]);
	}
}
