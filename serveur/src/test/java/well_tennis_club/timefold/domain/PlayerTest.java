package well_tennis_club.timefold.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	@Nested
	@DisplayName("Tests sur la méthode getSportAge")
	class GetSportAge {
		@Test
		@DisplayName("Né juste avant septembre")
		void OK_before_september() {
			Player player = new Player(null, LocalDate.of(2000, 8, 15), 0, 0, null, null);
			assertEquals(LocalDate.now().getYear() - (2000 - 1), player.getAge());
		}

		@Test
		@DisplayName("Né juste au début de septembre")
		void OK_after_septembre() {
			Player player = new Player(null, LocalDate.of(2000, 9, 1), 0, 0, null, null);
			assertEquals(LocalDate.now().getYear() - 2000, player.getAge());
		}

		@Test
		@DisplayName("Né en début d'année")
		void OK_start_of_year() {
			Player player = new Player(null, LocalDate.of(2000, 1, 1), 0, 0, null, null);
			assertEquals(LocalDate.now().getYear() - (2000 - 1), player.getAge());
		}

		@Test
		@DisplayName("Né en fin d'année")
		void OK_end_of_year() {
			Player player = new Player(null, LocalDate.of(2000, 12, 31), 0, 0, null, null);
			assertEquals(LocalDate.now().getYear() - 2000, player.getAge());
		}
	}

	@Nested
	@DisplayName("Tests sur la méthode isAvailable")
	class IsAvailable {
		@Test
		@DisplayName("Disponible")
		void OK_available() {
			Player player = new Player("P1", 10, 1, 1, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
			), new SessionConstraint(null, null, null, 1, 1, 60));
			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(10, 0), "T1");
			assertTrue(player.isAvailable(session));
		}

		@Test
		@DisplayName("Non disponible le jour entier")
		void KO_not_available_day() {
			Player player = new Player("P1", 10, 1, 1, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
			), new SessionConstraint(null, null, null, 1, 1, 60));
			Session session = new Session(UUID.randomUUID(), DayOfWeek.THURSDAY, LocalTime.of(9, 0), "T1");
			assertFalse(player.isAvailable(session));
		}

		@Test
		@DisplayName("Non disponible à l'heure de début")
		void KO_not_available_start() {
			Player player = new Player("P1", 10, 1, 1, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 0))
			), new SessionConstraint(null, null, null, 1, 1, 60));
			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(10, 0), "T1");
			assertFalse(player.isAvailable(session));
		}

		@Test
		@DisplayName("Non disponible à l'heure de fin")
		void KO_not_available_end() {
			Player player = new Player("P1", 10, 1, 1, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 30))
			), new SessionConstraint(null, null, null, 1, 1, 60));
			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(11, 0), "T1");
			assertFalse(player.isAvailable(session));
		}

		@Test
		@DisplayName("Non disponible à l'heure de début et de fin")
		void KO_not_available_start_end() {
			Player player = new Player("P1", 10, 1, 1, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30))
			), new SessionConstraint(null, null, null, 1, 1, 60));
			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			assertFalse(player.isAvailable(session));
		}
	}
}