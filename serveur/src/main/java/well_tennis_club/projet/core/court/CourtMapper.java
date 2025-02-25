package well_tennis_club.projet.core.court;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.time.TimeMapper;

import java.util.List;

@Mapper(uses = TimeMapper.class)
public interface CourtMapper {
	CourtMapper INSTANCE = Mappers.getMapper(CourtMapper.class);

	@Mapping(target = "times", source = "times")
	CourtDto mapToDTO(CourtEntity entity);

	@InheritInverseConfiguration
	CourtEntity mapToEntity(CourtDto dto);

	List<CourtDto> mapToListDTO(List<CourtEntity> entities);

	List<CourtEntity> mapToListEntity(List<CourtDto> dtos);
}
