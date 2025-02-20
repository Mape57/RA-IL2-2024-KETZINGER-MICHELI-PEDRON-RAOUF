package well_tennis_club.projet.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.dto.DisponibilityDto;
import well_tennis_club.projet.entity.DisponibilityEntity;

import java.util.List;

@Mapper
public interface DisponibilityMapper {
	DisponibilityMapper INSTANCE = Mappers.getMapper(DisponibilityMapper.class);

	DisponibilityDto mapToDTO(DisponibilityEntity entity);

	@InheritInverseConfiguration
	DisponibilityEntity mapToEntity(DisponibilityDto dto);

	List<DisponibilityDto> mapToListDTO(List<DisponibilityEntity> entities);

	List<DisponibilityEntity> mapToListEntity(List<DisponibilityDto> dtos);
}
