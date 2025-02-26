package well_tennis_club.projet.core.time.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.time.TimeEntity;
import well_tennis_club.projet.core.time.dto.NewTimeDto;
import well_tennis_club.projet.core.time.dto.TimeDto;

import java.util.List;

@Mapper
public interface NewTimeMapper {
	NewTimeMapper INSTANCE = Mappers.getMapper(NewTimeMapper.class);

	NewTimeDto mapToDTO(TimeEntity entity);

	@InheritInverseConfiguration
	TimeEntity mapToEntity(NewTimeDto dto);

	List<NewTimeDto> mapToListDTO(List<TimeEntity> entities);

	List<TimeEntity> mapToListEntity(List<NewTimeDto> dtos);
}