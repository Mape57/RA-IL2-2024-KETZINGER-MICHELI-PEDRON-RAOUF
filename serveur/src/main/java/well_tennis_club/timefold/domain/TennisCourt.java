package well_tennis_club.timefold.domain;

import lombok.Getter;
import well_tennis_club.data_structure.Timeslot;

import java.util.List;

@Getter
public class TennisCourt {
	private String name;
	private List<Timeslot> openingHours;

	public TennisCourt(String name, List<Timeslot> openingHours) {
		this.name = name;
		this.openingHours = openingHours;
	}
}
