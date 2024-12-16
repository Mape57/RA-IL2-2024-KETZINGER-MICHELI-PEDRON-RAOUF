package well_tennis_club.timefold.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import well_tennis_club.data_structure.Timeslot;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
	@Nested
	@DisplayName("Tests sur le constructeur Joueurs - Entraîneurs - Courts")
	class Timetable_PlayerTrainerTennisCourt_ConstructorTest {
		private static Timetable TIMETABLE;

		@BeforeAll
		static void setUp() {
			List<Player> players = List.of(
					new Player("p1", 0, 0, 2, null, null),
					new Player("p2", 0, 0, 1, null, null)
			);
			List<Trainer> trainers = List.of(
					new Trainer(null, null, null, null, false),
					new Trainer(null, null, null, null, false)
			);
			List<TennisCourt> tennisCourts = List.of(
					new TennisCourt("c1", List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 0)),
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(15, 30))
					)),
					new TennisCourt("c2", List.of(
							new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)),
							new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(15, 0))
					))
			);
			TIMETABLE = new Timetable(players, trainers, tennisCourts);
		}

		@Test
		@DisplayName("Les sessions sont correctements générées")
		void testSessionsGeneration() {
			assertEquals(List.of(
					new Session(DayOfWeek.MONDAY, LocalTime.of(8, 0), "c1"),
					new Session(DayOfWeek.MONDAY, LocalTime.of(8, 30), "c1"),
					new Session(DayOfWeek.MONDAY, LocalTime.of(14, 0), "c1"),
					new Session(DayOfWeek.MONDAY, LocalTime.of(14, 30), "c1"),
					new Session(DayOfWeek.MONDAY, LocalTime.of(15, 0), "c1"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(8, 0), "c2"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(8, 30), "c2"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(9, 0), "c2"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(9, 30), "c2"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(14, 0), "c2"),
					new Session(DayOfWeek.TUESDAY, LocalTime.of(14, 30), "c2")
			), TIMETABLE.getSessions());
		}

		@Test
		@DisplayName("Les liens joueur-session sont correctements générés")
		void testPlayerSessionLinksGeneration() {
			assertEquals(
					List.of("p1", "p1", "p2"),
					TIMETABLE.getPlayerSessionLinks().stream()
							.map(psl -> psl.getPlayer().getName())
							.toList()
			);
		}
	}
}