package well_tennis_club.timefold.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import well_tennis_club.timefold.data_structure.Timeslot;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerTest {
	@Nested
	@DisplayName("Tests sur la méthode isAvailable")
	class IsAvailable {
		@Test
		@DisplayName("Disponible")
		void OK_available() {
			Trainer trainer = new Trainer(UUID.randomUUID(), null, null, null, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))
			), false);
			Session session = new Session(null, DayOfWeek.MONDAY, LocalTime.of(9, 0), UUID.randomUUID());
			assertTrue(trainer.isAvailable(session, 60));
		}

		@Test
		@DisplayName("Non disponible le jour entier")
		void KO_not_available() {
			Trainer trainer = new Trainer(UUID.randomUUID(), null, null, null, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))
			), false);
			Session session = new Session(null, DayOfWeek.THURSDAY, LocalTime.of(9, 0), UUID.randomUUID());
			assertFalse(trainer.isAvailable(session, 180));
		}

		@Test
		@DisplayName("Non disponible à l'heure de début")
		void KO_not_available_start() {
			Trainer trainer = new Trainer(UUID.randomUUID(), null, null, null, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))
			), false);
			Session session = new Session(null, DayOfWeek.MONDAY, LocalTime.of(8, 0), UUID.randomUUID());
			assertFalse(trainer.isAvailable(session, 180));
		}

		@Test
		@DisplayName("Non disponible à l'heure de fin")
		void KO_not_available_end() {
			Trainer trainer = new Trainer(UUID.randomUUID(), null, null, null, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))
			), false);
			Session session = new Session(null, DayOfWeek.MONDAY, LocalTime.of(11, 0), UUID.randomUUID());
			assertFalse(trainer.isAvailable(session, 180));
		}

		@Test
		@DisplayName("Non disponible à l'heure de début et de fin")
		void KO_not_available_start_end() {
			Trainer trainer = new Trainer(UUID.randomUUID(), null, null, null, List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))
			), false);
			Session session = new Session(null, DayOfWeek.MONDAY, LocalTime.of(15, 0), UUID.randomUUID());
			assertFalse(trainer.isAvailable(session, 180));
		}
	}
}
