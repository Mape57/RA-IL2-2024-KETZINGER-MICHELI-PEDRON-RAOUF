package well_tennis_club.projet.core.trainer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.mapper.CreateDisponibilityMapper;
import well_tennis_club.projet.core.trainer.dto.CreateTrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.List;
import java.util.UUID;

@Mapper(uses = CreateDisponibilityMapper.class)
public interface CreateTrainerMapper {
	CreateTrainerMapper INSTANCE = Mappers.getMapper(CreateTrainerMapper.class);

	@Mapping(target = "disponibilities", source = "disponibilities")
	CreateTrainerDto mapToDTO(TrainerEntity entity);

	@InheritInverseConfiguration
	TrainerEntity mapToEntity(CreateTrainerDto dto);

	List<CreateTrainerDto> mapToListDTO(List<TrainerEntity> entities);

	List<TrainerEntity> mapToListEntity(List<CreateTrainerDto> dtos);
}
