package well_tennis_club.timefold.domain;

import lombok.Getter;
import well_tennis_club.data_structure.SessionConstraint;
import well_tennis_club.data_structure.Timeslot;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Player {
	private String name;
	private int age;
	private int level;
	private int sessionPerWeek;
	private List<Timeslot> availability;
	private SessionConstraint sessionConstraint;

	public Player(String name, LocalDate birthDate, int level, int sessionPerWeek, List<Timeslot> availability, SessionConstraint sessionConstraint) {
		this.name = name;
		this.age = getSportsAge(birthDate);
		this.level = level;
		this.sessionPerWeek = sessionPerWeek;
		this.availability = availability;
		this.sessionConstraint = sessionConstraint;
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
		int age = LocalDate.now().getYear() - birthDate.getYear();
		if (birthDate.getMonthValue() < 9) {
			age++;
		}
		return age;
	}
}
