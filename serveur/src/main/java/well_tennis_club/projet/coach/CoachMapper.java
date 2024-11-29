package well_tennis_club.projet.coach;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoachMapper {
    CoachMapper INSTANCE = Mappers.getMapper(CoachMapper.class);
    CoachDto mapToDTO(CoachEntity entity);

    @InheritInverseConfiguration
    CoachEntity mapToEntity(CoachDto dto);

    List<CoachDto> mapToListDTO(List<CoachEntity> entities);

    List<CoachEntity> mapToListEntity(List<CoachDto> dtos);
}
