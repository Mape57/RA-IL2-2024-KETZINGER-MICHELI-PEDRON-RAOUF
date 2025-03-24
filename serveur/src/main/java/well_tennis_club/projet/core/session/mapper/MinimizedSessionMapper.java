package well_tennis_club.projet.core.session.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.core.session.SessionEntity;
import well_tennis_club.projet.core.session.dto.MinimizedSessionDto;

import java.util.List;

@Mapper
public interface MinimizedSessionMapper {
	MinimizedSessionMapper INSTANCE = Mappers.getMapper(MinimizedSessionMapper.class);

	@Mapping(target = "court", source = "idCourt.name")
	MinimizedSessionDto mapToDTO(SessionEntity entity);

	List<MinimizedSessionDto> mapToListDTO(List<SessionEntity> entities);
}
