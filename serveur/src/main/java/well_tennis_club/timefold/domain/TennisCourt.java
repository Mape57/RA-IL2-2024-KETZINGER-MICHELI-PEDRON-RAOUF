package well_tennis_club.timefold.domain;

import lombok.Getter;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class TennisCourt implements Serializable {
	private UUID id;
	private String name;
	private List<Timeslot> openingHours;

	public TennisCourt(UUID id, String name, List<Timeslot> openingHours) {
		this.id = id;
		this.name = name;
		this.openingHours = openingHours;
	}

	public TennisCourt(String name, List<Timeslot> openingHours) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.openingHours = openingHours;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		TennisCourt tennisCourt = (TennisCourt) o;
		return Objects.equals(id, tennisCourt.id);
	}
}
