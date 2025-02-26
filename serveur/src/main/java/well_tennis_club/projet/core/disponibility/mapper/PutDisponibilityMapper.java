package well_tennis_club.projet.core.disponibility.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.dto.PutDisponibilityDto;
import well_tennis_club.projet.core.disponibility.entity.DisponibilityEntity;

import java.util.List;

@Mapper
public interface PutDisponibilityMapper {
	PutDisponibilityMapper INSTANCE = Mappers.getMapper(PutDisponibilityMapper.class);

	PutDisponibilityDto mapToDTO(DisponibilityEntity entity);

	@InheritInverseConfiguration
	DisponibilityEntity mapToEntity(PutDisponibilityDto dto);

	List<PutDisponibilityDto> mapToListDTO(List<DisponibilityEntity> entities);

	List<DisponibilityEntity> mapToListEntity(List<PutDisponibilityDto> dtos);
}
