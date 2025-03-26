package well_tennis_club.timefold.tools;

import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;
import well_tennis_club.timefold.domain.Player;
import well_tennis_club.timefold.domain.TennisCourt;
import well_tennis_club.timefold.domain.Trainer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SampleData {
	public static class sessionConstraints {
		public static List<SessionConstraint> real = List.of(
				// ages,		levels,		grpSizes,	ageDif,		lvlDif,		duration
				// (3, 4),		null,		(4, 6),		null,		null,		60
				new SessionConstraint(new ValueRange(3, 4), null, new ValueRange(4, 6), null, null, 60),
				// (5, 7),		null,		(4, 6),		null,		1,			60
				new SessionConstraint(new ValueRange(5, 7), null, new ValueRange(4, 6), null, 1, 60),
				// (8, 17),		(0, 7),		(3, 6),		2,			1,			60
				new SessionConstraint(new ValueRange(8, 18), new ValueRange(0, 7), new ValueRange(3, 6), 2, 1, 60),
				// (8, 17),		(8, 19),	(3, 6),		2,			1,			90
				new SessionConstraint(new ValueRange(8, 18), new ValueRange(8, 19), new ValueRange(3, 6), 2, 1, 90),
				// (18, 99),	null,		(3, 6),		null,		1,			90
				new SessionConstraint(new ValueRange(19, 99), null, new ValueRange(3, 6), null, 1, 90)
		);
	}

	public static class trainers {
		public static List<Trainer> small = List.of(
				new Trainer(UUID.randomUUID(), new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false),
				new Trainer(UUID.randomUUID(), new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false)
		);

		public static List<Trainer> real_adult = List.of(
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(14, 19), new ValueRange(1620, 1620), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 30))
				), true),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(1, 19), new ValueRange(1680, 1680), List.of(
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(1, 15), new ValueRange(600, 600), List.of(
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30))
				), true),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(21, 0), LocalTime.of(22, 30))
				), false),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(1, 13), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(21, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(21, 0))
				), true)
		);

		public static List<Trainer> real_child = List.of(
				new Trainer(UUID.randomUUID(), new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))
				), false),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))
				), true),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))
				), false),
				new Trainer(UUID.randomUUID(), new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true)
		);

		public static List<Trainer> real() {
			List<Trainer> trainers = new ArrayList<>();
			trainers.addAll(real_adult);
			trainers.addAll(real_child);
			return trainers;
		}
	}

	public static class tennisCourts {
		public static List<TennisCourt> small = List.of(
				new TennisCourt("Terrain 1", List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(8, 30))
				))
		);

		public static List<TennisCourt> medium = List.of(
				new TennisCourt("Terrain 1", timeslot.medium.group_1),
				new TennisCourt("Terrain 2", timeslot.medium.group_1)
		);

		public static List<TennisCourt> real = List.of(
				new TennisCourt("Terrain 1", timeslot.large.group_1),
				new TennisCourt("Terrain 2", timeslot.large.group_2),
				new TennisCourt("Terrain 3", timeslot.large.group_2)
		);
	}

	public static class timeslot {
		public static class small {
			public static List<Timeslot> group_1 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
			);

			public static List<Timeslot> group_2 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
			);
		}

		public static class medium {
			public static List<Timeslot> group_1 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
			);

			public static List<Timeslot> group_2 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)),
					new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(12, 0))
			);
		}

		public static class large {
			public static List<Timeslot> group_1 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))
			);

			public static List<Timeslot> group_2 = List.of(
					new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)),
					new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
			);
		}
	}

	public static class players {
		public static List<Player> small = List.of(
				new Player(25, 9, 1, timeslot.small.group_1, sessionConstraints.real.get(2)),
				new Player(28, 9, 1, timeslot.small.group_2, sessionConstraints.real.get(2)),
				new Player(25, 9, 1, timeslot.small.group_2, sessionConstraints.real.get(2))
		);

		public static List<Player> medium = List.of(
				new Player(8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(13, 7, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(12, 7, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(11, 6, 1, timeslot.medium.group_2, sessionConstraints.real),
				new Player(16, 3, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(17, 2, 1, timeslot.medium.group_1, sessionConstraints.real),
				new Player(17, 3, 1, timeslot.medium.group_1, sessionConstraints.real)
		);

		public static List<Player> large = List.of(
				new Player(76, 2, 1, null, sessionConstraints.real),
				new Player(86, 2, 1, null, sessionConstraints.real),
				new Player(88, 2, 1, null, sessionConstraints.real),
				new Player(61, 3, 1, null, sessionConstraints.real),
				new Player(65, 3, 1, null, sessionConstraints.real),
				new Player(90, 3, 1, null, sessionConstraints.real),
				new Player(91, 3, 1, null, sessionConstraints.real),
				new Player(31, 4, 1, null, sessionConstraints.real),
				new Player(48, 4, 1, null, sessionConstraints.real),
				new Player(50, 4, 1, null, sessionConstraints.real),
				new Player(63, 4, 1, null, sessionConstraints.real),
				new Player(68, 4, 1, null, sessionConstraints.real),
				new Player(35, 5, 1, null, sessionConstraints.real),
				new Player(41, 5, 1, null, sessionConstraints.real),
				new Player(55, 5, 1, null, sessionConstraints.real),
				new Player(77, 5, 1, null, sessionConstraints.real),
				new Player(79, 5, 1, null, sessionConstraints.real),
				new Player(82, 5, 1, null, sessionConstraints.real),
				new Player(22, 6, 1, null, sessionConstraints.real),
				new Player(23, 6, 1, null, sessionConstraints.real),
				new Player(38, 6, 1, null, sessionConstraints.real),
				new Player(42, 6, 1, null, sessionConstraints.real),
				new Player(71, 6, 1, null, sessionConstraints.real),
				new Player(57, 7, 1, null, sessionConstraints.real),
				new Player(58, 7, 1, null, sessionConstraints.real),
				new Player(60, 7, 1, null, sessionConstraints.real),
				new Player(66, 7, 1, null, sessionConstraints.real),
				new Player(89, 7, 1, null, sessionConstraints.real),
				new Player(92, 7, 1, null, sessionConstraints.real),
				new Player(37, 8, 1, null, sessionConstraints.real),
				new Player(39, 8, 1, null, sessionConstraints.real),
				new Player(46, 8, 1, null, sessionConstraints.real),
				new Player(54, 8, 1, null, sessionConstraints.real),
				new Player(70, 8, 1, null, sessionConstraints.real),
				new Player(28, 9, 1, null, sessionConstraints.real),
				new Player(51, 9, 1, null, sessionConstraints.real),
				new Player(56, 9, 1, null, sessionConstraints.real),
				new Player(59, 9, 1, null, sessionConstraints.real),
				new Player(25, 10, 1, null, sessionConstraints.real),
				new Player(29, 10, 1, null, sessionConstraints.real),
				new Player(32, 10, 1, null, sessionConstraints.real),
				new Player(34, 10, 1, null, sessionConstraints.real),
				new Player(39, 10, 1, null, sessionConstraints.real),
				new Player(44, 10, 1, null, sessionConstraints.real),
				new Player(20, 11, 1, null, sessionConstraints.real),
				new Player(67, 11, 1, null, sessionConstraints.real),
				new Player(70, 11, 1, null, sessionConstraints.real),
				new Player(75, 11, 1, null, sessionConstraints.real),
				new Player(93, 11, 1, null, sessionConstraints.real),
				new Player(47, 12, 1, null, sessionConstraints.real),
				new Player(53, 12, 1, null, sessionConstraints.real),
				new Player(62, 12, 1, null, sessionConstraints.real),
				new Player(74, 12, 1, null, sessionConstraints.real),
				new Player(83, 12, 1, null, sessionConstraints.real),
				new Player(95, 12, 1, null, sessionConstraints.real),
				new Player(33, 13, 1, null, sessionConstraints.real),
				new Player(45, 13, 1, null, sessionConstraints.real),
				new Player(84, 13, 1, null, sessionConstraints.real),
				new Player(26, 14, 1, null, sessionConstraints.real),
				new Player(48, 14, 1, null, sessionConstraints.real),
				new Player(65, 14, 1, null, sessionConstraints.real),
				new Player(69, 14, 1, null, sessionConstraints.real),
				new Player(72, 14, 1, null, sessionConstraints.real),
				new Player(24, 15, 1, null, sessionConstraints.real),
				new Player(27, 15, 1, null, sessionConstraints.real),
				new Player(32, 15, 1, null, sessionConstraints.real),
				new Player(80, 15, 1, null, sessionConstraints.real),
				new Player(30, 16, 1, null, sessionConstraints.real),
				new Player(81, 16, 1, null, sessionConstraints.real),
				new Player(87, 16, 1, null, sessionConstraints.real),
				new Player(43, 17, 1, null, sessionConstraints.real),
				new Player(73, 17, 1, null, sessionConstraints.real),
				new Player(92, 17, 1, null, sessionConstraints.real),
				new Player(19, 18, 1, null, sessionConstraints.real),
				new Player(21, 18, 1, null, sessionConstraints.real),
				new Player(49, 18, 1, null, sessionConstraints.real),
				new Player(85, 18, 1, null, sessionConstraints.real),
				new Player(36, 19, 1, null, sessionConstraints.real),
				new Player(40, 19, 1, null, sessionConstraints.real),
				new Player(64, 19, 1, null, sessionConstraints.real),
				new Player(78, 19, 1, null, sessionConstraints.real)
		);

		public static List<Player> real() {
			List<Player> players = new ArrayList<>();
			players.addAll(real_3_4);
			players.addAll(real_5_7);
			players.addAll(real_8_18);
			players.addAll(real_adult);
			return players;
		}

		public static List<Player> real_3_4 = List.of(
				new Player(4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(4, 0, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real)
		);

		public static List<Player> real_5_7 = List.of(
				new Player(5, 1, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(5, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(5, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(5, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(6, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(7, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(7, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				new Player(7, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(7, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(7, 6, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real)
		);

		public static List<Player> real_8_18 = List.of(
				new Player(8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 7, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 6, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(9, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 13, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(9, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30))), sessionConstraints.real),
				new Player(10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(10, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(10, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(10, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(10, 14, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(10, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(10, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(11, 7, 3, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(11, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(11, 7, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				new Player(11, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(11, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30))), sessionConstraints.real),
				new Player(11, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				new Player(11, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(12, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(12, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(12, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(12, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				new Player(12, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(13, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(13, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(15, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 45), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(14, 30))), sessionConstraints.real),
				new Player(13, 9, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(13, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(13, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(14, 11, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(14, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(14, 9, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 31)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(14, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(14, 9, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 11, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 11, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(14, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(14, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(14, 10, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(14, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 9, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				new Player(15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(18, 30))), sessionConstraints.real),
				new Player(15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(15, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(15, 15, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				new Player(15, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(15, 12, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(15, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				new Player(15, 18, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(15, 13, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 16, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(15, 11, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(16, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(15, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(16, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(16, 15, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real),
				new Player(16, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(16, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30))), sessionConstraints.real),
				new Player(16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(16, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				new Player(17, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30))), sessionConstraints.real),
				new Player(17, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				new Player(17, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 14, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				new Player(17, 16, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				new Player(17, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(17, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(17, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(18, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(18, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(20, 31))), sessionConstraints.real),
				new Player(18, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				new Player(18, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(18, 17, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(18, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(18, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(21, 0))), sessionConstraints.real)
		);

		public static List<Player> real_adult = List.of(
				//new Player("DELAMARRE Emilie", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("FRASER Keely", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("LUX Guillaume", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("LUX Sophie", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 18, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 15), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				new Player(99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 16, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				new Player(99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 15), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 17, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(14, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				new Player(99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				new Player(99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				new Player(99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 16, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				new Player(99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				new Player(99, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real)
		);
	}
}