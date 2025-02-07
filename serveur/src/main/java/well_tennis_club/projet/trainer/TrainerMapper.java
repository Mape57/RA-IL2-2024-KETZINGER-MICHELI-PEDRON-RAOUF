package well_tennis_club.projet.trainer;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.disponibility.DisponibilityMapper;

import java.util.List;

@Mapper(uses = DisponibilityMapper.class)
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);
    @Mapping(target = "disponibilities", source = "disponibitities")
    TrainerDto mapToDTO(TrainerEntity entity);

    @InheritInverseConfiguration
    TrainerEntity mapToEntity(TrainerDto dto);

    List<TrainerDto> mapToListDTO(List<TrainerEntity> entities);

    List<TrainerEntity> mapToListEntity(List<TrainerDto> dtos);
}
