package well_tennis_club.projet.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.dto.CourtDto;
import well_tennis_club.projet.entity.CourtEntity;

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
