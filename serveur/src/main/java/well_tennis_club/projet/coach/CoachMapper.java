package well_tennis_club.projet.coach;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.disponibility.DisponibilityMapper;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface CoachMapper {
    CoachMapper INSTANCE = Mappers.getMapper(CoachMapper.class);
    @Mapping(target = "disponibilities", source = "disponibitities")
    CoachDto mapToDTO(CoachEntity entity);

    @InheritInverseConfiguration
    CoachEntity mapToEntity(CoachDto dto);

    List<CoachDto> mapToListDTO(List<CoachEntity> entities);

    List<CoachEntity> mapToListEntity(List<CoachDto> dtos);
}
