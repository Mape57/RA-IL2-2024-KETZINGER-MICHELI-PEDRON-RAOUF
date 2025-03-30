package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.session.SessionService;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ResponseTrainerMappingHelper {
	private final SessionService sessionService;

	@Autowired
	public ResponseTrainerMappingHelper(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Named(value = "getWeeklyMinutes")
	public Long getWeeklyMinutes(TrainerEntity trainer) {
		return (long) sessionService.getSessionsByTrainer(trainer).stream()
				.map(s -> {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
					LocalTime start = LocalTime.parse(s.getStart(), formatter);
					LocalTime stop = LocalTime.parse(s.getStop(), formatter);
					return (stop.toSecondOfDay() - start.toSecondOfDay()) / 60;
				}).reduce(0, Integer::sum);
	}
}
