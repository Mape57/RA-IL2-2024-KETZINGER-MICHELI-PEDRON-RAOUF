package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.session.SessionService;

@Component
public class ResponsePlayerMappingHelper {
	private final SessionService sessionService;

	@Autowired
	public ResponsePlayerMappingHelper(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Named(value = "getNumberOfParticipations")
	public Long getNumberOfParticipations(PlayerEntity playerEntity) {
		return (long) sessionService.getSessionsContaining(playerEntity).size();
	}
}
