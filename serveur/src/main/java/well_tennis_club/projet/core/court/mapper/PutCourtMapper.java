package well_tennis_club.projet.core.court.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.court.CourtEntity;
import well_tennis_club.projet.core.court.dto.PutCourtDto;
import well_tennis_club.projet.core.time.mapper.NewTimeMapper;

import java.util.List;

@Mapper(uses = NewTimeMapper.class)
public interface PutCourtMapper {
	PutCourtMapper INSTANCE = Mappers.getMapper(PutCourtMapper.class);

	@Mapping(target = "times", source = "times")
	PutCourtDto mapToDTO(CourtEntity entity);

	@InheritInverseConfiguration
	CourtEntity mapToEntity(PutCourtDto dto);

	List<PutCourtDto> mapToListDTO(List<CourtEntity> entities);

	List<CourtEntity> mapToListEntity(List<PutCourtDto> dtos);
}
