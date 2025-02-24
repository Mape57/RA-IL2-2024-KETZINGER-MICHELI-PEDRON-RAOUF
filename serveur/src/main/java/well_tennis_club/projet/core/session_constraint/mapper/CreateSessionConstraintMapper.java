package well_tennis_club.projet.core.session_constraint.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.session_constraint.dto.CreateSessionConstraintDto;

import java.util.List;

@Mapper
public interface CreateSessionConstraintMapper {
	CreateSessionConstraintMapper INSTANCE = Mappers.getMapper(CreateSessionConstraintMapper.class);

	CreateSessionConstraintDto mapToDTO(SessionConstraintEntity entity);

	@InheritInverseConfiguration
	SessionConstraintEntity mapToEntity(CreateSessionConstraintDto dto);

	List<CreateSessionConstraintDto> mapToListDTO(List<SessionConstraintEntity> entities);

	List<SessionConstraintEntity> mapToListEntity(List<CreateSessionConstraintDto> dtos);
}
