package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.trainer.dto.MinimizedTrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface MinimizedTrainerMapper {
	MinimizedTrainerMapper INSTANCE = Mappers.getMapper(MinimizedTrainerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	MinimizedTrainerDto mapToDTO(TrainerEntity entity);

	List<MinimizedTrainerDto> mapToListDTO(List<TrainerEntity> entities);
}
