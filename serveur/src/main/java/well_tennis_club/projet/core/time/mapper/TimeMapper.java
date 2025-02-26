package well_tennis_club.projet.core.time.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.time.TimeEntity;
import well_tennis_club.projet.core.time.dto.TimeDto;

import java.util.List;

@Mapper
public interface TimeMapper {
	TimeMapper INSTANCE = Mappers.getMapper(TimeMapper.class);

	TimeDto mapToDTO(TimeEntity entity);

	@InheritInverseConfiguration
	TimeEntity mapToEntity(TimeDto dto);

	List<TimeDto> mapToListDTO(List<TimeEntity> entities);

	List<TimeEntity> mapToListEntity(List<TimeDto> dtos);
}
