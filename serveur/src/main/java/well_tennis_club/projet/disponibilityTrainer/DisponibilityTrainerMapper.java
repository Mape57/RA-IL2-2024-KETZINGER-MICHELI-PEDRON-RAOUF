package well_tennis_club.projet.disponibilityTrainer;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DisponibilityTrainerMapper {
    DisponibilityTrainerMapper INSTANCE = Mappers.getMapper(DisponibilityTrainerMapper.class);

    @Mapping(target = "idTrainer",source = "disponibilityTrainerKey.idTrainer")
    @Mapping(target = "idDisponibility",source = "disponibilityTrainerKey.idDisponibility")
    DisponibilityTrainerDto mapToDTO(DisponibilityTrainerEntity entity);

    @InheritInverseConfiguration
    DisponibilityTrainerEntity mapToEntity(DisponibilityTrainerDto dto);
    List<DisponibilityTrainerDto> mapToListDTO(List<DisponibilityTrainerEntity> entities);
    List<DisponibilityTrainerEntity> mapToListEntity(List<DisponibilityTrainerDto> dtos);
}
