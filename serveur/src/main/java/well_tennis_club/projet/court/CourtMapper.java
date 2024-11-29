package well_tennis_club.projet.court;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourtMapper {
    CourtMapper INSTANCE = Mappers.getMapper(CourtMapper.class);

    CourtDto mapToDTO(CourtEntity entity);

    @InheritInverseConfiguration
    CourtEntity mapToEntity(CourtDto dto);

    List<CourtDto> mapToListDTO(List<CourtEntity> entities);

    List<CourtEntity> mapToListEntity(List<CourtDto> dtos);
}
