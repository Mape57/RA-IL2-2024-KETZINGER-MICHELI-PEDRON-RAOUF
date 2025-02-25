package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.core.time.TimeEntity;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TimeEntityConverter {
	public static TimeEntity from(Timeslot timeslot) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		TimeEntity timeEntity = new TimeEntity();
		timeEntity.setId(UUID.randomUUID());
		timeEntity.setDayWeek(timeslot.day().getValue());
		timeEntity.setStart(timeslot.startTime().format(formatter));
		timeEntity.setStop(timeslot.endTime().format(formatter));
		return timeEntity;
	}
}
