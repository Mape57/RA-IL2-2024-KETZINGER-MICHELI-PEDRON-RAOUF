package well_tennis_club.projet.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.dto.TimeDto;
import well_tennis_club.projet.entity.TimeEntity;

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
