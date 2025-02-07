package well_tennis_club.projet.disponibilityPlayer;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DisponibilityPlayerMapper {
    DisponibilityPlayerMapper INSTANCE = Mappers.getMapper(DisponibilityPlayerMapper.class);
    @Mapping(target = "idPlayer", source = "disponibilityPlayerKey.idPlayer")
    @Mapping(target = "idDisponibility", source = "disponibilityPlayerKey.idDisponibility")
    DisponibilityPlayerDto mapToDTO(DisponibilityPlayerEntity entity);
    @InheritInverseConfiguration
    DisponibilityPlayerEntity mapToEntity(DisponibilityPlayerDto dto);
    List<DisponibilityPlayerDto> mapToListDTO(List<DisponibilityPlayerEntity> entities);
    List<DisponibilityPlayerEntity> mapToListEntity(List<DisponibilityPlayerDto> dtos);
}
