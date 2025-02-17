package well_tennis_club.data_converter.to_domain;

import well_tennis_club.projet.sessionConstraint.SessionConstraintEntity;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.ValueRange;

public class SessionConstraintConverter {
	public static SessionConstraint from(SessionConstraintEntity sessionConstraintEntity) {
		int infAge = sessionConstraintEntity.getInfAge();
		int supAge = sessionConstraintEntity.getSupAge();
		ValueRange ages = new ValueRange(infAge, supAge);

		int infLevel = sessionConstraintEntity.getInfLevel();
		int supLevel = sessionConstraintEntity.getSupLevel();
		ValueRange levels = new ValueRange(infLevel, supLevel);

		int infGroup = sessionConstraintEntity.getInfGroup();
		int supGroup = sessionConstraintEntity.getSupGroup();
		ValueRange groups = new ValueRange(infGroup, supGroup);

		int ageDiff = sessionConstraintEntity.getAgeDiff();
		int levelDiff = sessionConstraintEntity.getLevelDiff();
		int duration = sessionConstraintEntity.getDuration();

		return new SessionConstraint(ages, levels, groups, ageDiff, levelDiff, duration);
	}
}
