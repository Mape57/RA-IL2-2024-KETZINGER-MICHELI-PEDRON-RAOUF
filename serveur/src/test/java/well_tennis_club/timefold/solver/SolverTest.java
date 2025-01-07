package well_tennis_club.timefold.solver;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import well_tennis_club.data_structure.SessionConstraint;
import well_tennis_club.data_structure.ValueRange;
import well_tennis_club.timefold.domain.*;

import java.util.List;

@SpringBootTest
public class SolverTest {
	@Autowired
	private ConstraintVerifier<TimetableConstraintProvider, Timetable> constraintVerifier;

	// ages,		levels,		grpSizes,	ageDif,		lvlDif,		duration
	// (1, 99),		null,		(1, +inf),	null,		null,		60
	public static final SessionConstraint SESSION_CONSTRAINT_NONE = new SessionConstraint(null, null, null, null, null, null);
	// (3, 4),		null,		(4, 6),		null,		null,		60
	public static final SessionConstraint SESSION_CONSTRAINT_group_size_2_3 = new SessionConstraint(null, null, new ValueRange(2, 3), null, null, null);
	// (8, 17),		(0, 7),		(3, 6),		2,			1,			60
	public static final SessionConstraint SESSION_CONSTRAINT_age_diff_2 = new SessionConstraint(null, null, null, 2, null, null);
	// (8, 17),		(8, 19),	(3, 6),		2,			1,			90
	public static final SessionConstraint SESSION_CONSTRAINT_level_diff_1 = new SessionConstraint(null, null, null, null, 1, null);

	/**
	 * Crée une liste de PlayerSessionLink à partir d'une liste de Player.
	 *
	 * @param players la liste de Player
	 * @return la liste de PlayerSessionLink de même taille que players
	 */
	private List<PlayerSessionLink> pslsWithBlankSession(List<Player> players) {
		Session session = new Session(null, null, null);
		return players.stream().map(player -> new PlayerSessionLink(player, session)).toList();
	}

	@Nested
	@DisplayName("Contrainte sur la différence d'âge")
	class AgeDifferenceConstraintTest {
		@Nested
		@DisplayName("Tests sur la contrainte")
		class ConstraintTest {
			@Test
			@DisplayName("Les joueurs ont une différence d'âge correspondant au maximum")
			void OK_maximum_difference() {
				List<Player> players = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 12, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 11, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Les joueurs ont une différence d'âge de zéro")
			void OK_no_difference() {
				List<Player> players = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Les joueurs ont une différence d'âge supérieure au maximum")
			void KO_max_difference_plus_1() {
				List<Player> players = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 14, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 11, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(2);
			}
		}

		@Nested
		@DisplayName("Tests sur plusieurs groupes")
		class GroupsTest {
			@Test
			@DisplayName("Deux groupes respectant la contrainte")
			void OK_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 12, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 11, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 13, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Deux groupes dont un respecte la contrainte")
			void OK_one_group() {
				List<Player> players_group_1 = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 12, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 13, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(1);
			}

			@Test
			@DisplayName("Deux groupes ne respectant pas la contrainte")
			void KO_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 10, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 13, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 9, 0, 0, null, SESSION_CONSTRAINT_age_diff_2),
						new Player(null, 15, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(5); // 1 + 4
			}
		}
	}

	@Nested
	@DisplayName("Contrainte sur la différence de niveau")
	class LevelDifferenceConstraintTest {
		@Nested
		@DisplayName("Tests sur la contrainte")
		class ConstraintTest {
			@Test
			@DisplayName("Les joueurs ont une différence de niveau correspondant au maximum")
			void OK_maximum_difference() {
				List<Player> players = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 2, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Les joueurs ont une différence de niveau de zéro")
			void OK_no_difference() {
				List<Player> players = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Les joueurs ont une différence de niveau supérieure au maximum")
			void KO_max_difference_plus_1() {
				List<Player> players = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 3, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(1);
			}
		}

		@Nested
		@DisplayName("Tests sur plusieurs groupes")
		class GroupsTest {
			@Test
			@DisplayName("Deux groupes respectant la contrainte")
			void OK_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 2, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 2, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Deux groupes dont un respecte la contrainte")
			void OK_one_group() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 2, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 3, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1));
			}

			@Test
			@DisplayName("Deux groupes ne respectant pas la contrainte")
			void KO_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 3, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 1, 0, null, SESSION_CONSTRAINT_level_diff_1),
						new Player(null, 0, 4, 0, null, SESSION_CONSTRAINT_level_diff_1)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::levelDifference)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(3);
			}
		}
	}

	@Nested
	@DisplayName("Contrainte sur la taille du groupe")
	class GroupSizeConstraintTest {
		@Nested
		@DisplayName("Tests sur la contrainte")
		class ConstraintTest {
			@Test
			@DisplayName("La taille du groupe est égal à la taille minimum")
			void OK_min_group_size() {
				List<Player> players = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls.get(0), psls.get(1))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("La taille du groupe est égal à la taille maximum")
			void OK_max_group_size() {
				List<Player> players = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls.get(0), psls.get(1), psls.get(2))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("La taille du groupe est inférieure à la taille minimum")
			void KO_min_group_size_minus_1() {
				List<Player> players = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls.get(0))
						.penalizesBy(1);
			}

			@Test
			@DisplayName("La taille du groupe est supérieure à la taille maximum")
			void KO_max_group_size_plus_1() {
				List<Player> players = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls = pslsWithBlankSession(players);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls.get(0), psls.get(1), psls.get(2), psls.get(3))
						.penalizesBy(1);
			}
		}

		@Nested
		@DisplayName("Tests sur plusieurs groupes")
		class GroupsTest {
			@Test
			@DisplayName("Deux groupes respectant la contrainte")
			void OK_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Deux groupes dont un respecte la contrainte")
			void OK_one_group() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0))
						.penalizesBy(1);
			}

			@Test
			@DisplayName("Deux groupes ne respectant pas la contrainte")
			void KO_two_groups() {
				List<Player> players_group_1 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);
				List<Player> players_group_2 = List.of(
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3),
						new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_group_size_2_3)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::groupSize)
						.given(psls_1.get(0), psls_2.get(0), psls_2.get(1), psls_2.get(2), psls_2.get(3))
						.penalizesBy(2);
			}
		}
	}
}