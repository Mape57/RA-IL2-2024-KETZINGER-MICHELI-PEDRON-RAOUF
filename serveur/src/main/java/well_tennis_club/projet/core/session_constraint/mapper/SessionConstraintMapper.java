package well_tennis_club.projet.core.session_constraint.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.session_constraint.dto.SessionConstraintDto;

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
