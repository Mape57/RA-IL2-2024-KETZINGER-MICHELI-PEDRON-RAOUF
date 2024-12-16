package well_tennis_club.timefold.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
}