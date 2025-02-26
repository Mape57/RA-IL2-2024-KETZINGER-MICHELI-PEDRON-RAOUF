package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.PutDisponibilityMapper;
import well_tennis_club.projet.core.trainer.dto.PutTrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;

@Mapper(uses = PutDisponibilityMapper.class)
public interface PutTrainerMapper {
	PutTrainerMapper INSTANCE = Mappers.getMapper(PutTrainerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	PutTrainerDto mapToDTO(TrainerEntity entity);

	@InheritInverseConfiguration
	TrainerEntity mapToEntity(PutTrainerDto dto);

	List<PutTrainerDto> mapToListDTO(List<TrainerEntity> entities);

	List<TrainerEntity> mapToListEntity(List<PutTrainerDto> dtos);
}
