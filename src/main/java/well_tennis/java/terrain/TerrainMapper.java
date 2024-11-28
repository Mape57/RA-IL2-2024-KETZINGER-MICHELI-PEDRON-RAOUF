package well_tennis.java.terrain;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis.java.horaire.HoraireMapper;
import well_tennis.java.seance.SeanceMapper;

import java.util.List;

@Mapper(uses = {HoraireMapper.class, SeanceMapper.class})
public interface TerrainMapper {
    TerrainMapper INSTANCE = Mappers.getMapper(TerrainMapper.class);

    @Mapping(source = "horaires", target = "horaires")
    @Mapping(source = "seances", target = "seances")
    TerrainDto mapToDTO(TerrainEntity entity);

    @InheritInverseConfiguration
    TerrainEntity mapToEntity(TerrainDto dto);

    List<TerrainDto> mapToListDTO(List<TerrainEntity> entities);
    List<TerrainEntity> mapToListEntity(List<TerrainDto> dtos);
}