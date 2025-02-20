package well_tennis_club.projet.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.service.TimetableService;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.TennisCourt;
import well_tennis_club.timefold.domain.Timetable;
import well_tennis_club.timefold.domain.Trainer;

import java.util.List;

@Component
public class TimetableFactory {
	private final TimetableService timetableService;

	@Autowired
	public TimetableFactory(TimetableService timetableService) {
		this.timetableService = timetableService;
	}

	public Timetable createTimetable() {
		List<SessionConstraint> sessionConstraints = timetableService.getAllSessionConstraints();
		List<Player> players = timetableService.getAllPlayers();
		players.forEach(player -> setSessionConstraint(player, sessionConstraints));

		List<Trainer> trainers = timetableService.getAllTrainers();
		List<TennisCourt> tennisCourts = timetableService.getAllTennisCourts();
		return new Timetable(players, trainers, tennisCourts);
	}

	private void setSessionConstraint(Player player, List<SessionConstraint> sessionConstraints) {
		for (SessionConstraint sessionConstraint : sessionConstraints) {
			if (player.complyWith(sessionConstraint)) {
				player.setSessionConstraint(sessionConstraint);
				return;
			}
		}
	}
}
