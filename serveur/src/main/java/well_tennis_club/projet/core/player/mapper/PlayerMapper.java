package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.player.dto.PlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface PlayerMapper {
	PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	PlayerDto mapToDTO(PlayerEntity entity);

	@InheritInverseConfiguration
	PlayerEntity mapToEntity(PlayerDto dto);

	List<PlayerDto> mapToListDTO(List<PlayerEntity> entities);

	List<PlayerEntity> mapToListEntity(List<PlayerDto> dtos);
}
