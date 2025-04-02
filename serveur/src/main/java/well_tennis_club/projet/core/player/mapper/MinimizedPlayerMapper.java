package well_tennis_club.projet.core.player.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.player.dto.MinimizedPlayerDto;
import well_tennis_club.projet.core.player.entity.PlayerEntity;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface MinimizedPlayerMapper {
	MinimizedPlayerMapper INSTANCE = Mappers.getMapper(MinimizedPlayerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	MinimizedPlayerDto mapToDTO(PlayerEntity entity);

	List<MinimizedPlayerDto> mapToListDTO(List<PlayerEntity> entities);
}
