package well_tennis_club.projet.core.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import well_tennis_club.projet.core.player.dto.PlayerDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionPlayerDto {
    private PlayerDto player;
    private List<SessionDto> sessions;
}
