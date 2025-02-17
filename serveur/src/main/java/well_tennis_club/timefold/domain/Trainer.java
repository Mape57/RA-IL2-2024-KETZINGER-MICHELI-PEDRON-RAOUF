package well_tennis_club.timefold.domain;

import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Trainer {
	private UUID id;
	private ValueRange agePreference;
	private ValueRange levelPreference;
	private ValueRange weeklyMinutes;
	private List<Timeslot> availability;
	private boolean isPartTime;

	public Trainer() {
	}

	public Trainer(UUID id, ValueRange agePreference, ValueRange levelPreference, ValueRange weeklyMinutes, List<Timeslot> availability, boolean isPartTime) {
		this.id = id;
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
		return Objects.equals(id, trainer.id);
	}

	@Override
	public String toString() {
		return id.toString() + "{" +
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