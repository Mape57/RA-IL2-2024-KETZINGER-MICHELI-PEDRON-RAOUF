package well_tennis_club.timefold.solver;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ConstraintsTest {
	@Autowired
	private ConstraintVerifier<TimetableConstraintProvider, Timetable> constraintVerifier;

	// ages,		levels,		grpSizes,	ageDif,		lvlDif,		duration
	// (1, 99),		null,		(1, +inf),	null,		null,		60
	public static final SessionConstraint SESSION_CONSTRAINT_NONE = new SessionConstraint(null, null, null, null, null, null);
	// (3, 4),		null,		(4, 6),		null,		null,		60
	public static final SessionConstraint SESSION_CONSTRAINT_group_size_2_3 = new SessionConstraint(null, null, new ValueRange(2, 3), null, null, null);
	// (8, 17),		(0, 7),		(3, 6),		1 & 2,			1,			60
	public static final SessionConstraint SESSION_CONSTRAINT_age_diff_1 = new SessionConstraint(null, null, null, 1, null, null);
	public static final SessionConstraint SESSION_CONSTRAINT_age_diff_2 = new SessionConstraint(null, null, null, 2, null, null);
	// (8, 17),		(8, 19),	(3, 6),		2,			1,			90
	public static final SessionConstraint SESSION_CONSTRAINT_level_diff_1 = new SessionConstraint(null, null, null, null, 1, null);
	// (5, 7),		(3, 15),	(4, 6),		2,			1,			60
	public static final SessionConstraint SESSION_CONSTRAINT_COMPLEX_1 = new SessionConstraint(new ValueRange(5, 7), new ValueRange(3, 15), new ValueRange(4, 6), 2, 1, 60);
	public static final SessionConstraint SESSION_CONSTRAINT_COMPLEX_2 = new SessionConstraint(new ValueRange(5, 7), new ValueRange(3, 15), new ValueRange(4, 6), 2, 1, 60);

	/**
	 * Crée une liste de PlayerSessionLink à partir d'une liste de Player.
	 *
	 * @param players la liste de Player
	 * @return la liste de PlayerSessionLink de même taille que players
	 */
	private List<PlayerSessionLink> pslsWithBlankSession(List<Player> players) {
		Session session = new Session(null, null, null, null);
		return players.stream().map(player -> new PlayerSessionLink(player, session)).toList();
	}

	@Nested
	@DisplayName("Contrainte sur la différence d'âge")
	class AgeDifference {
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
						new Player(null, 11, 0, 0, null, SESSION_CONSTRAINT_age_diff_1),
						new Player(null, 12, 0, 0, null, SESSION_CONSTRAINT_age_diff_1)
				);

				List<PlayerSessionLink> psls_1 = pslsWithBlankSession(players_group_1);
				List<PlayerSessionLink> psls_2 = pslsWithBlankSession(players_group_2);

				constraintVerifier.verifyThat(TimetableConstraintProvider::ageDifference)
						.given(psls_1.get(0), psls_2.get(0), psls_2.get(1), psls_1.get(1))
						.penalizesBy(0);
			}

			@Test
			@DisplayName("Deux groupes dont un respecte la contrainte")
			void KO_one_group() {
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
	class LevelDifference {
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
			void KO_one_group() {
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
						.given(psls_1.get(0), psls_1.get(1), psls_2.get(0), psls_2.get(1))
						.penalizesBy(1);
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
	class GroupSize {
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
			void KO_one_group() {
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

	@Nested
	@DisplayName("Contrainte sur le regroupement par SessionConstraint")
	class GroupHaveSameSessionConstraint {
		@Test
		@DisplayName("Les joueurs ont la même SessionConstraint")
		void OK_same_session_constraint() {
			List<Player> players = List.of(
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_COMPLEX_1),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_COMPLEX_2),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_COMPLEX_1)
			);

			List<PlayerSessionLink> psls = pslsWithBlankSession(players);

			constraintVerifier.verifyThat(TimetableConstraintProvider::groupHaveSameSessionConstraint)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Un joueur n'a pas la même SessionConstraint")
		void KO_one_player() {
			List<Player> players = List.of(
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_COMPLEX_1),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_COMPLEX_1),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_level_diff_1)
			);

			List<PlayerSessionLink> psls = pslsWithBlankSession(players);

			constraintVerifier.verifyThat(TimetableConstraintProvider::groupHaveSameSessionConstraint)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(1);
		}

		@Test
		@DisplayName("Deux joueurs n'ont pas la même SessionConstraint")
		void KO_two_players() {
			List<Player> players = List.of(
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_NONE),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_level_diff_1),
					new Player(null, 0, 0, 0, null, SESSION_CONSTRAINT_age_diff_2)
			);

			List<PlayerSessionLink> psls = pslsWithBlankSession(players);

			constraintVerifier.verifyThat(TimetableConstraintProvider::groupHaveSameSessionConstraint)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(2);
		}
	}

	@Nested
	@DisplayName("Contrainte sur la présence d'un entraîneur dans une session vide")
	class SessionWithTrainerNeedTrainer {
		@Test
		@DisplayName("Entraîneur présent avec joueur")
		void OK_trainer_player() {
			Session session = new Session(null, null, null, null, new Trainer());
			Player player = new Player();
			PlayerSessionLink psl = new PlayerSessionLink(player, session);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionTrainerWithoutPlayers)
					.given(session, psl)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Entraîneur présent sans joueur")
		void KO_trainer_no_player() {
			Session session = new Session(null, null, null, null, new Trainer());

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionTrainerWithoutPlayers)
					.given(session)
					.penalizesBy(1);
		}
	}

	@Nested
	@DisplayName("Contrainte sur l'absence d'un entraîneur dans une session planifiée")
	class SessionWithPlayersNeedTrainer {
		@Test
		@DisplayName("Entraîneur présent avec joueur")
		void OK_player_trainer() {
			Session session = new Session(null, null, null, null, new Trainer());
			Player player = new Player();
			PlayerSessionLink psl = new PlayerSessionLink(player, session);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionWithPlayersNeedTrainer)
					.given(session, psl)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Entraîneur absent avec joueur")
		void KO_player_no_trainer() {
			Session session = new Session(null, null, null, null);
			Player player = new Player();
			PlayerSessionLink psl = new PlayerSessionLink(player, session);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionWithPlayersNeedTrainer)
					.given(session, psl)
					.penalizesBy(1);
		}

	}

	@Nested
	@DisplayName("Contrainte sur la superposition des sessions")
	class SessionOverlapDuration {
		private SessionConstraint sc;
		private Player p1;
		private Player p2;
		private Player p3;

		@BeforeEach
		void setUp() {
			sc = new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(1, 3), 2, 1, 90);
			p1 = new Player("P1", 8, 5, 1, null, sc);
			p2 = new Player("P2", 9, 6, 1, null, sc);
			p3 = new Player("P3", 10, 5, 1, null, sc);
		}

		@Test
		@DisplayName("Les sessions ne sont pas sur le même terrain")
		void OK_no_overlap_tennisCourt() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T2");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Les sessions ne sont pas le même jour")
		void OK_no_overlap_day() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.TUESDAY, LocalTime.of(8, 0), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Les sessions ne sont pas à la même heure")
		void OK_no_overlap_time() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(10, 0), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Les sessions se suivent")
		void OK_following_sessions() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(9, 30), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Les joueurs sont dans la même session")
		void OK_same_session() {
			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Les sessions se chevauchent (30min d'écart)")
		void KO_overlapping_sessions_30mn() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 30), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(1);
		}

		@Test
		@DisplayName("Les sessions se chevauchent (1H d'écart)")
		void KO_overlapping_sessions_1H() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(9, 0), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2)
					.penalizesBy(1);
		}

		@Test
		@DisplayName("Trois sessions se chevauchent")
		void KO_overlapping_sessions_3() {
			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 30), "T1");
			Session session_3 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(9, 0), "T1");

			PlayerSessionLink psl_1 = new PlayerSessionLink(p1, session_1);
			PlayerSessionLink psl_2 = new PlayerSessionLink(p2, session_2);
			PlayerSessionLink psl_3 = new PlayerSessionLink(p3, session_3);

			constraintVerifier.verifyThat(TimetableConstraintProvider::sessionOverlapDuration)
					.given(psl_1, psl_2, psl_3)
					.penalizesBy(3);
		}
	}

	@Nested
	@DisplayName("Contrainte sur les disponibilités des joueurs")
	class PlayerAvailability {
		@Test
		@DisplayName("Les joueurs sont disponibles")
		void OK_players_available() {
			SessionConstraint sc = new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(1, 3), 2, 1, 90);
			Player p1 = new Player("P1", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);
			Player p2 = new Player("P2", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);
			Player p3 = new Player("P3", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);

			List<PlayerSessionLink> psls = List.of(
					new PlayerSessionLink(p1, new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1")),
					new PlayerSessionLink(p2, new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1")),
					new PlayerSessionLink(p3, new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1"))
			);

			constraintVerifier.verifyThat(TimetableConstraintProvider::playerAvailability)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Un joueur n'est pas disponible")
		void KO_one_player_absent() {
			SessionConstraint sc = new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(1, 3), 2, 1, 90);
			Player p1 = new Player("P1", 9, 5, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);
			Player p2 = new Player("P2", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);
			Player p3 = new Player("P3", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0))), sc);

			Session session = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			List<PlayerSessionLink> psls = List.of(
					new PlayerSessionLink(p1, session),
					new PlayerSessionLink(p2, session),
					new PlayerSessionLink(p3, session)
			);

			constraintVerifier.verifyThat(TimetableConstraintProvider::playerAvailability)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(1);
		}
	}

	@Nested
	@DisplayName("Contrainte sur le nombre de sessions par jour")
	class PlayerSingleSessionPerDay {
		@Test
		@DisplayName("Un joueur a une session par jour")
		void OK_one_session() {
			SessionConstraint sc = new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(1, 3), 2, 1, 90);
			Player p1 = new Player("P1", 9, 5, 1, null, sc);
			Player p2 = new Player("P2", 9, 5, 1, null, sc);

			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.TUESDAY, LocalTime.of(8, 0), "T1");
			Session session_3 = new Session(UUID.randomUUID(), DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), "T1");

			List<PlayerSessionLink> psls = List.of(
					new PlayerSessionLink(p1, session_1),
					new PlayerSessionLink(p2, session_2),
					new PlayerSessionLink(p2, session_3)
			);

			constraintVerifier.verifyThat(TimetableConstraintProvider::playerSingleSessionPerDay)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(0);
		}

		@Test
		@DisplayName("Un joueur a deux sessions le même jour")
		void KO_two_sessions() {
			SessionConstraint sc = new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(1, 3), 2, 1, 90);
			Player p1 = new Player("P1", 9, 5, 1, null, sc);
			Player p2 = new Player("P2", 9, 5, 1, null, sc);

			Session session_1 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(8, 0), "T1");
			Session session_2 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(10, 0), "T1");
			Session session_3 = new Session(UUID.randomUUID(), DayOfWeek.MONDAY, LocalTime.of(15, 0), "T1");

			List<PlayerSessionLink> psls = List.of(
					new PlayerSessionLink(p1, session_1),
					new PlayerSessionLink(p2, session_2),
					new PlayerSessionLink(p2, session_3)
			);

			constraintVerifier.verifyThat(TimetableConstraintProvider::playerSingleSessionPerDay)
					.given(psls.get(0), psls.get(1), psls.get(2))
					.penalizesBy(1);
		}
	}
}