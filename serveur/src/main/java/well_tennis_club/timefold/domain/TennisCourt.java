package well_tennis_club.timefold.domain;

import lombok.Getter;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.util.List;
import java.util.Objects;

@Getter
public class TennisCourt {
	private String name;
	private List<Timeslot> openingHours;

	public TennisCourt(String name, List<Timeslot> openingHours) {
		this.name = name;
		this.openingHours = openingHours;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		TennisCourt tennisCourt = (TennisCourt) o;
		if (openingHours == null ^ tennisCourt.openingHours == null) return false;
		return Objects.equals(name, tennisCourt.name) &&
				((openingHours == null) ||
				(openingHours.size() == tennisCourt.openingHours.size() &&
				openingHours.containsAll(tennisCourt.openingHours)));
	}
}
