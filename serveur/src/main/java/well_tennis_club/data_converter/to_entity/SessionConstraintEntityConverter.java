package well_tennis_club.data_converter.to_entity;

import well_tennis_club.projet.sessionConstraint.SessionConstraintEntity;
import well_tennis_club.timefold.data_structure.SessionConstraint;

import java.util.UUID;

public class SessionConstraintEntityConverter {
	public static SessionConstraintEntity from(SessionConstraint sessionConstraint) {
		SessionConstraintEntity sessionConstraintEntity = new SessionConstraintEntity();
		sessionConstraintEntity.setId(UUID.randomUUID());

		sessionConstraintEntity.setInfAge(sessionConstraint.ages().getMin());
		sessionConstraintEntity.setSupAge(sessionConstraint.ages().getMax());

		sessionConstraintEntity.setInfLevel(sessionConstraint.levels().getMin());
		sessionConstraintEntity.setSupLevel(sessionConstraint.levels().getMax());

		sessionConstraintEntity.setInfGroup(sessionConstraint.groupSize().getMin());
		sessionConstraintEntity.setSupGroup(sessionConstraint.groupSize().getMax());

		sessionConstraintEntity.setAgeDiff(sessionConstraint.ageDifference());
		sessionConstraintEntity.setLevelDiff(sessionConstraint.levelDifference());
		sessionConstraintEntity.setDuration(sessionConstraint.duration());

		return sessionConstraintEntity;
	}
}
