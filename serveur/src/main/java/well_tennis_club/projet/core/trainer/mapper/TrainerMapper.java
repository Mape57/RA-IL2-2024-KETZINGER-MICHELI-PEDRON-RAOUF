package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.DisponibilityMapper;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface TrainerMapper {
	TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	TrainerDto mapToDTO(TrainerEntity entity);

	@InheritInverseConfiguration
	TrainerEntity mapToEntity(TrainerDto dto);

	List<TrainerDto> mapToListDTO(List<TrainerEntity> entities);

	List<TrainerEntity> mapToListEntity(List<TrainerDto> dtos);
}
