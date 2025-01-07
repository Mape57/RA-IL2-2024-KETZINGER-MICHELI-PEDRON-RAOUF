package well_tennis_club.timefold.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.*;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.solver.collectors.CustomCollectors;
import well_tennis_club.timefold.solver.justifications.AgeOverflowJustification;
import well_tennis_club.timefold.solver.justifications.GroupSizeDifferenceJustification;
import well_tennis_club.timefold.solver.justifications.LevelOverflowJustification;

public class TimetableConstraintProvider implements ConstraintProvider {
	@Override
	public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
		return new Constraint[]{
				groupSize(constraintFactory),
				ageDifference(constraintFactory),
				levelDifference(constraintFactory)
		};
	}

	Constraint ageDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.ageOverflow())
				.filter((x, ageOverflow) -> ageOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, ageOverflow) -> ageOverflow)
				.justifyWith((session, ageOverflow, x) -> new AgeOverflowJustification(session, ageOverflow))
				.asConstraint("La différence d'âge maximum par groupe est dépassée");
	}

	Constraint levelDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.levelOverflow())
				.filter((x, levelOverflow) -> levelOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, levelOverflow) -> levelOverflow)
				.justifyWith((session, levelOverflow, x) -> new LevelOverflowJustification(session, levelOverflow))
				.asConstraint("La différence de niveau maximum par groupe est dépassée");
	}

	Constraint groupSize(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.groupSizeDifference())
				.filter((x, groupSizeDifference) -> groupSizeDifference != 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, groupSizeDifference) -> Math.abs(groupSizeDifference))
				.justifyWith((session, groupSizeDifference, x) -> new GroupSizeDifferenceJustification(session, groupSizeDifference))
				.asConstraint("La taille du groupe n'est pas respectée");
	}
}
