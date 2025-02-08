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
		public static List<SessionConstraint> real() {
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
					new Trainer("trainer1", new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
					), false),
					new Trainer("trainer2", new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
					), false)
			);
		}

		public static List<Trainer> real() {
			return List.of(
					new Trainer("BRANDT Pierre", new ValueRange(1, 99), new ValueRange(14, 19), new ValueRange(1620, 1620), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 30))
					), true),
					new Trainer("HOUTMANN Bastien", new ValueRange(1, 99), new ValueRange(1, 19), new ValueRange(1680, 1680), List.of(
							new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
					), true),
					new Trainer("DESSOY Pierre", new ValueRange(1, 99), new ValueRange(1, 15), new ValueRange(600, 600), List.of(
							new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30))
					), true),
					new Trainer("HOMBOURGER Malo", new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(21, 0), LocalTime.of(22, 30))
					), false),
					new Trainer("HOMBOURGER Solal", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
							new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))
					), false),
					new Trainer("DOYEN Manon", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))
					), true),
					new Trainer("BODOT Lucie", new ValueRange(1, 99), new ValueRange(1, 13), new ValueRange(300, 420), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
							new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
							new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
							new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
					), true),
					new Trainer("MARIE Maxence", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
							new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))
					), false),
					new Trainer("MONTAGNE Nicolas", new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(21, 0)),
							new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(21, 0))
					), true),
					new Trainer("ADLINE Thomas", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
							new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
							new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
							new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
							new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
					), true)
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
					new Player("player01", 25, 9, 1, timeslot.small.group_1(), sessionConstraints.real()),
					new Player("player02", 25, 9, 1, timeslot.small.group_2(), sessionConstraints.real()),
					new Player("player03", 25, 9, 1, timeslot.small.group_2(), sessionConstraints.real())
			);
		}

		public static List<Player> medium() {
			return List.of(
					new Player("player01", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player02", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player03", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player04", 8, 5, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player05", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player06", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player07", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player08", 8, 5, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player09", 13, 7, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player10", 12, 7, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player11", 11, 6, 1, timeslot.medium.group_2(), sessionConstraints.real()),
					new Player("player12", 16, 3, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player13", 17, 2, 1, timeslot.medium.group_1(), sessionConstraints.real()),
					new Player("player14", 17, 3, 1, timeslot.medium.group_1(), sessionConstraints.real())
			);
		}

		public static List<Player> large() {
			return List.of(
					new Player("player52", 76, 2, 1, null, sessionConstraints.real()),
					new Player("player91", 86, 2, 1, null, sessionConstraints.real()),
					new Player("player34", 88, 2, 1, null, sessionConstraints.real()),
					new Player("player71", 61, 3, 1, null, sessionConstraints.real()),
					new Player("player19", 65, 3, 1, null, sessionConstraints.real()),
					new Player("player87", 90, 3, 1, null, sessionConstraints.real()),
					new Player("player42", 91, 3, 1, null, sessionConstraints.real()),
					new Player("player83", 31, 4, 1, null, sessionConstraints.real()),
					new Player("player25", 48, 4, 1, null, sessionConstraints.real()),
					new Player("player46", 50, 4, 1, null, sessionConstraints.real()),
					new Player("player56", 63, 4, 1, null, sessionConstraints.real()),
					new Player("player69", 68, 4, 1, null, sessionConstraints.real()),
					new Player("player75", 35, 5, 1, null, sessionConstraints.real()),
					new Player("player17", 41, 5, 1, null, sessionConstraints.real()),
					new Player("player50", 55, 5, 1, null, sessionConstraints.real()),
					new Player("player29", 77, 5, 1, null, sessionConstraints.real()),
					new Player("player61", 79, 5, 1, null, sessionConstraints.real()),
					new Player("player94", 82, 5, 1, null, sessionConstraints.real()),
					new Player("player67", 22, 6, 1, null, sessionConstraints.real()),
					new Player("player79", 23, 6, 1, null, sessionConstraints.real()),
					new Player("player58", 38, 6, 1, null, sessionConstraints.real()),
					new Player("player33", 42, 6, 1, null, sessionConstraints.real()),
					new Player("player89", 71, 6, 1, null, sessionConstraints.real()),
					new Player("player74", 57, 7, 1, null, sessionConstraints.real()),
					new Player("player22", 58, 7, 1, null, sessionConstraints.real()),
					new Player("player63", 60, 7, 1, null, sessionConstraints.real()),
					new Player("player40", 66, 7, 1, null, sessionConstraints.real()),
					new Player("player54", 89, 7, 1, null, sessionConstraints.real()),
					new Player("player96", 92, 7, 1, null, sessionConstraints.real()),
					new Player("player35", 37, 8, 1, null, sessionConstraints.real()),
					new Player("player20", 39, 8, 1, null, sessionConstraints.real()),
					new Player("player77", 46, 8, 1, null, sessionConstraints.real()),
					new Player("player66", 54, 8, 1, null, sessionConstraints.real()),
					new Player("player48", 70, 8, 1, null, sessionConstraints.real()),
					new Player("player60", 28, 9, 1, null, sessionConstraints.real()),
					new Player("player81", 51, 9, 1, null, sessionConstraints.real()),
					new Player("player28", 56, 9, 1, null, sessionConstraints.real()),
					new Player("player39", 59, 9, 1, null, sessionConstraints.real()),
					new Player("player15", 25, 10, 1, null, sessionConstraints.real()),
					new Player("player30", 29, 10, 1, null, sessionConstraints.real()),
					new Player("player64", 32, 10, 1, null, sessionConstraints.real()),
					new Player("player41", 34, 10, 1, null, sessionConstraints.real()),
					new Player("player98", 39, 10, 1, null, sessionConstraints.real()),
					new Player("player85", 44, 10, 1, null, sessionConstraints.real()),
					new Player("player51", 20, 11, 1, null, sessionConstraints.real()),
					new Player("player80", 67, 11, 1, null, sessionConstraints.real()),
					new Player("player97", 70, 11, 1, null, sessionConstraints.real()),
					new Player("player38", 75, 11, 1, null, sessionConstraints.real()),
					new Player("player26", 93, 11, 1, null, sessionConstraints.real()),
					new Player("player95", 47, 12, 1, null, sessionConstraints.real()),
					new Player("player36", 53, 12, 1, null, sessionConstraints.real()),
					new Player("player86", 62, 12, 1, null, sessionConstraints.real()),
					new Player("player72", 74, 12, 1, null, sessionConstraints.real()),
					new Player("player23", 83, 12, 1, null, sessionConstraints.real()),
					new Player("player59", 95, 12, 1, null, sessionConstraints.real()),
					new Player("player53", 33, 13, 1, null, sessionConstraints.real()),
					new Player("player62", 45, 13, 1, null, sessionConstraints.real()),
					new Player("player82", 84, 13, 1, null, sessionConstraints.real()),
					new Player("player55", 26, 14, 1, null, sessionConstraints.real()),
					new Player("player43", 48, 14, 1, null, sessionConstraints.real()),
					new Player("player10", 65, 14, 1, null, sessionConstraints.real()),
					new Player("player76", 69, 14, 1, null, sessionConstraints.real()),
					new Player("player21", 72, 14, 1, null, sessionConstraints.real()),
					new Player("player37", 24, 15, 1, null, sessionConstraints.real()),
					new Player("player88", 27, 15, 1, null, sessionConstraints.real()),
					new Player("player16", 32, 15, 1, null, sessionConstraints.real()),
					new Player("player68", 80, 15, 1, null, sessionConstraints.real()),
					new Player("player27", 30, 16, 1, null, sessionConstraints.real()),
					new Player("player45", 81, 16, 1, null, sessionConstraints.real()),
					new Player("player78", 87, 16, 1, null, sessionConstraints.real()),
					new Player("player92", 43, 17, 1, null, sessionConstraints.real()),
					new Player("player65", 73, 17, 1, null, sessionConstraints.real()),
					new Player("player49", 92, 17, 1, null, sessionConstraints.real()),
					new Player("player73", 19, 18, 1, null, sessionConstraints.real()),
					new Player("player24", 21, 18, 1, null, sessionConstraints.real()),
					new Player("player90", 49, 18, 1, null, sessionConstraints.real()),
					new Player("player57", 85, 18, 1, null, sessionConstraints.real()),
					new Player("player47", 36, 19, 1, null, sessionConstraints.real()),
					new Player("player70", 40, 19, 1, null, sessionConstraints.real()),
					new Player("player31", 64, 19, 1, null, sessionConstraints.real()),
					new Player("player84", 78, 19, 1, null, sessionConstraints.real())
			);
		}

		public static List<Player> real() {
			return List.of(
					new Player("ADJAL Martin", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("ATLANI Tom", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BADJI Ines", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BADJI Maylis", 5, 1, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BARBARY Gaspard", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BARBARY Louise", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BARREAULT Nikita", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BENNEOUALA Liam", 9, 6, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BENNEOUALA Neal", 7, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BENZINA Ibrahim", 9, 5, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("BERNAY Simon", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("BERTET Max - Alexandre", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BERTHE Mathis", 11, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("BIDAUD - LARCAT Victoire", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("BINAUD Marius", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BLANCHARD Audrey", 14, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("BLANCHARD Roland", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("BLIN Louison", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("BLUZAT Evan", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BLUZAT Luke", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BOIREAU Blanche", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("BRANDENBERG Arthur", 14, 11, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("BRIMONT Alice", 13, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("BRIMONT Camille", 17, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("BRUNIER Noé", 16, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(15, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("BRUNSTEIN HUGUENIN Noam", 13, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(15, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 45), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(14, 30))), sessionConstraints.real()),
					new Player("BRUNSTEIN HUGUENIN Oana", 9, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real()),
					new Player("CAMUS Noé", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("CARRÉ Lana", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("CHANDECLERC Alois", 18, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("CINQUALBRE Edouard", 15, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("COLLIN Roméo", 15, 9, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("CONART Gabriel", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("COURVOISIER Cloé", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("CUNY Jules", 14, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("CZESTOCHOWSKI Daniel", 12, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("DECRAENE Léo", 18, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(20, 31))), sessionConstraints.real()),
					new Player("DEDERICHS Achille", 11, 7, 3, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("DEDERICHS Arsène", 13, 9, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("DEDERICHS Vadim", 14, 9, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 31)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("DEDERICHS Camille", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("DEDERICHS Marin", 14, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("DELLESTABLE Alice", 17, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("DELLESTABLE Thibault", 14, 9, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("DEREINE - CHARRON Luce", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(18, 30))), sessionConstraints.real()),
					new Player("DIAS Rafael", 18, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real()),
					new Player("DORY Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("DROUILLY WENGER Estebane", 16, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("DUBOIS Camille", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("DUBOIS Ethan", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("EL ADSSI Rayan", 15, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("EVE Grégoire", 17, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("EVRARD Maxence", 7, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real()),
					new Player("FERRY Sacha", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("FIEVET-LESSER Hector", 16, 15, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("FINANCE Sacha", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30))), sessionConstraints.real()),
					new Player("FIXE-MARTZ Arthur", 14, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("FLESCHEN Léo", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("FLESCHEN Liam", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("GABRIEL MASSEHIAN Louis", 14, 11, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("GANSMÜLLER Théodore", 10, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30))), sessionConstraints.real()),
					new Player("GELLRICH Lyam", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("GEOFFROY Constant", 15, 15, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real()),
					new Player("GEORGE Louis", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("GODON Anna", 11, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("GODON Mahé", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("GREGOIRE THOMAS Elias", 11, 7, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("GRENOT Alexis", 12, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("GRENOT Martin", 18, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("GUERIN Arthur", 15, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("GUERIN Auguste", 10, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("HAUSBERGER Hugo", 17, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(20, 30))), sessionConstraints.real()),
					new Player("HERVE Clémence", 18, 17, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("HILSELBERGER Arthur", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("HOMBOURGER Paloma", 16, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("HSSINE Khayyem", 7, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("HUGUIN Amélien", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("HUTTIN Célestine", 14, 11, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("JACQUES Léo", 14, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("JEANNOT Tom", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real()),
					new Player("KADEM Eva", 17, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("KARM DELAUNAY Emy", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("KOCH Charles", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("KOCH  Raphaella", 11, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("KREMPF Marius", 5, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("L'HUILLIER Anatole", 11, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("L'HUILLIER Pacôme", 14, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("LAFARGE LAURENT Jasmin", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("LAUMONT KNAUF Andreas", 10, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("LECOANET Baptiste", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("LEDROIT Hector", 16, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("LEGER Céleste", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("LEGER Jade", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("LEHMANN Ferdinand", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("LEMOINE Laure - Line", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("LEVACHER BICHWILLER Victor", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("LIGIBEL Elsa", 17, 14, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real()),
					new Player("LOYE Clément", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("LUX Victor", 14, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("MAGISSON-KLEIN Amos", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("MALGRAS Clovis", 15, 12, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("MALGRAS Gaspard", 15, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real()),
					new Player("MANTE Gabin", 9, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("MANTE Marceau", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("MARCHAND Ilona", 15, 18, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("MARIA Gaspard", 11, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("MARNE HEINRICH Lino", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("MASSEHIAN Aristide", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("MASSEHIAN Léonard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("MC CALLA Raphael", 14, 10, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("MEILHAC Capucine", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("MICHAUX Baptiste", 17, 16, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real()),
					new Player("MICHAUX Paul", 11, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("MORTIER Axel", 15, 13, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("MORTIER Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30))), sessionConstraints.real()),
					new Player("NGUEMO Ava", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("NGUEMO Salomé", 8, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("NUNGE Martin", 8, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("OGBALET Salomon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("OKOLI Uchechukwu Angel", 5, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("PALMERO SOLER Mathéo", 11, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30))), sessionConstraints.real()),
					new Player("PALMERO SOLER Timothé", 12, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("PARIS Hector", 12, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("PARIS Octave", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("PAVANI  Gabriel", 14, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("PELLISSIER BLIQUE Victor", 11, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("PIA Ruben", 18, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("PIERLOT Achille", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("PINNA MEGE Charlie", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RABEHI Charlotte", 7, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RADENKOVIK Margaux", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("RAGOT Arthur", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RAPENNE Léon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RAYMOND Mael", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real()),
					new Player("REITER Maxime", 17, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("REMY Yvann", 9, 13, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("RICHARD Achille", 11, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RICHARD Ninon", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("RODERMANN Georges", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("ROQUES CARMES Louis", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("ROTATINTI Noé", 5, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("SAKANYAN Narek", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("SCHAACK Roman", 15, 16, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("SCHWARTZ Antoine", 9, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("SCHWEITZER Bastien", 18, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("SCHWITZER Valentin", 7, 6, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("SELTON Castille", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("SIMEONIN Judith", 16, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("SIMIER Edouard", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("SIMIER Margot", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("SIMONET Charlotte", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("SKALLI Nael", 15, 11, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("SKALLI Ziam", 11, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("SMEJKAL Louise", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0))), sessionConstraints.real()),
					new Player("SOBAGA Adèle", 17, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("STANOWSKI Gaspard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("STEPANIAN Léonard", 10, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("STEPANIAN Louis", 12, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("TALLOTTE Léon", 10, 14, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("TALLOTTE Rose", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("TARTE Zélie", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("TATOPOULOS Achille", 10, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("THEDENAT Marius", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("TILLY Gabriel", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("TOUTAIN Raphael", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("TREVIS Jean", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real()),
					new Player("VALETTE Léandre", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("VAUTRIN--DEHOVE Hermione", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("VERBRUGGHE Anna", 10, 7, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("VERBRUGGHE Roxane", 8, 7, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("VIAL Jules", 17, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("VIGNERON Théo", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("VOLODIMER César", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("VOLODIMER Martin", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("VOYDEVILLE Emile", 13, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("VOYDEVILLE Margot", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("WEBERT JUSSY Hugo", 6, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("WEBERT JUSSY Paul", 13, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real()),
					new Player("WINGERTER Côme", 10, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real()),
					new Player("WINGERTER Lilas", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					new Player("ZEYER Matthieu", 10, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real()),


					// ADULTS


					new Player("ADLINE Thomas", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("ALEXANDRE Odin", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("ANTONY Nathalie", 99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("BASTIAN Alexis", 99, 18, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 15), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("BELLINA Léa", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("BLAIN Tristan", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))), sessionConstraints.real()),
					new Player("BLANCO Ivhonne", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("BLIN Merlin", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("BLUZAT Emeric", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("BRUMBT Anaïs", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("BRUSCHI Julien", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("DAFONSECA Hugo", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("DEBRABANT Camille", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(13, 0))), sessionConstraints.real()),
					new Player("DECKER Bénédicte", 99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("DESTRACQUE Lina", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("DIAS Carlos", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("DOYEN Manon", 99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("EVRARD Antoine", 99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("FERRARO Mathilde", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("GERIN JeanYves", 99, 16, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(21, 0))), sessionConstraints.real()),
					new Player("GUIRAUD Thomas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("GORCHON Djeyda", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("GORCHON Jon", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("HERVE Yann", 99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("HERVO Clémence", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("HOMBOURGER Malo", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("HOMBOURGER Solal", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("JUMEAUX Tom", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("JUSSY Elodie", 99, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 15), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("KIRCHER Nicolas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("LALOT Jean-Marc", 99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("LANGLOIS Jordan", 99, 17, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("LAUMONT Olivier", 99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("LEBEE Matthieu", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("LECLERE Justine", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("LELONG Faustine", 99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(14, 0), LocalTime.of(17, 0))), sessionConstraints.real()),
					new Player("LOUIS Benjamin", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MARCHAL Tita", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("MARCHAL Patrice", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("MARCHAND Alexis", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MARIE Maxence", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MARTI Nathalie", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(20, 0))), sessionConstraints.real()),
					new Player("MAURY Mathieu", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MAYER Margaux", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("MICHEL Aurélie", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("MICHEL JeanCarl", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MONTAGNE Catherine", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("MONTAGNE Nicolas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("MORTIER Géraldine", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 0), LocalTime.of(21, 30))), sessionConstraints.real()),
					new Player("OLIVETTI Célia", 99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("PAULY Guillaume", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("PAYON Eliott", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("PERON Benoit", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("PIETTE Cléa", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("RAUCH Victor", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("RODERMANN Jean", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("ROQUES-CARMES Thibault", 99, 16, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("ROUGET Jean-Hugo", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("STOLL Hugo", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("TESTA Muriel", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("THOMASSIN Lucas", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("TOUTAIN Anne-Cécile", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("TOUTAIN Jean-Baptiste", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("TOURNADRE Claire", 99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("VIAL Florence", 99, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real()),
					new Player("WEBER Quentin", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real()),
					new Player("WEBERT David", 99, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real())

					// no availibility for these players

					//new Player("DELAMARRE Emilie", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					//new Player("FRASER Keely", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					//new Player("LUX Guillaume", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real()),
					//new Player("LUX Sophie", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real())
			);
		}
	}
}
