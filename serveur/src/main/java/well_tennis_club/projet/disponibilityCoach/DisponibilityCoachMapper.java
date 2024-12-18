package well_tennis_club.projet.disponibilityCoach;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DisponibilityCoachMapper {
    DisponibilityCoachMapper INSTANCE = Mappers.getMapper(DisponibilityCoachMapper.class);

    @Mapping(target = "idCoach",source = "disponibilityCoachKey.idCoach")
    @Mapping(target = "idDisponibility",source = "disponibilityCoachKey.idDisponibility")
    DisponibilityCoachDto mapToDTO(DisponibilityCoachEntity entity);

    @InheritInverseConfiguration
    DisponibilityCoachEntity mapToEntity(DisponibilityCoachDto dto);
    List<DisponibilityCoachDto> mapToListDTO(List<DisponibilityCoachEntity> entities);
    List<DisponibilityCoachEntity> mapToListEntity(List<DisponibilityCoachDto> dtos);
}
