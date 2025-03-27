package well_tennis_club.projet.core.solver.dto;

import lombok.Getter;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.session.mapper.SessionMapper;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SessionJustificationsDto {
	private SessionDto session;
	private Map<String, String> sessionJustifications = new HashMap<>();

	public SessionJustificationsDto(SessionEntity session) {
		this.session = SessionMapper.INSTANCE.mapToDTO(session);
	}

	public void addJustification(String justification, String score) {
		sessionJustifications.put(justification, score);
	}
}
