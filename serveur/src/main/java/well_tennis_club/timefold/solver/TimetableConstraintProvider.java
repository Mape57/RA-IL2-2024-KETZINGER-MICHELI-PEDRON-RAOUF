package well_tennis_club.timefold.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.solver.collectors.CustomCollectors;
import well_tennis_club.timefold.solver.justifications.*;

import java.util.function.Function;

import static well_tennis_club.timefold.tools.TimetableConstraintProperties.*;

public class TimetableConstraintProvider implements ConstraintProvider {
	@Override
	public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
		return new Constraint[]{
				ageDifference(constraintFactory),
				levelDifference(constraintFactory),
				groupSize(constraintFactory),
				groupHaveSameSessionConstraint(constraintFactory),
				//sessionTrainerWithoutPlayers(constraintFactory),
				//sessionWithPlayersNeedTrainer(constraintFactory),
				sessionOverlapDuration(constraintFactory),
				playerAvailability(constraintFactory),
				playerSingleSessionPerDay(constraintFactory)
		};
	}

	Constraint ageDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.AGE_OVERFLOW)
				.filter((x, ageOverflow) -> ageOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, ageOverflow) -> ageOverflow)
				.justifyWith(AgeDifferenceJustification::new)
				.asConstraint(GROUP_AGE_DIFFERENCE);
	}

	Constraint levelDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.LEVEL_OVERFLOW)
				.filter((x, levelOverflow) -> levelOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, levelOverflow) -> levelOverflow)
				.justifyWith(LevelDifferenceJustification::new)
				.asConstraint(GROUP_LEVEL_DIFFERENCE);
	}

	Constraint groupSize(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.GROUP_SIZE_DIFFERENCE)
				.filter((x, groupSizeDifference) -> groupSizeDifference != 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, groupSizeDifference) -> Math.abs(groupSizeDifference))
				.justifyWith(GroupSizeJustification::new)
				.asConstraint(GROUP_SIZE);
	}

	Constraint groupHaveSameSessionConstraint(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.DISTINCT_SESSION_CONSTRAINT)
				.filter((x, distinctSessionConstraint) -> distinctSessionConstraint > 1)
				.penalize(HardSoftScore.ONE_HARD, (x, distinctSessionConstraint) -> distinctSessionConstraint - 1)
				.justifyWith(GroupBySessionConstraintJustification::new)
				.asConstraint(GROUP_SESSION_CONSTRAINT);
	}

	// OFF
	Constraint sessionTrainerWithoutPlayers(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Session.class)
				.ifNotExists(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.penalize(HardSoftScore.ONE_HARD)
				.justifyWith(SessionTrainerWithoutPlayersJustification::new)
				.asConstraint(SESSION_TRAINER_WITHOUT_PLAYERS);
	}

	// OFF
	Constraint sessionWithPlayersNeedTrainer(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEachIncludingUnassigned(Session.class)
				.filter((session) -> session.getTrainer() == null)
				.ifExists(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.penalize(HardSoftScore.ONE_HARD)
				.justifyWith(SessionWithPlayersNeedTrainerJustification::new)
				.asConstraint(SESSION_WITH_PLAYERS_NEED_TRAINER);
	}

	Constraint sessionOverlapDuration(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.join(PlayerSessionLink.class, Joiners.lessThan(PlayerSessionLink::getSession))
				.groupBy((pslA, pslB) -> pslA.getSession(), CustomCollectors.SESSION_OVERLAPPING)
				.filter((session, overlapping) -> overlapping > 0)
				.penalize(HardSoftScore.ONE_HARD, (session, overlapping) -> overlapping)
				.justifyWith(SessionOverlapJustification::new)
				.asConstraint(SESSION_OVERLAPPING);
	}

	Constraint playerAvailability(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.filter((psl) -> !psl.getPlayer().isAvailable(psl.getSession()))
				.penalize(HardSoftScore.ONE_HARD)
				.justifyWith(PlayerAvailabilityJustification::new)
				.asConstraint(PLAYER_AVAILABILITY);
	}

	Constraint playerSingleSessionPerDay(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getPlayer, CustomCollectors.SINGLE_SESSION_PER_DAY)
				.filter((player, days) -> !days.isEmpty())
				.penalize(HardSoftScore.ONE_HARD, (player, days) -> days.size())
				.justifyWith(PlayerSingleSessionPerDayJustification::new)
				.asConstraint(PLAYER_SINGLE_SESSION_PER_DAY);
	}
}
