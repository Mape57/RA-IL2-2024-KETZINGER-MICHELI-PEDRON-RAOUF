package well_tennis_club.timefold.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;
import well_tennis_club.timefold.domain.Trainer;
import well_tennis_club.timefold.solver.collectors.CustomCollectors;
import well_tennis_club.timefold.solver.justifications.*;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Function;

import static well_tennis_club.timefold.tools.TimetableConstraintProperties.*;

public class TimetableConstraintProvider implements ConstraintProvider {
	private static final int POWER = 2;

	@Override
	public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
		return new Constraint[]{
				ageDifference(constraintFactory),
				levelDifference(constraintFactory),
				groupSize(constraintFactory),
				groupHaveSameSessionConstraint(constraintFactory),
				sessionTrainerWithoutPlayers(constraintFactory),
				sessionWithPlayersNeedTrainer(constraintFactory),
				sessionOverlapping(constraintFactory),
				playerAvailability(constraintFactory),
				playerSingleSessionPerDay(constraintFactory),
				trainerAvailability(constraintFactory),
				trainerPreferedAge(constraintFactory),
				trainerPreferedLevel(constraintFactory),
				//trainerweeklyMinutes(constraintFactory),
				//trainerweeklyMinutesWithNoPlanification(constraintFactory),
				trainerSessionOverlapping(constraintFactory)
		};
	}

	Constraint ageDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.ageOverflow())
				.filter((x, ageOverflow) -> ageOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, ageOverflow) -> (int) Math.pow(ageOverflow, POWER))
				.justifyWith(AgeDifferenceJustification::new)
				.asConstraint(GROUP_AGE_DIFFERENCE);
	}

	Constraint levelDifference(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.levelOverflow())
				.filter((x, levelOverflow) -> levelOverflow > 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, levelOverflow) -> (int) Math.pow(levelOverflow, POWER))
				.justifyWith(LevelDifferenceJustification::new)
				.asConstraint(GROUP_LEVEL_DIFFERENCE);
	}

	Constraint groupSize(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.groupSizeDifference())
				.filter((x, groupSizeDifference) -> groupSizeDifference != 0)
				.penalize(HardSoftScore.ONE_SOFT, (x, groupSizeDifference) -> (int) Math.pow(groupSizeDifference, POWER))
				.justifyWith(GroupSizeJustification::new)
				.asConstraint(GROUP_SIZE);
	}

	Constraint groupHaveSameSessionConstraint(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getSession, CustomCollectors.distinctSessionConstraint())
				.filter((x, distinctSessionConstraint) -> distinctSessionConstraint > 1)
				.penalize(HardSoftScore.ONE_HARD, (x, distinctSessionConstraint) -> (int) Math.pow(distinctSessionConstraint - 1, POWER))
				.justifyWith(GroupBySessionConstraintJustification::new)
				.asConstraint(GROUP_SESSION_CONSTRAINT);
	}

	Constraint sessionTrainerWithoutPlayers(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Session.class)
				.ifNotExists(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.penalize(HardSoftScore.ONE_SOFT)
				.justifyWith(SessionTrainerWithoutPlayersJustification::new)
				.asConstraint(SESSION_TRAINER_WITHOUT_PLAYERS);
	}

	Constraint sessionWithPlayersNeedTrainer(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEachIncludingUnassigned(Session.class)
				.filter((session) -> session.getTrainer() == null)
				.ifExists(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.penalize(HardSoftScore.ONE_HARD)
				.justifyWith(SessionWithPlayersNeedTrainerJustification::new)
				.asConstraint(SESSION_WITH_PLAYERS_NEED_TRAINER);
	}

	Constraint sessionOverlapping(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.join(PlayerSessionLink.class, Joiners.lessThan(PlayerSessionLink::getSession))
				.groupBy((pslA, pslB) -> pslA.getSession(), CustomCollectors.sessionOverlapping())
				.filter((session, overlapping) -> overlapping > 0)
				.penalize(HardSoftScore.ONE_HARD, (session, overlapping) -> (int) Math.pow(overlapping, POWER))
				.justifyWith(SessionOverlapJustification::new)
				.asConstraint(SESSION_OVERLAPPING);
	}

	Constraint playerAvailability(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.filter((psl) -> !psl.getPlayer().isAvailable(psl.getSession()))
				.penalize(HardSoftScore.ofHard(100))
				.justifyWith(PlayerAvailabilityJustification::new)
				.asConstraint(PLAYER_AVAILABILITY);
	}

	Constraint playerSingleSessionPerDay(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(PlayerSessionLink.class)
				.groupBy(PlayerSessionLink::getPlayer, CustomCollectors.singleSessionPerDay())
				.filter((player, days) -> !days.isEmpty())
				.penalize(HardSoftScore.ONE_HARD, (player, days) -> (int) Math.pow(days.size(), POWER))
				.justifyWith(PlayerSingleSessionPerDayJustification::new)
				.asConstraint(PLAYER_SINGLE_SESSION_PER_DAY);
	}

	Constraint trainerAvailability(ConstraintFactory constraintFactory) {
		// il est possible de vérifier la disponibilité d'un entraineur seulement si des joueurs sont liés à la session
		return constraintFactory
				.forEach(Session.class)
				.join(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.groupBy((session, psl) -> session, CustomCollectors.sessionDuration())
				.filter((session, duration) -> !session.getTrainer().isAvailable(session, duration))
				.penalize(HardSoftScore.ofHard(100))
				.justifyWith(TrainerAvailabilityJustification::new)
				.asConstraint(TRAINER_AVAILABILITY);
	}

	Constraint trainerPreferedAge(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Session.class)
				.join(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.groupBy((session, psl) -> session, CustomCollectors.trainerAgeOverflow())
				.filter((session, trainerAgeOverflow) -> trainerAgeOverflow > 0)
				.penalize(HardSoftScore.ONE_HARD, (session, trainerAgeOverflow) -> (int) Math.pow(trainerAgeOverflow, POWER))
				.justifyWith(TrainerPreferedAgeJustification::new)
				.asConstraint(TRAINER_PREFERED_AGE);
	}

	Constraint trainerPreferedLevel(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Session.class)
				.join(PlayerSessionLink.class, Joiners.equal(Function.identity(), PlayerSessionLink::getSession))
				.groupBy((session, psl) -> session, CustomCollectors.trainerLevelOverflow())
				.filter((session, trainerLevelOverflow) -> trainerLevelOverflow > 0)
				.penalize(HardSoftScore.ONE_HARD, (session, trainerLevelOverflow) -> (int) Math.pow(trainerLevelOverflow, POWER))
				.justifyWith(TrainerPreferedLevelJustification::new)
				.asConstraint(TRAINER_PREFERED_LEVEL);
	}

	Constraint trainerweeklyMinutes(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Trainer.class)
				.join(PlayerSessionLink.class, Joiners.equal(Function.identity(), psl -> psl.getSession().getTrainer()))
				.groupBy((trainer, psl) -> trainer, CustomCollectors.trainerweeklyMinutes())
				.filter((trainer, weeklyMinutes) -> !trainer.getWeeklyMinutes().contains(weeklyMinutes))
				.penalize(HardSoftScore.ONE_HARD, (trainer, weeklyMinutes) -> (int) Math.pow(Duration.ofMinutes(trainer.getWeeklyMinutes().difference(weeklyMinutes)).toHours(), POWER))
				.justifyWith(TrainerweeklyMinutesJustification::new)
				.asConstraint(TRAINER_WEEKLY_HOURS);
	}

	Constraint trainerweeklyMinutesWithNoPlanification(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEach(Trainer.class)
				.ifNotExists(PlayerSessionLink.class, Joiners.equal(Function.identity(), psl -> psl.getSession().getTrainer()))
				.expand((trainer) -> {
					ValueRange weeklyMinutes = trainer.getWeeklyMinutes();
					if (weeklyMinutes.getMin() == 0) return 0;
					long hours = Duration.ofMinutes(trainer.getWeeklyMinutes().getMax()).toHours();
					return (int) Math.pow(hours, POWER);
				})
				.filter((trainer, maxHours) -> maxHours > 0)
				.penalize(HardSoftScore.ONE_HARD, (trainer, maxHours) -> maxHours)
				.justifyWith(TrainerweeklyMinutesJustification::new)
				.asConstraint(TRAINER_WEEKLY_HOURS_WITH_NO_PLANIFICATION);
	}

	Constraint trainerSessionOverlapping(ConstraintFactory constraintFactory) {
		return constraintFactory
				.forEachUniquePair(Session.class,
						Joiners.equal(Session::getTrainer),
						Joiners.equal(Session::getDay),
						Joiners.equal(Session::getStartTime),
						Joiners.filtering((sessionA, sessionB) -> !Objects.equals(sessionA.getTennisCourt(), sessionB.getTennisCourt()))
				).penalize(HardSoftScore.ONE_HARD)
				.justifyWith(TrainerSessionOverlappingJustification::new)
				.asConstraint(TRAINER_SESSION_OVERLAPPING);
	}
}
