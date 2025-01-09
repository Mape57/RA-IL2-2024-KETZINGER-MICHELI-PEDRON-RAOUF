package well_tennis_club.timefold.tools;

import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.TennisCourt;
import well_tennis_club.timefold.domain.Trainer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public final class SampleData {
	public static class sessionConstraints {
		public static List<SessionConstraint> medium() {
			return List.of(
					// ages,		levels,		grpSizes,	ageDif,		lvlDif,		duration
					// (3, 4),		null,		(4, 6),		null,		null,		60
					new SessionConstraint(new ValueRange(3, 4), null, new ValueRange(4, 6), null, null, 60),
					// (5, 7),		null,		(4, 6),		null,		1,			60
					new SessionConstraint(new ValueRange(5, 7), null, new ValueRange(4, 6), null, 1, 60),
					// (8, 17),		(0, 7),		(3, 6),		2,			1,			60
					new SessionConstraint(new ValueRange(8, 17), new ValueRange(0, 7), new ValueRange(3, 6), 2, 1, 60),
					// (8, 17),		(8, 19),	(3, 6),		2,			1,			90
					new SessionConstraint(new ValueRange(8, 17), new ValueRange(8, 19), new ValueRange(3, 6), 2, 1, 90),
					// (18, 99),	null,		(3, 6),		null,		1,			90
					new SessionConstraint(new ValueRange(18, 99), null, new ValueRange(3, 6), null, 1, 90)
			);
		}
	}

	public static class trainers {
		public static List<Trainer> small() {
			return List.of(
					new Trainer("trainer1", new ValueRange(0, 18), new ValueRange(0, 10), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
					), false),
					new Trainer("trainer2", new ValueRange(0, 18), new ValueRange(0, 10), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
					), false)
			);
		}
	}

	public static class tennisCourts {
		public static List<TennisCourt> small() {
			return List.of(
					new TennisCourt("Terrain 1", List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(14, 0))
					))
			);
		}

		public static List<TennisCourt> medium() {
			return List.of(
					new TennisCourt("Terrain 1", timeslot.medium.group_1()),
					new TennisCourt("Terrain 2", timeslot.medium.group_1())
			);
		}

		public static List<TennisCourt> real() {
			return List.of(
					new TennisCourt("Terrain 1", timeslot.large.group_1()),
					new TennisCourt("Terrain 2", timeslot.large.group_2()),
					new TennisCourt("Terrain 3", timeslot.large.group_2())
			);
		}
	}

	public static class timeslot {
		public static class small {
			public static List<Timeslot> group_1() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				);
			}

			public static List<Timeslot> group_2() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
				);
			}
		}

		public static class medium {
			public static List<Timeslot> group_1() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				);
			}

			public static List<Timeslot> group_2() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
				);
			}
		}

		public static class large {
			public static List<Timeslot> group_1() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))
				);
			}

			public static List<Timeslot> group_2() {
				return List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				);
			}
		}
	}

	public static class players {
		public static List<Player> small() {
			return List.of(
					new Player("player01", 25, 9, 1, timeslot.small.group_1(), sessionConstraints.medium()),
					new Player("player02", 25, 9, 1, timeslot.small.group_2(), sessionConstraints.medium()),
					new Player("player03", 25, 9, 1, timeslot.small.group_2(), sessionConstraints.medium())
			);
		}

		public static List<Player> medium() {
			return List.of(
					new Player("player01", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player02", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player03", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player04", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player05", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player06", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player07", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player08", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player09", 13, 7, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player10", 12, 7, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player11", 11, 6, 1, timeslot.medium.group_2(), sessionConstraints.medium()),
					new Player("player12", 16, 3, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player13", 17, 2, 1, timeslot.medium.group_1(), sessionConstraints.medium()),
					new Player("player14", 17, 3, 1, timeslot.medium.group_1(), sessionConstraints.medium())
			);
		}

		public static List<Player> large() {
			return List.of(
					new Player("player52", 76, 2, 1, null, sessionConstraints.medium()),
					new Player("player91", 86, 2, 1, null, sessionConstraints.medium()),
					new Player("player34", 88, 2, 1, null, sessionConstraints.medium()),
					new Player("player71", 61, 3, 1, null, sessionConstraints.medium()),
					new Player("player19", 65, 3, 1, null, sessionConstraints.medium()),
					new Player("player87", 90, 3, 1, null, sessionConstraints.medium()),
					new Player("player42", 91, 3, 1, null, sessionConstraints.medium()),
					new Player("player83", 31, 4, 1, null, sessionConstraints.medium()),
					new Player("player25", 48, 4, 1, null, sessionConstraints.medium()),
					new Player("player46", 50, 4, 1, null, sessionConstraints.medium()),
					new Player("player56", 63, 4, 1, null, sessionConstraints.medium()),
					new Player("player69", 68, 4, 1, null, sessionConstraints.medium()),
					new Player("player75", 35, 5, 1, null, sessionConstraints.medium()),
					new Player("player17", 41, 5, 1, null, sessionConstraints.medium()),
					new Player("player50", 55, 5, 1, null, sessionConstraints.medium()),
					new Player("player29", 77, 5, 1, null, sessionConstraints.medium()),
					new Player("player61", 79, 5, 1, null, sessionConstraints.medium()),
					new Player("player94", 82, 5, 1, null, sessionConstraints.medium()),
					new Player("player67", 22, 6, 1, null, sessionConstraints.medium()),
					new Player("player79", 23, 6, 1, null, sessionConstraints.medium()),
					new Player("player58", 38, 6, 1, null, sessionConstraints.medium()),
					new Player("player33", 42, 6, 1, null, sessionConstraints.medium()),
					new Player("player89", 71, 6, 1, null, sessionConstraints.medium()),
					new Player("player74", 57, 7, 1, null, sessionConstraints.medium()),
					new Player("player22", 58, 7, 1, null, sessionConstraints.medium()),
					new Player("player63", 60, 7, 1, null, sessionConstraints.medium()),
					new Player("player40", 66, 7, 1, null, sessionConstraints.medium()),
					new Player("player54", 89, 7, 1, null, sessionConstraints.medium()),
					new Player("player96", 92, 7, 1, null, sessionConstraints.medium()),
					new Player("player35", 37, 8, 1, null, sessionConstraints.medium()),
					new Player("player20", 39, 8, 1, null, sessionConstraints.medium()),
					new Player("player77", 46, 8, 1, null, sessionConstraints.medium()),
					new Player("player66", 54, 8, 1, null, sessionConstraints.medium()),
					new Player("player48", 70, 8, 1, null, sessionConstraints.medium()),
					new Player("player60", 28, 9, 1, null, sessionConstraints.medium()),
					new Player("player81", 51, 9, 1, null, sessionConstraints.medium()),
					new Player("player28", 56, 9, 1, null, sessionConstraints.medium()),
					new Player("player39", 59, 9, 1, null, sessionConstraints.medium()),
					new Player("player15", 25, 10, 1, null, sessionConstraints.medium()),
					new Player("player30", 29, 10, 1, null, sessionConstraints.medium()),
					new Player("player64", 32, 10, 1, null, sessionConstraints.medium()),
					new Player("player41", 34, 10, 1, null, sessionConstraints.medium()),
					new Player("player98", 39, 10, 1, null, sessionConstraints.medium()),
					new Player("player85", 44, 10, 1, null, sessionConstraints.medium()),
					new Player("player51", 20, 11, 1, null, sessionConstraints.medium()),
					new Player("player80", 67, 11, 1, null, sessionConstraints.medium()),
					new Player("player97", 70, 11, 1, null, sessionConstraints.medium()),
					new Player("player38", 75, 11, 1, null, sessionConstraints.medium()),
					new Player("player26", 93, 11, 1, null, sessionConstraints.medium()),
					new Player("player95", 47, 12, 1, null, sessionConstraints.medium()),
					new Player("player36", 53, 12, 1, null, sessionConstraints.medium()),
					new Player("player86", 62, 12, 1, null, sessionConstraints.medium()),
					new Player("player72", 74, 12, 1, null, sessionConstraints.medium()),
					new Player("player23", 83, 12, 1, null, sessionConstraints.medium()),
					new Player("player59", 95, 12, 1, null, sessionConstraints.medium()),
					new Player("player53", 33, 13, 1, null, sessionConstraints.medium()),
					new Player("player62", 45, 13, 1, null, sessionConstraints.medium()),
					new Player("player82", 84, 13, 1, null, sessionConstraints.medium()),
					new Player("player55", 26, 14, 1, null, sessionConstraints.medium()),
					new Player("player43", 48, 14, 1, null, sessionConstraints.medium()),
					new Player("player100", 65, 14, 1, null, sessionConstraints.medium()),
					new Player("player76", 69, 14, 1, null, sessionConstraints.medium()),
					new Player("player21", 72, 14, 1, null, sessionConstraints.medium()),
					new Player("player37", 24, 15, 1, null, sessionConstraints.medium()),
					new Player("player88", 27, 15, 1, null, sessionConstraints.medium()),
					new Player("player16", 32, 15, 1, null, sessionConstraints.medium()),
					new Player("player68", 80, 15, 1, null, sessionConstraints.medium()),
					new Player("player27", 30, 16, 1, null, sessionConstraints.medium()),
					new Player("player45", 81, 16, 1, null, sessionConstraints.medium()),
					new Player("player78", 87, 16, 1, null, sessionConstraints.medium()),
					new Player("player92", 43, 17, 1, null, sessionConstraints.medium()),
					new Player("player65", 73, 17, 1, null, sessionConstraints.medium()),
					new Player("player49", 92, 17, 1, null, sessionConstraints.medium()),
					new Player("player73", 19, 18, 1, null, sessionConstraints.medium()),
					new Player("player24", 21, 18, 1, null, sessionConstraints.medium()),
					new Player("player90", 49, 18, 1, null, sessionConstraints.medium()),
					new Player("player57", 85, 18, 1, null, sessionConstraints.medium()),
					new Player("player47", 36, 19, 1, null, sessionConstraints.medium()),
					new Player("player70", 40, 19, 1, null, sessionConstraints.medium()),
					new Player("player31", 64, 19, 1, null, sessionConstraints.medium()),
					new Player("player84", 78, 19, 1, null, sessionConstraints.medium())
			);
		}

		public static List<Player> real() {
			return List.of(
					new Player("ADJAL Martin", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("ATLANI Tom", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BADJI Ines", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BADJI Maylis", 5, 1, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BARBARY Gaspard", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BARBARY Louise", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BARREAULT Nikita", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BENNEOUALA Liam", 9, 6, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BENNEOUALA Neal", 7, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BENZINA Ibrahim", 9, 5, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("BERNAY Simon", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("BERTET Max - Alexandre", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BERTHE Mathis", 11, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("BIDAUD - LARCAT Victoire", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("BINAUD Marius", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BLANCHARD Audrey", 14, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("BLANCHARD Roland", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("BLIN Louison", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("BLUZAT Evan", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BLUZAT Luke", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BOIREAU Blanche", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("BRANDENBERG Arthur", 14, 11, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("BRIMONT Alice", 13, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("BRIMONT Camille", 17, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("BRUNIER Noé", 16, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(15, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("BRUNSTEIN HUGUENIN Noam", 13, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(15, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 45), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(14, 30))), sessionConstraints.medium()),
					new Player("BRUNSTEIN HUGUENIN Oana", 9, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.medium()),
					new Player("CAMUS Noé", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("CARRÉ Lana", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("CHANDECLERC Alois", 18, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.medium()),
					new Player("CINQUALBRE Edouard", 15, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("COLLIN Roméo", 15, 9, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("CONART Gabriel", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("COURVOISIER Cloé", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("CUNY Jules", 14, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("CZESTOCHOWSKI Daniel", 12, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("DECRAENE Léo", 18, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(20, 31))), sessionConstraints.medium()),
					new Player("DEDERICHS Achille", 11, 7, 3, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("DEDERICHS Arsène", 13, 9, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("DEDERICHS Vadim", 14, 9, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 31)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("DEDERICHS Camille", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("DEDERICHS Marin", 14, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("DELLESTABLE Alice", 17, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("DELLESTABLE Thibault", 14, 9, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("DEREINE - CHARRON Luce", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(18, 30))), sessionConstraints.medium()),
					new Player("DIAS Rafael", 18, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.medium()),
					new Player("DORY Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("DROUILLY WENGER Estebane", 16, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("DUBOIS Camille", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("DUBOIS Ethan", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("EL ADSSI Rayan", 15, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("EVE Grégoire", 17, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("EVRARD Maxence", 7, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.medium()),
					new Player("FERRY Sacha", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("FIEVET-LESSER Hector", 16, 15, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("FINANCE Sacha", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30))), sessionConstraints.medium()),
					new Player("FIXE-MARTZ Arthur", 14, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("FLESCHEN Léo", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("FLESCHEN Liam", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("GABRIEL MASSEHIAN Louis", 14, 11, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("GANSMÜLLER Théodore", 10, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30))), sessionConstraints.medium()),
					new Player("GELLRICH Lyam", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("GEOFFROY Constant", 15, 15, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.medium()),
					new Player("GEORGE Louis", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("GODON Anna", 11, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("GODON Mahé", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("GREGOIRE THOMAS Elias", 11, 7, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("GRENOT Alexis", 12, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("GRENOT Martin", 18, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.medium()),
					new Player("GUERIN Arthur", 15, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("GUERIN Auguste", 10, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("HAUSBERGER Hugo", 17, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(20, 30))), sessionConstraints.medium()),
					new Player("HERVE Clémence", 18, 17, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("HILSELBERGER Arthur", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("HOMBOURGER Paloma", 16, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("HSSINE Khayyem", 7, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("HUGUIN Amélien", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("HUTTIN Célestine", 14, 11, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("JACQUES Léo", 14, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("JEANNOT Tom", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.medium()),
					new Player("KADEM Eva", 17, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("KARM DELAUNAY Emy", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("KOCH Charles", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("KOCH  Raphaella", 11, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("KREMPF Marius", 5, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("L'HUILLIER Anatole", 11, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("L'HUILLIER Pacôme", 14, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("LAFARGE LAURENT Jasmin", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("LAUMONT KNAUF Andreas", 10, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("LECOANET Baptiste", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("LEDROIT Hector", 16, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("LEGER Céleste", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("LEGER Jade", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("LEHMANN Ferdinand", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("LEMOINE Laure - Line", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("LEVACHER BICHWILLER Victor", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("LIGIBEL Elsa", 17, 14, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.medium()),
					new Player("LOYE Clément", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("LUX Victor", 14, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("MAGISSON-KLEIN Amos", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("MALGRAS Clovis", 15, 12, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("MALGRAS Gaspard", 15, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.medium()),
					new Player("MANTE Gabin", 9, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("MANTE Marceau", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("MARCHAND Ilona", 15, 18, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("MARIA Gaspard", 11, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("MARNE HEINRICH Lino", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("MASSEHIAN Aristide", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("MASSEHIAN Léonard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("MC CALLA Raphael", 14, 10, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("MEILHAC Capucine", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("MICHAUX Baptiste", 17, 16, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.medium()),
					new Player("MICHAUX Paul", 11, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("MORTIER Axel", 15, 13, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("MORTIER Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30))), sessionConstraints.medium()),
					new Player("NGUEMO Ava", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("NGUEMO Salomé", 8, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("NUNGE Martin", 8, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("OGBALET Salomon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("OKOLI Uchechukwu Angel", 5, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("PALMERO SOLER Mathéo", 11, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30))), sessionConstraints.medium()),
					new Player("PALMERO SOLER Timothé", 12, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("PARIS Hector", 12, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("PARIS Octave", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("PAVANI  Gabriel", 14, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("PELLISSIER BLIQUE Victor", 11, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("PIA Ruben", 18, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("PIERLOT Achille", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("PINNA MEGE Charlie", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RABEHI Charlotte", 7, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RADENKOVIK Margaux", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("RAGOT Arthur", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RAPENNE Léon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RAYMOND Mael", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.medium()),
					new Player("REITER Maxime", 17, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("REMY Yvann", 9, 13, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("RICHARD Achille", 11, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RICHARD Ninon", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("RODERMANN Georges", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("ROQUES CARMES Louis", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("ROTATINTI Noé", 5, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("SAKANYAN Narek", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("SCHAACK Roman", 15, 16, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("SCHWARTZ Antoine", 9, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("SCHWEITZER Bastien", 18, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("SCHWITZER Valentin", 7, 6, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("SELTON Castille", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("SIMEONIN Judith", 16, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("SIMIER Edouard", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("SIMIER Margot", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("SIMONET Charlotte", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("SKALLI Nael", 15, 11, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("SKALLI Ziam", 11, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("SMEJKAL Louise", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0))), sessionConstraints.medium()),
					new Player("SOBAGA Adèle", 17, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("STANOWSKI Gaspard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("STEPANIAN Léonard", 10, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("STEPANIAN Louis", 12, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("TALLOTTE Léon", 10, 14, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("TALLOTTE Rose", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("TARTE Zélie", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("TATOPOULOS Achille", 10, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("THEDENAT Marius", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("TILLY Gabriel", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("TOUTAIN Raphael", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("TREVIS Jean", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.medium()),
					new Player("VALETTE Léandre", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("VAUTRIN--DEHOVE Hermione", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("VERBRUGGHE Anna", 10, 7, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("VERBRUGGHE Roxane", 8, 7, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("VIAL Jules", 17, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0))), sessionConstraints.medium()),
					new Player("VIGNERON Théo", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("VOLODIMER César", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("VOLODIMER Martin", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("VOYDEVILLE Emile", 13, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))), sessionConstraints.medium()),
					new Player("VOYDEVILLE Margot", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.medium()),
					new Player("WEBERT JUSSY Hugo", 6, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("WEBERT JUSSY Paul", 13, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.medium()),
					new Player("WINGERTER Côme", 10, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.medium()),
					new Player("WINGERTER Lilas", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.medium()),
					new Player("ZEYER Matthieu", 10, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.medium())
			);
		}
	}
}
