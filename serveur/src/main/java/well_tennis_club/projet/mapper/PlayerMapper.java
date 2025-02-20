package well_tennis_club.projet.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.dto.PlayerDto;
import well_tennis_club.projet.entity.PlayerEntity;

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
