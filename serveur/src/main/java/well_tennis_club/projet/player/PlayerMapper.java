package well_tennis_club.projet.player;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    PlayerDto mapToDTO(PlayerEntity entity);

    @InheritInverseConfiguration
    PlayerEntity mapToEntity(PlayerDto dto);

    List<PlayerDto> mapToListDTO(List<PlayerEntity> entities);

    List<PlayerEntity> mapToListEntity(List<PlayerDto> dtos);
}
