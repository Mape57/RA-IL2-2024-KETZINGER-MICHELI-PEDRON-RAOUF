package well_tennis_club.timefold.solver.collectors;

/**
 * Cette classe est une Factory de Collectors personnalisés.
 */
public class CustomCollectors {
	private static final AgeOverflowCollector AGE_OVERFLOW_COLLECTOR = new AgeOverflowCollector();
	private static final LevelOverflowCollector LEVEL_OVERFLOW_COLLECTOR = new LevelOverflowCollector();
	private static final GroupSizeDifferenceCollector GROUP_SIZE_DIFFERENCE = new GroupSizeDifferenceCollector();

	public static AgeOverflowCollector ageOverflow() {
		return AGE_OVERFLOW_COLLECTOR;
	}

	public static LevelOverflowCollector levelOverflow() {
		return LEVEL_OVERFLOW_COLLECTOR;
	}

	public static GroupSizeDifferenceCollector groupSizeDifference() {
		return GROUP_SIZE_DIFFERENCE;
	}
}
