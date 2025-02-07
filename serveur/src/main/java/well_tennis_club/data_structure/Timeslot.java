package well_tennis_club.data_structure;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Représente un créneau horaire
 *
 * @param day       Jour de la semaine
 * @param startTime Heure de début
 * @param endTime   Heure de fin
 */
public record Timeslot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
	@Override
	public String toString() {
		return day + ":" + startTime + "-" + endTime;
	}
}
