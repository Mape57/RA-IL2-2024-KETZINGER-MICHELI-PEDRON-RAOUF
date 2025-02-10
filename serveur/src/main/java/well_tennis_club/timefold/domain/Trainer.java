package well_tennis_club.timefold.domain;

import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Trainer {
	private String name;
	private ValueRange agePreference;
	private ValueRange levelPreference;
	private ValueRange weeklyMinutes;
	private List<Timeslot> availability;
	private boolean isPartTime;

	public Trainer() {
	}

	public Trainer(String name, ValueRange agePreference, ValueRange levelPreference, ValueRange weeklyMinutes, List<Timeslot> availability, boolean isPartTime) {
		this.name = name;
		this.agePreference = agePreference;
		this.levelPreference = levelPreference;
		this.weeklyMinutes = weeklyMinutes;
		this.availability = availability;
		this.isPartTime = isPartTime;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Trainer trainer = (Trainer) o;
		if (availability == null ^ trainer.availability == null) return false;
		return isPartTime == trainer.isPartTime &&
				Objects.equals(name, trainer.name) &&
				Objects.equals(agePreference, trainer.agePreference) &&
				Objects.equals(levelPreference, trainer.levelPreference) &&
				Objects.equals(weeklyMinutes, trainer.weeklyMinutes) &&
				// availability
				((availability == null) ||
				(availability.size() == trainer.availability.size() &&
				availability.containsAll(trainer.availability)));
	}

	@Override
	public String toString() {
		return name + "{" +
				"agePreference=" + agePreference +
				", levelPreference=" + levelPreference +
				", availability=" + availability +
				", isPartTime=" + isPartTime +
				'}';
	}

	public boolean isAvailable(Session session, Integer duration) {
		LocalTime sessionStart = session.getStartTime();
		LocalTime sessionEnd = sessionStart.plusMinutes(duration);

		for (Timeslot timeslot : availability) {
			if (!Objects.equals(timeslot.day(), session.getDay())) continue;
			if (sessionStart.isBefore(timeslot.startTime()) || sessionEnd.isAfter(timeslot.endTime())) continue;
			return true;
		}
		return false;
	}
}