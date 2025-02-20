package well_tennis_club.projet.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.dto.SessionConstraintDto;
import well_tennis_club.projet.entity.SessionConstraintEntity;

import java.util.List;

@Mapper
public interface SessionConstraintMapper {
	SessionConstraintMapper INSTANCE = Mappers.getMapper(SessionConstraintMapper.class);

	SessionConstraintDto mapToDTO(SessionConstraintEntity entity);

	@InheritInverseConfiguration
	SessionConstraintEntity mapToEntity(SessionConstraintDto dto);

	List<SessionConstraintDto> mapToListDTO(List<SessionConstraintEntity> entities);

	List<SessionConstraintEntity> mapToListEntity(List<SessionConstraintDto> dtos);
}
