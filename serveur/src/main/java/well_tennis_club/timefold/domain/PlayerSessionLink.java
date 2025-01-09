package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.tools.difficulty_comparator.PSLDifficultyComparator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@PlanningEntity(difficultyComparatorClass = PSLDifficultyComparator.class)
public class PlayerSessionLink {
	@PlanningId
	private UUID id;

	@JsonIdentityReference
	private Player player;
	@PlanningVariable
	@JsonIdentityReference
	private Session session;

	public PlayerSessionLink() {
		this.id = UUID.randomUUID();
	}

	public PlayerSessionLink(Player player) {
		this.id = UUID.randomUUID();
		this.player = player;
	}

	public PlayerSessionLink(Player player, Session session) {
		this.id = UUID.randomUUID();
		this.player = player;
		this.session = session;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PlayerSessionLink psl = (PlayerSessionLink) o;
		return Objects.equals(player, psl.player) && Objects.equals(session, psl.session);
	}

	@Override
	public String toString() {
		String res = "{";
		if (player != null) res += player.getName();
		res += " - ";
		if (session != null) res += ", " + session.getTennisCourt() + ": " + session.getDay() + "-" + session.getStartTime();
		return res + "}";
	}
}