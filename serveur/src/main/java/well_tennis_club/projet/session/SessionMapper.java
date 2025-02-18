package well_tennis_club.projet.session;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import well_tennis_club.projet.trainer.TrainerMapper;
import well_tennis_club.projet.court.CourtMapper;
import well_tennis_club.projet.player.PlayerMapper;

import java.util.List;

@Mapper(uses = {CourtMapper.class, TrainerMapper.class, PlayerMapper.class})
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mapping(target = "idTrainer",source = "idTrainer")
    @Mapping(target = "idCourt",source = "idCourt")
    @Mapping(target = "players",source = "players")
    SessionDto mapToDTO(SessionEntity entity);
    @InheritInverseConfiguration
    SessionEntity mapToEntity(SessionDto dto);
    List<SessionDto> mapToListDTO(List<SessionEntity> entities);
    List<SessionEntity> mapToListEntity(List<SessionDto> dtos);
}
