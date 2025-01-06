package well_tennis_club.projet.timeCourt;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TimeCourtMapper {
    TimeCourtMapper INSTANCE = Mappers.getMapper(TimeCourtMapper.class);

    @Mapping(target = "idTime", source = "timeCourtKey.idTime")
    @Mapping(target = "idCourt",source = "timeCourtKey.idCourt")
    TimeCourtDto mapToDTO(TimeCourtEntity entity);
    @InheritInverseConfiguration
    TimeCourtEntity mapToEntity(TimeCourtDto dto);
    List<TimeCourtDto> mapToListDTO(List<TimeCourtEntity> entities);
    List<TimeCourtEntity> mapToListEntity(List<TimeCourtDto> dtos);
}
