package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.PutDisponibilityMapper;
import well_tennis_club.projet.core.player.dto.PutPlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(uses = PutDisponibilityMapper.class)
public interface PutPlayerMapper {
	PutPlayerMapper INSTANCE = Mappers.getMapper(PutPlayerMapper.class);

	PutPlayerDto mapToDTO(PlayerEntity entity);

	@InheritInverseConfiguration
	PlayerEntity mapToEntity(PutPlayerDto dto);

	List<PutPlayerDto> mapToListDTO(List<PlayerEntity> entities);

	List<PlayerEntity> mapToListEntity(List<PutPlayerDto> dtos);
}
