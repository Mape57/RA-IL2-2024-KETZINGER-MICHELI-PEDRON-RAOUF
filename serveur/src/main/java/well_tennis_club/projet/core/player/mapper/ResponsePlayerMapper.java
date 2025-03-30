package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.player.dto.ResponsePlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DisponibilityMapper.class, ResponsePlayerMappingHelper.class})
public interface ResponsePlayerMapper {
	@Mapping(target = "disponibilities", source = "disponibilities")
	@Mapping(target = "numberOfSessions", source = "entity", qualifiedByName = "getNumberOfParticipations")
	ResponsePlayerDto mapToDTO(PlayerEntity entity);

	List<ResponsePlayerDto> mapToListDTO(List<PlayerEntity> entities);
}
