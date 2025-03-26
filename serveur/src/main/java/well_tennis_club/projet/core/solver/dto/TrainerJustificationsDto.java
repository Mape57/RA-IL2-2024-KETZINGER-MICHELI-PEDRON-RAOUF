package well_tennis_club.projet.core.solver.dto;

import lombok.Getter;
import lombok.Setter;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.MinimizedSessionDto;
import well_tennis_club.projet.core.session.mapper.MinimizedSessionMapper;
import well_tennis_club.projet.core.trainer.dto.MinimizedTrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.mapper.MinimizedTrainerMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainerJustificationsDto extends JustificationsDto {
	private MinimizedTrainerDto trainer;
	private List<MinimizedSessionDto> unavailabilities = new ArrayList<>();
	private List<MinimizedSessionDto> overlappingSessions = new ArrayList<>();

	public TrainerJustificationsDto(TrainerEntity trainer) {
		super(Type.TRAINER);
		this.trainer = MinimizedTrainerMapper.INSTANCE.mapToDTO(trainer);
	}

	public void addUnavailability(SessionEntity session) {
		MinimizedSessionDto msd = MinimizedSessionMapper.INSTANCE.mapToDTO(session);
		if (unavailabilities.contains(msd)) return;
		unavailabilities.add(msd);
	}

	public void addOverlappingSession(SessionEntity session) {
		MinimizedSessionDto msd = MinimizedSessionMapper.INSTANCE.mapToDTO(session);
		if (overlappingSessions.contains(msd)) return;
		overlappingSessions.add(msd);
	}
}
