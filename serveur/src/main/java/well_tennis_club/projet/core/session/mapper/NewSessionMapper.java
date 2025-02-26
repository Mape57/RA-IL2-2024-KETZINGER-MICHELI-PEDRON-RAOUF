package well_tennis_club.projet.core.session.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.NewSessionDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SessionMappingHelper.class})
public interface NewSessionMapper {
	@Mapping(target = "idTrainer", source = "idTrainer.id")
	@Mapping(target = "idCourt", source = "idCourt.id")
	@Mapping(target = "playerIds", source = "players", qualifiedByName = "playersToIds")
	NewSessionDto mapToDTO(SessionEntity entity);

	@Mapping(target = "idTrainer", source = "idTrainer", qualifiedByName = "trainerFromId")
	@Mapping(target = "idCourt", source = "idCourt", qualifiedByName = "courtFromId")
	@Mapping(target = "players", source = "playerIds", qualifiedByName = "playersFromIds")
	SessionEntity mapToEntity(NewSessionDto dto);

	List<NewSessionDto> mapToListDTO(List<SessionEntity> entities);

	List<SessionEntity> mapToListEntity(List<NewSessionDto> dtos);
}
