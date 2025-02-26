package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.player.dto.PlayerInscriptionDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface PlayerInscriptionMapper {
	PlayerInscriptionMapper INSTANCE = Mappers.getMapper(PlayerInscriptionMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	PlayerInscriptionDto mapToDTO(PlayerEntity entity);

	@InheritInverseConfiguration
	@Mapping(target = "validate", constant = "false")
	PlayerEntity mapToEntity(PlayerInscriptionDto dto);

	List<PlayerInscriptionDto> mapToListDTO(List<PlayerEntity> entities);

	List<PlayerEntity> mapToListEntity(List<PlayerInscriptionDto> dtos);
}
