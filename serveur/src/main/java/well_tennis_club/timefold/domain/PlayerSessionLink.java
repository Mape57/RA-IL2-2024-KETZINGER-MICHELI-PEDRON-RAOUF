package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import lombok.Getter;

@PlanningEntity
@Getter
public class PlayerSessionLink {
	private Player player;
	@PlanningVariable
	private Session session;

	public PlayerSessionLink() {
	}

	public PlayerSessionLink(Player player) {
		this.player = player;
	}

	public PlayerSessionLink(Player player, Session session) {
		this.player = player;
		this.session = session;
	}
}