package well_tennis_club.timefold.data_structure;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Représente un créneau horaire
 *
 * @param day       Jour de la semaine
 * @param startTime Heure de début
 * @param endTime   Heure de fin
 */
public record Timeslot(DayOfWeek day, LocalTime startTime, LocalTime endTime) implements Serializable {
	@Override
	public String toString() {
		return day + ":" + startTime + "-" + endTime;
	}

	public boolean contains(LocalTime time) {
		return startTime.isBefore(time) && time.isBefore(endTime);
	}

	public Duration duration() {
		return Duration.between(startTime, endTime);
	}
}
