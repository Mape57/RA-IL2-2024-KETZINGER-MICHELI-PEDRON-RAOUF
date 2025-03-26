package well_tennis_club.projet.core.solver.dto;

import lombok.Getter;
import well_tennis_club.projet.core.player.dto.MinimizedPlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;
import well_tennis_club.projet.core.player.mapper.MinimizedPlayerMapper;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.MinimizedSessionDto;
import well_tennis_club.projet.core.session.mapper.MinimizedSessionMapper;
import well_tennis_club.timefold.domain.Session;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerJustificationsDto extends JustificationsDto {
	private MinimizedPlayerDto player;
	private List<MinimizedSessionDto> unavailabilities = new ArrayList<>();
	private List<MinimizedSessionDto> tooManySessionsPerDay = new ArrayList<>();

	public PlayerJustificationsDto(PlayerEntity player) {
		super(Type.PLAYER);
		this.player = MinimizedPlayerMapper.INSTANCE.mapToDTO(player);
	}

	public void addUnavailability(SessionEntity session) {
		MinimizedSessionDto msd = MinimizedSessionMapper.INSTANCE.mapToDTO(session);
		unavailabilities.add(msd);
	}

	public void addSessionPerDay(SessionEntity session) {
		MinimizedSessionDto msd = MinimizedSessionMapper.INSTANCE.mapToDTO(session);
		if (tooManySessionsPerDay.contains(msd)) return;
		tooManySessionsPerDay.add(msd);
	}
}
