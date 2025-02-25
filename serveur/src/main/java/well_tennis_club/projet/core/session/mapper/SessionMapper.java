package well_tennis_club.projet.core.session.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.court.CourtMapper;
import well_tennis_club.projet.core.player.mapper.PlayerMapper;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;

import java.util.List;

@Mapper(uses = {CourtMapper.class, TrainerMapper.class, PlayerMapper.class})
public interface SessionMapper {
	SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

	@Mapping(target = "idTrainer", source = "idTrainer")
	@Mapping(target = "idCourt", source = "idCourt")
	@Mapping(target = "players", source = "players")
	SessionDto mapToDTO(SessionEntity entity);

	@InheritInverseConfiguration
	SessionEntity mapToEntity(SessionDto dto);

	List<SessionDto> mapToListDTO(List<SessionEntity> entities);

	List<SessionEntity> mapToListEntity(List<SessionDto> dtos);
}
