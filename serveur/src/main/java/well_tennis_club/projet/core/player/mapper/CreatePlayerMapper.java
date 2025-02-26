package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.CreateDisponibilityMapper;
import well_tennis_club.projet.core.player.dto.CreatePlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(uses = CreateDisponibilityMapper.class)
public interface CreatePlayerMapper {
	CreatePlayerMapper INSTANCE = Mappers.getMapper(CreatePlayerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	CreatePlayerDto mapToDTO(PlayerEntity entity);

	@InheritInverseConfiguration
	PlayerEntity mapToEntity(CreatePlayerDto dto);

	List<CreatePlayerDto> mapToListDTO(List<PlayerEntity> entities);

	List<PlayerEntity> mapToListEntity(List<CreatePlayerDto> dtos);
}
