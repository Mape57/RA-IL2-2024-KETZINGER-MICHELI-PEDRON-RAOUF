package well_tennis_club.projet.core.disponibility.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.disponibility.dto.CreateDisponibilityDto;
import well_tennis_club.projet.core.disponibility.entity.DisponibilityEntity;

import java.util.List;

@Mapper
public interface CreateDisponibilityMapper {
	DisponibilityMapper INSTANCE = Mappers.getMapper(DisponibilityMapper.class);

	CreateDisponibilityDto mapToDTO(DisponibilityEntity entity);

	@InheritInverseConfiguration
	DisponibilityEntity mapToEntity(CreateDisponibilityDto dto);

	List<CreateDisponibilityDto> mapToListDTO(List<DisponibilityEntity> entities);

	List<DisponibilityEntity> mapToListEntity(List<CreateDisponibilityDto> dtos);
}
