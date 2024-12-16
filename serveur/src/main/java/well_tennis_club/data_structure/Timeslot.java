package well_tennis_club.data_structure;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record Timeslot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
	public int durationInMinutes() {
		return (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
	}

	@Override
	public String toString() {
		return day + ":" + startTime + "-" + endTime;
	}
}
