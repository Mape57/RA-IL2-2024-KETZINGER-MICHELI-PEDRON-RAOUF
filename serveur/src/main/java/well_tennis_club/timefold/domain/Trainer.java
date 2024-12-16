package well_tennis_club.timefold.domain;

import lombok.Getter;
import well_tennis_club.data_structure.Timeslot;
import well_tennis_club.data_structure.ValueRange;

import java.util.List;

@Getter
public class Trainer {
	private String name;
	private ValueRange agePreference;
	private ValueRange levelPreference;
	private List<Timeslot> availability;
	private boolean isPartTime;

	public Trainer() {
	}

	public Trainer(String name, ValueRange agePreference, ValueRange levelPreference, List<Timeslot> availability, boolean isPartTime) {
		this.name = name;
		this.agePreference = agePreference;
		this.levelPreference = levelPreference;
		this.availability = availability;
		this.isPartTime = isPartTime;
	}
}