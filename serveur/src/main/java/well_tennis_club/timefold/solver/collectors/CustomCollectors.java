package well_tennis_club.timefold.solver.collectors;

/**
 * Cette classe est une Factory de Collectors personnalisés.
 */
public class CustomCollectors {
	public static final AgeOverflowCollector AGE_OVERFLOW = new AgeOverflowCollector();
	public static final LevelOverflowCollector LEVEL_OVERFLOW = new LevelOverflowCollector();
	public static final GroupSizeDifferenceCollector GROUP_SIZE_DIFFERENCE = new GroupSizeDifferenceCollector();
	public static final DistinctSessionConstraintCollector DISTINCT_SESSION_CONSTRAINT = new DistinctSessionConstraintCollector();
	public static final SessionOverlappingCollector SESSION_OVERLAPPING = new SessionOverlappingCollector();
	public static final SessionPerDayCollector SINGLE_SESSION_PER_DAY = new SessionPerDayCollector();
}
