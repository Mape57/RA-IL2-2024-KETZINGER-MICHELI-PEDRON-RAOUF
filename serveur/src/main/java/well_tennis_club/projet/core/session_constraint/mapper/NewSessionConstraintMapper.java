package well_tennis_club.projet.core.session_constraint.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.session_constraint.dto.NewSessionConstraintDto;

import java.util.List;

@Mapper
public interface NewSessionConstraintMapper {
	NewSessionConstraintMapper INSTANCE = Mappers.getMapper(NewSessionConstraintMapper.class);

	NewSessionConstraintDto mapToDTO(SessionConstraintEntity entity);

	@InheritInverseConfiguration
	SessionConstraintEntity mapToEntity(NewSessionConstraintDto dto);

	List<NewSessionConstraintDto> mapToListDTO(List<SessionConstraintEntity> entities);

	List<SessionConstraintEntity> mapToListEntity(List<NewSessionConstraintDto> dtos);
}
