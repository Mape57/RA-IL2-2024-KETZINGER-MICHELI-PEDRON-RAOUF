package well_tennis_club.data_structure;

import well_tennis_club.timefold.domain.Timetable;

/**
 * Représente les contraintes d'une session.
 */
public record SessionConstraint(
		ValueRange ages,
		ValueRange levels,
		ValueRange groupSize,
		Integer ageDifference,
		Integer levelDifference,
		Integer duration
) {
	/**
	 * Constructeur de la contrainte d'une session.
	 *
	 * @param ages            le groupe d'âge (1 <= ages.min() <= ages.max() <= 99)
	 * @param levels          le groupe de niveau (levels == null || 0 <= levels.min() <= levels.max() <= 19)
	 * @param groupSize       la taille du groupe (1 <= groupSize.min())
	 * @param ageDifference   la différence d'âge (ageDifference == null || 0 <= ageDifference)
	 * @param levelDifference la différence de niveau (levelDifference == null || 0 <= levelDifference)
	 * @param duration        la durée de la session en minutes (1 <= duration)
	 */
	public SessionConstraint {
		if (ages == null) ages = new ValueRange(1, 99);
		else {
			if (ages.getMin() < 1) ages = new ValueRange(1, ages.getMax());
			if (ages.getMax() > 99) ages = new ValueRange(ages.getMin(), 99);
		}

		if (levels == null) levels = new ValueRange(0, 19);
		else {
			if (levels.getMin() < 0) levels = new ValueRange(0, levels.getMax());
			if (levels.getMax() > 19) levels = new ValueRange(levels.getMin(), 19);
		}

		if (groupSize == null) groupSize = new ValueRange(1, Integer.MAX_VALUE);
		else if (groupSize.getMin() < 1) groupSize = new ValueRange(1, groupSize.getMax());

		if (ageDifference == null) ageDifference = 100;
		else if (ageDifference < 0) ageDifference = 0;

		if (levelDifference == null) levelDifference = 20;
		else if (levelDifference < 0) levelDifference = 0;

		if (duration == null || duration < Timetable.MINIMUM_DURATION) duration = Timetable.MINIMUM_DURATION;
	}
}
