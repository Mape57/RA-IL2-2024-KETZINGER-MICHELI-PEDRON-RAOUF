package well_tennis_club.projet.participation;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ParticipationMapper {
    ParticipationMapper INSTANCE = Mappers.getMapper(ParticipationMapper.class);
    @Mapping(target = "idPlayer", source = "participationKey.idPlayer")
    @Mapping(target = "idSession",source = "participationKey.idSession")
    ParticipationDto mapToDTO(ParticipationEntity entity);
    @InheritInverseConfiguration
    ParticipationEntity mapToEntity(ParticipationDto dto);
    List<ParticipationDto> mapToListDTO(List<ParticipationEntity> entities);
    List<ParticipationEntity> mapToListEntity(List<ParticipationDto> dtos);
}
