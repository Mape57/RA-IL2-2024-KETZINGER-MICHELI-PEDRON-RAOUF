package well_tennis.java.horaire;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HoraireMapper {
    HoraireMapper INSTANCE = Mappers.getMapper(HoraireMapper.class);

    HoraireDto mapToDTO(HoraireEntity entity);

    HoraireEntity mapToEntity(HoraireDto dto);
}