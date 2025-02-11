package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.util.List;
import java.util.UUID;

public class CourtEntityConverter {
	public static CourtEntity from(String name, List<Timeslot> openingHours) {
		CourtEntity courtEntity = new CourtEntity();
		courtEntity.setId(UUID.randomUUID());
		courtEntity.setName(name);
		courtEntity.setTimes(openingHours.stream().map(TimeEntityConverter::from).toList());

		return courtEntity;
	}
}
