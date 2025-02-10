package well_tennis_club.timefold.data_structure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueRangeTest {
	@Nested
	@DisplayName("Tests sur le constructeur à partir d'une liste de valeurs")
	class ListConstructor {
		@Test
		@DisplayName("La liste est vide")
		void empty_list() {
			ValueRange valueRange = new ValueRange(List.of());
			assertEquals(Integer.MAX_VALUE, valueRange.getMin());
			assertEquals(Integer.MIN_VALUE, valueRange.getMax());
		}

		@Test
		@DisplayName("La liste contient une seule valeur")
		void one_value() {
			ValueRange valueRange = new ValueRange(List.of(5));
			assertEquals(5, valueRange.getMin());
			assertEquals(5, valueRange.getMax());
		}

		@Test
		@DisplayName("La liste contient plusieurs valeurs")
		void several_values() {
			ValueRange valueRange = new ValueRange(List.of(3, 6, 4));
			assertEquals(3, valueRange.getMin());
			assertEquals(6, valueRange.getMax());
		}
	}

	@Nested
	@DisplayName("Tests sur la méthode contains")
	class Constains {
		private final ValueRange valueRange = new ValueRange(3, 6);

		@Test
		@DisplayName("La valeur est contenue dans la plage")
		void contains() {
			assertTrue(valueRange.contains(4));
		}

		@Test
		@DisplayName("La valeur est la borne inférieure de la plage")
		void contains_min() {
			assertTrue(valueRange.contains(3));
		}

		@Test
		@DisplayName("La valeur est la borne supérieure de la plage")
		void contains_max() {
			assertTrue(valueRange.contains(6));
		}

		@Test
		@DisplayName("La valeur est inférieure à la plage")
		void not_contains_inf() {
			assertFalse(valueRange.contains(2));
		}

		@Test
		@DisplayName("La valeur est supérieure à la plage")
		void not_contains_sup() {
			assertFalse(valueRange.contains(7));
		}
	}

	@Nested
	@DisplayName("Tests sur la méthode size")
	class Size {
		@Test
		@DisplayName("La plage est d'une seule valeur (min = max)")
		void size_one() {
			ValueRange valueRange = new ValueRange(5, 5);
			assertEquals(1, valueRange.size());
		}

		@Test
		@DisplayName("La plage contient plusieurs valeurs")
		void size_several() {
			ValueRange valueRange = new ValueRange(3, 6);
			assertEquals(4, valueRange.size());
		}
	}
}