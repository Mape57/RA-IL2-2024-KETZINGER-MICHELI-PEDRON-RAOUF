package well_tennis_club.projet.core.session.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.court.CourtMapper;
import well_tennis_club.projet.core.player.mapper.PlayerMapper;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.CreateSessionDto;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;

import java.util.List;

@Mapper(uses = {CourtMapper.class, TrainerMapper.class, PlayerMapper.class})
public interface CreateSessionMapper {
	CreateSessionMapper INSTANCE = Mappers.getMapper(CreateSessionMapper.class);

	@Mapping(target = "idTrainer", source = "idTrainer")
	@Mapping(target = "idCourt", source = "idCourt")
	@Mapping(target = "players", source = "players")
	CreateSessionDto mapToDTO(SessionEntity entity);

	@InheritInverseConfiguration
	SessionEntity mapToEntity(CreateSessionDto dto);

	List<CreateSessionDto> mapToListDTO(List<SessionEntity> entities);

	List<SessionEntity> mapToListEntity(List<CreateSessionDto> dtos);
}
