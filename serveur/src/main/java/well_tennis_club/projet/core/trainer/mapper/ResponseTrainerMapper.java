package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.trainer.dto.ResponseTrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DisponibilityMapper.class, ResponseTrainerMappingHelper.class})
public interface ResponseTrainerMapper {
	@Mapping(target = "disponibilities", source = "disponibilities")
	@Mapping(target = "weeklyMinutes", source = "entity", qualifiedByName = "getWeeklyMinutes")
	ResponseTrainerDto mapToDTO(TrainerEntity entity);

	List<ResponseTrainerDto> mapToListDTO(List<TrainerEntity> entities);
}
