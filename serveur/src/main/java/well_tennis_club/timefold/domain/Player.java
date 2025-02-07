package well_tennis_club.timefold.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIdentityInfo(scope = Player.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Player {
	private String name;
	private int age;
	private int level;
	private int sessionPerWeek;
	private List<Timeslot> availability;
	private SessionConstraint sessionConstraint;

	public Player() {
	}

	public Player(String name, LocalDate birthDate, int level, int sessionPerWeek, List<Timeslot> availability, SessionConstraint sessionConstraint) {
		this.name = name;
		this.age = getSportsAge(birthDate);
		this.level = level;
		this.sessionPerWeek = sessionPerWeek;
		this.availability = availability;
		this.sessionConstraint = sessionConstraint;
	}

	public Player(String name, int age, int level, int sessionPerWeek, List<Timeslot> availability, List<SessionConstraint> sessionConstraints) {
		this.name = name;
		this.age = age;
		this.level = level;
		this.sessionPerWeek = sessionPerWeek;
		this.availability = availability;
		for (SessionConstraint sessionConstraint : sessionConstraints) {
			if (sessionConstraint.ages().contains(age) && sessionConstraint.levels().contains(level)) {
				this.sessionConstraint = sessionConstraint;
				break;
			}
		}
	}

	public Player(String name, int age, int level, int sessionPerWeek, List<Timeslot> availability, SessionConstraint sessionConstraint) {
		this.name = name;
		this.age = age;
		this.level = level;
		this.sessionPerWeek = sessionPerWeek;
		this.availability = availability;
		this.sessionConstraint = sessionConstraint;
	}

	/**
	 * L'âge d'une personne est l'âge qu'il aura à la fin de l'année sportive (septembre à août)<br>
	 * Exemple : pour l'année sportive 2024-2025 d'une personne née en 2004<br>
	 * - si elle est née avant septembre, elle a 21 ans<br>
	 * - si elle est née pendant ou après septembre, elle a 20 ans
	 *
	 * @return l'âge sportif de la personne
	 */
	public int getSportsAge(LocalDate birthDate) {
		LocalDate now = LocalDate.now();
		int age = now.getYear() - birthDate.getYear();
		if (now.getMonthValue() < 9) age--;
		if (birthDate.getMonthValue() < 9) age++;
		return age;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		if (availability == null ^ player.availability == null) return false;
		return age == player.age &&
				level == player.level &&
				sessionPerWeek == player.sessionPerWeek &&
				Objects.equals(name, player.name) &&
				Objects.equals(sessionConstraint, player.sessionConstraint) &&
				((availability == null) ||
						(availability.size() == player.availability.size() &&
								availability.containsAll(player.availability)));
	}

	@Override
	public String toString() {
		return name + '{' +
				"age=" + age +
				", level=" + level +
				", sessionPerWeek=" + sessionPerWeek +
				", availability=" + availability +
				", sessionConstraint=" + sessionConstraint +
				'}';
	}

	public boolean isAvailable(Session session) {
		LocalTime sessionStart = session.getStartTime();
		LocalTime sessionEnd = sessionStart.plusMinutes(sessionConstraint.duration());

		for (Timeslot timeslot : availability) {
			if (!Objects.equals(timeslot.day(), session.getDay())) continue;
			if (sessionStart.isBefore(timeslot.startTime()) || sessionEnd.isAfter(timeslot.endTime())) continue;
			return true;
		}
		return false;
	}
}
