package well_tennis_club.data_converter.to_domain;

import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.domain.TennisCourt;

import java.util.List;
import java.util.UUID;

public class TennisCourtConverter {
	public static TennisCourt from(CourtEntity courtEntity) {
		UUID id = courtEntity.getId();
		String name = courtEntity.getName();
		List<Timeslot> openingHours = courtEntity.getTimes().stream().map(TimeslotConverter::from).toList();
		return new TennisCourt(id, name, openingHours);
	}
}
