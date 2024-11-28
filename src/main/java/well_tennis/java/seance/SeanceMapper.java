package well_tennis.java.seance;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeanceMapper {
    SeanceMapper INSTANCE = Mappers.getMapper(SeanceMapper.class);

    SeanceDto mapToDTO(SeanceEntity entity);

    SeanceEntity mapToEntity(SeanceDto dto);
}