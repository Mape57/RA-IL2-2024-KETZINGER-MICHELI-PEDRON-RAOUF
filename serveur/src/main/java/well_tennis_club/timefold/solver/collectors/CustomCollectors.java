package well_tennis_club.timefold.solver.collectors;

/**
 * Cette classe est une Factory de Collectors personnalisés.
 */
public class CustomCollectors {
	public static AgeOverflowCollector ageOverflow() {
		return new AgeOverflowCollector();
	}

	public static LevelOverflowCollector levelOverflow() {
		return new LevelOverflowCollector();
	}

	public static GroupSizeDifferenceCollector groupSizeDifference() {
		return new GroupSizeDifferenceCollector();
	}

	public static DistinctSessionConstraintCollector distinctSessionConstraint() {
		return new DistinctSessionConstraintCollector();
	}

	public static SessionOverlappingCollector sessionOverlapping() {
		return new SessionOverlappingCollector();
	}

	public static SessionPerDayCollector singleSessionPerDay() {
		return new SessionPerDayCollector();
	}

	public static SessionDurationCollector sessionDuration() {
		return new SessionDurationCollector();
	}

	public static TrainerAgeOverflowCollector trainerAgeOverflow() {
		return new TrainerAgeOverflowCollector();
	}

	public static TrainerLevelOverflowCollector trainerLevelOverflow() {
		return new TrainerLevelOverflowCollector();
	}

	public static TrainerweeklyMinutesCollector trainerweeklyMinutes() {
		return new TrainerweeklyMinutesCollector();
	}
}
