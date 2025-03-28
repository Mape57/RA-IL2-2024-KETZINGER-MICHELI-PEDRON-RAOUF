package well_tennis_club.timefold.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.tools.difficulty_comparator.SessionDifficultyComparator;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@PlanningEntity(difficultyComparatorClass = SessionDifficultyComparator.class)
@JsonIdentityInfo(scope = Session.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Session implements Comparable<Session>, Serializable {
	@PlanningId
	private UUID id;

	private UUID tennisCourt;
	private String tennisCourtName;
	private DayOfWeek day;
	private LocalTime startTime;

	@PlanningVariable(allowsUnassigned = true)
	private Trainer trainer;

	public Session() {
	}

	public Session(UUID id, DayOfWeek day, LocalTime startTime, UUID tennisCourt) {
		this.id = id;
		this.day = day;
		this.startTime = startTime;
		this.tennisCourt = tennisCourt;
	}

	public Session(UUID id, DayOfWeek day, LocalTime startTime, UUID tennisCourt, Trainer trainer) {
		this(id, day, startTime, tennisCourt);
		this.trainer = trainer;
	}

	public Session(UUID id, DayOfWeek day, LocalTime startTime, UUID tennisCourt, String tennisCourtName) {
		this(id, day, startTime, tennisCourt);
		this.tennisCourtName = tennisCourtName;
	}

	public Session(UUID id, DayOfWeek day, LocalTime startTime, UUID tennisCourt, String tennisCourtName, Trainer trainer) {
		this(id, day, startTime, tennisCourt, trainer);
		this.tennisCourtName = tennisCourtName;
	}

	@Override
	public String toString() {
		return tennisCourtName + " le " + getDayString() + " à " + startTime;
	}

	/**
	 * Transforme le jour en chaine de caractères en français
	 *
	 * @return le jour traduit en français
	 */
	@JsonIgnore
	public String getDayString() {
		if (day == null) return "";
		return day.getDisplayName(TextStyle.FULL, Locale.FRENCH);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Session session = (Session) o;
		return Objects.equals(id, session.id);
	}

	@Override
	public int compareTo(Session o) {
		if (o == null) return 1;
		int dayComparison = day.compareTo(o.day);
		if (dayComparison != 0) return dayComparison;
		return startTime.compareTo(o.startTime);
	}
}