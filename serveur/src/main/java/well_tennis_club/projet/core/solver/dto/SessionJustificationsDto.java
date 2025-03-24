package well_tennis_club.projet.core.solver.dto;

import lombok.Getter;
import lombok.Setter;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.MinimizedSessionDto;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.session.mapper.MinimizedSessionMapper;
import well_tennis_club.projet.core.session.mapper.SessionMapper;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SessionJustificationsDto extends JustificationsDto {
	private SessionDto session;
	private Map<String, String> sessionJustifications = new HashMap<>();

	public SessionJustificationsDto(SessionEntity session) {
		super(Type.SESSION);
		this.session = SessionMapper.INSTANCE.mapToDTO(session);
	}

	public void addJustification(String justification, String score) {
		sessionJustifications.put(justification, score);
	}
}
