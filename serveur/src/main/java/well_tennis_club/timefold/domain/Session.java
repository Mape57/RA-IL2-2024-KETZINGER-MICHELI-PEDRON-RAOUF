package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

@Getter
@PlanningEntity
public class Session {
	private String tennisCourt;
	private DayOfWeek day;
	private LocalTime startTime;

	@PlanningVariable(allowsUnassigned = true)
	private Trainer trainer;

	public Session() {
	}

	public Session(DayOfWeek day, LocalTime startTime, String tennisCourt) {
		this.day = day;
		this.startTime = startTime;
		this.tennisCourt = tennisCourt;
	}

	public Session(DayOfWeek day, LocalTime startTime, String tennisCourt, Trainer trainer) {
		this(day, startTime, tennisCourt);
		this.trainer = trainer;
	}

	@Override
	public String toString() {
		return "[" + tennisCourt + " le " + getDayString() + " a " + startTime + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Session session = (Session) o;
		return Objects.equals(tennisCourt, session.tennisCourt) && day == session.day && Objects.equals(startTime, session.startTime) && Objects.equals(trainer, session.trainer);
	}

	/**
	 * Transforme le jour en chaine de caractères en français
	 *
	 * @return le jour traduit en français
	 */
	public String getDayString() {
		if (day == null) return "";
		return day.getDisplayName(TextStyle.FULL, Locale.FRENCH);
	}
}