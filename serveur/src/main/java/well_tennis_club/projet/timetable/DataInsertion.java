package well_tennis_club.projet.timetable;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import well_tennis_club.data_converter.to_entity.CourtEntityConverter;
import well_tennis_club.data_converter.to_entity.PlayerEntityConverter;
import well_tennis_club.data_converter.to_entity.SessionConstraintEntityConverter;
import well_tennis_club.data_converter.to_entity.TrainerEntityConverter;
import well_tennis_club.projet.court.CourtEntity;
import well_tennis_club.projet.court.CourtService;
import well_tennis_club.projet.player.PlayerEntity;
import well_tennis_club.projet.player.PlayerService;
import well_tennis_club.projet.sessionConstraint.SessionConstraintEntity;
import well_tennis_club.projet.sessionConstraint.SessionConstraintService;
import well_tennis_club.projet.trainer.TrainerEntity;
import well_tennis_club.projet.trainer.TrainerService;
import well_tennis_club.timefold.data_structure.SessionConstraint;
import well_tennis_club.timefold.data_structure.Timeslot;
import well_tennis_club.timefold.data_structure.ValueRange;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public class DataInsertion {
	public static class sessionConstraints {
		public static List<SessionConstraintEntity> real = Stream.of(
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
		).map(SessionConstraintEntityConverter::from).toList();
	}

	public static class trainers {
		public static List<TrainerEntity> small = List.of(
				TrainerEntityConverter.from("trainer", "1", new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false),
				TrainerEntityConverter.from("trainer", "2", new ValueRange(0, 18), new ValueRange(0, 10), new ValueRange(0, 40), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))
				), false)
		);

		public static List<TrainerEntity> real_adult = List.of(
				TrainerEntityConverter.from("BRANDT", "Pierre", new ValueRange(1, 99), new ValueRange(14, 19), new ValueRange(1620, 1620), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 30))
				), true),
				TrainerEntityConverter.from("HOUTMANN", "Bastien", new ValueRange(1, 99), new ValueRange(1, 19), new ValueRange(1680, 1680), List.of(
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true),
				TrainerEntityConverter.from("DESSOY", "Pierre", new ValueRange(1, 99), new ValueRange(1, 15), new ValueRange(600, 600), List.of(
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(22, 30)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(22, 30))
				), true),
				TrainerEntityConverter.from("HOMBOURGER", "Malo", new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(21, 0), LocalTime.of(22, 30))
				), false),
				TrainerEntityConverter.from("BODOT", "Lucie", new ValueRange(1, 99), new ValueRange(1, 13), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true),
				TrainerEntityConverter.from("MONTAGNE", "Nicolas", new ValueRange(1, 99), new ValueRange(1, 14), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(21, 0)),
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(21, 0))
				), true)
		);

		public static List<TrainerEntity> real_child = List.of(
				TrainerEntityConverter.from("HOMBOURGER", "Solal", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))
				), false),
				TrainerEntityConverter.from("DOYEN", "Manon", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))
				), true),
				TrainerEntityConverter.from("MARIE", "Maxence", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(0, 2400), List.of(
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))
				), false),
				TrainerEntityConverter.from("ADLINE", "Thomas", new ValueRange(1, 18), new ValueRange(1, 12), new ValueRange(300, 420), List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)),
						new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))
				), true)
		);

		public static List<TrainerEntity> real() {
			List<TrainerEntity> trainers = new ArrayList<>();
			trainers.addAll(real_adult);
			trainers.addAll(real_child);
			return trainers;
		}
	}

	public static class tennisCourts {
		public static List<CourtEntity> small = List.of(
				CourtEntityConverter.from("Terrain 1", List.of(
						new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(14, 0))
				))
		);

		public static List<CourtEntity> medium = List.of(
				CourtEntityConverter.from("Terrain 1", timeslot.medium.group_1),
				CourtEntityConverter.from("Terrain 2", timeslot.medium.group_1)
		);

		public static List<CourtEntity> real = List.of(
				CourtEntityConverter.from("Terrain 1", timeslot.large.group_1),
				CourtEntityConverter.from("Terrain 2", timeslot.large.group_2),
				CourtEntityConverter.from("Terrain 3", timeslot.large.group_2)
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
		public static List<PlayerEntity> small = List.of(
				PlayerEntityConverter.from("player", "1", 25, 9, 1, timeslot.small.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "2", 25, 9, 1, timeslot.small.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "3", 25, 9, 1, timeslot.small.group_2, sessionConstraints.real)
		);

		public static List<PlayerEntity> medium = List.of(
				PlayerEntityConverter.from("player", "1", 8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "2", 8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "3", 8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "4", 8, 5, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "5", 8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "6", 8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "7", 8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "8", 8, 5, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "9", 13, 7, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "10", 12, 7, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "11", 11, 6, 1, timeslot.medium.group_2, sessionConstraints.real),
				PlayerEntityConverter.from("player", "12", 16, 3, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "13", 17, 2, 1, timeslot.medium.group_1, sessionConstraints.real),
				PlayerEntityConverter.from("player", "14", 17, 3, 1, timeslot.medium.group_1, sessionConstraints.real)
		);

		public static List<PlayerEntity> large = List.of(
				PlayerEntityConverter.from("player", "52", 76, 2, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "91", 86, 2, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "34", 88, 2, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "71", 61, 3, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "19", 65, 3, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "87", 90, 3, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "42", 91, 3, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "83", 31, 4, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "25", 48, 4, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "46", 50, 4, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "56", 63, 4, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "69", 68, 4, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "75", 35, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "17", 41, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "50", 55, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "29", 77, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "61", 79, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "94", 82, 5, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "67", 22, 6, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "79", 23, 6, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "58", 38, 6, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "33", 42, 6, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "89", 71, 6, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "74", 57, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "22", 58, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "63", 60, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "40", 66, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "54", 89, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "96", 92, 7, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "35", 37, 8, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "20", 39, 8, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "77", 46, 8, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "66", 54, 8, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "48", 70, 8, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "60", 28, 9, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "81", 51, 9, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "28", 56, 9, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "39", 59, 9, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "15", 25, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "30", 29, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "64", 32, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "41", 34, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "98", 39, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "85", 44, 10, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "51", 20, 11, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "80", 67, 11, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "97", 70, 11, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "38", 75, 11, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "26", 93, 11, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "95", 47, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "36", 53, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "86", 62, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "72", 74, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "23", 83, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "59", 95, 12, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "53", 33, 13, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "62", 45, 13, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "82", 84, 13, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "55", 26, 14, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "43", 48, 14, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "10", 65, 14, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "76", 69, 14, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "21", 72, 14, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "37", 24, 15, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "88", 27, 15, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "16", 32, 15, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "68", 80, 15, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "27", 30, 16, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "45", 81, 16, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "78", 87, 16, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "92", 43, 17, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "65", 73, 17, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "49", 92, 17, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "73", 19, 18, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "24", 21, 18, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "90", 49, 18, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "57", 85, 18, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "47", 36, 19, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "70", 40, 19, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "31", 64, 19, 1, null, sessionConstraints.real),
				PlayerEntityConverter.from("player", "84", 78, 19, 1, null, sessionConstraints.real)
		);

		public static List<PlayerEntity> real() {
			List<PlayerEntity> players = new ArrayList<>();
			players.addAll(real_3_4);
			players.addAll(real_5_7);
			players.addAll(real_8_18);
			players.addAll(real_adult);
			return players;
		}

		public static List<PlayerEntity> real_3_4 = List.of(
				PlayerEntityConverter.from("CONART", "Gabriel", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("OGBALET", "Salomon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RAPENNE", "Léon", 4, 0, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real)
		);

		public static List<PlayerEntity> real_5_7 = List.of(
				PlayerEntityConverter.from("BADJI", "Maylis", 5, 1, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLUZAT", "Luke", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("KREMPF", "Marius", 5, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEGER", "Céleste", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("OKOLI", "Uchechukwu Angel", 5, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ROTATINTI", "Noé", 5, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("THEDENAT", "Marius", 5, 1, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("FLESCHEN", "Léo", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GODON", "Mahé", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MANTE", "Marceau", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SELTON", "Castille", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TOUTAIN", "Raphael", 6, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("WEBERT", "JUSSY Hugo", 6, 2, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("WINGERTER", "Lilas", 6, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BENNEOUALA", "Neal", 7, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("EVRARD", "Maxence", 7, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("HSSINE", "Khayyem", 7, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEGER", "Jade", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RABEHI", "Charlotte", 7, 2, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SCHWITZER", "Valentin", 7, 6, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TALLOTTE", "Rose", 7, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real)
		);

		public static List<PlayerEntity> real_8_18 = List.of(
				PlayerEntityConverter.from("BADJI", "Ines", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BARBARY", "Gaspard", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CAMUS", "Noé", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("HILSELBERGER", "Arthur", 8, 3, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MEILHAC", "Capucine", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("NGUEMO", "Salomé", 8, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("NUNGE", "Martin", 8, 5, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PINNA MEGE", "Charlie", 8, 3, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VERBRUGGHE", "Roxane", 8, 7, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("VOLODIMER", "César", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VOLODIMER", "Martin", 8, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ADJAL", "Martin", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ATLANI", "Tom", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BENNEOUALA", "Liam", 9, 6, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BENZINA", "Ibrahim", 9, 5, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BERTET", "Max - Alexandre", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BINAUD", "Marius", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLUZAT", "Evan", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BOIREAU", "Blanche", 9, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRUNSTEIN HUGUENIN", "Oana", 9, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("COURVOISIER", "Cloé", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LAFARGE", "LAURENT Jasmin", 9, 5, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LECOANET", "Baptiste", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEVACHER BICHWILLER", "Victor", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MAGISSON-KLEIN", "Amos", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MANTE", "Gabin", 9, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MASSEHIAN", "Aristide", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MASSEHIAN", "Léonard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RAGOT", "Arthur", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("REMY", "Yvann", 9, 13, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SCHWARTZ", "Antoine", 9, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("STANOWSKI", "Gaspard", 9, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VALETTE", "Léandre", 9, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BARBARY", "Louise", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CARRÉ", "Lana", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("FERRY", "Sacha", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("FLESCHEN", "Liam", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GANSMÜLLER", "Théodore", 10, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GEORGE", "Louis", 10, 5, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GUERIN", "Auguste", 10, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LAUMONT KNAUF", "Andreas", 10, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("LEHMANN", "Ferdinand", 10, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("STEPANIAN", "Léonard", 10, 5, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TALLOTTE", "Léon", 10, 14, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TATOPOULOS", "Achille", 10, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VERBRUGGHE", "Anna", 10, 7, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("WINGERTER", "Côme", 10, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ZEYER", "Matthieu", 10, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BARREAULT", "Nikita", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BERTHE", "Mathis", 11, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLANCHARD", "Roland", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEDERICHS", "Achille", 11, 7, 3, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GODON", "Anna", 11, 4, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GREGOIRE THOMAS", "Elias", 11, 7, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("JEANNOT", "Tom", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("KOCH", "Raphaella", 11, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("L'HUILLIER", "Anatole", 11, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LOYE", "Clément", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MARIA", "Gaspard", 11, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MARNE", "HEINRICH Lino", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MICHAUX", "Paul", 11, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("NGUEMO", "Ava", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PALMERO SOLER", "Mathéo", 11, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(19, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PELLISSIER BLIQUE", "Victor", 11, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RAYMOND", "Mael", 11, 7, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RICHARD", "Achille", 11, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SAKANYAN", "Narek", 11, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SIMONET", "Charlotte", 11, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SKALLI", "Ziam", 11, 7, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VOYDEVILLE", "Margot", 11, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CZESTOCHOWSKI", "Daniel", 12, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GRENOT", "Alexis", 12, 6, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEMOINE", "Laure-Line", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PALMERO SOLER", "Timothé", 12, 10, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PARIS", "Hector", 12, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RICHARD", "Ninon", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ROQUES CARMES", "Louis", 12, 9, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("STEPANIAN", "Louis", 12, 7, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TILLY", "Gabriel", 12, 6, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRIMONT", "Alice", 13, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRUNSTEIN HUGUENIN", "Noam", 13, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(15, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 45), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(14, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DEDERICHS", "Arsène", 13, 9, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VOYDEVILLE", "Emile", 13, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("WEBERT JUSSY", "Paul", 13, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("BLANCHARD", "Audrey", 14, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRANDENBERG", "Arthur", 14, 11, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CUNY", "Jules", 14, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEDERICHS", "Vadim", 14, 9, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(19, 31)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEDERICHS", "Marin", 14, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DELLESTABLE", "Thibault", 14, 9, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("FIXE-MARTZ", "Arthur", 14, 10, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GABRIEL MASSEHIAN", "Louis", 14, 11, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HUTTIN", "Célestine", 14, 11, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("JACQUES", "Léo", 14, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("L'HUILLIER", "Pacôme", 14, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LUX", "Victor", 14, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MC CALLA", "Raphael", 14, 10, 1, List.of(new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PAVANI", "Gabriel", 14, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SIMIER", "Edouard", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SMEJKAL", "Louise", 14, 7, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BERNAY", "Simon", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CINQUALBRE", "Edouard", 15, 11, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("COLLIN", "Roméo", 15, 9, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(18, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEDERICHS", "Camille", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEREINE-CHARRON", "Luce", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(18, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DUBOIS", "Camille", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DUBOIS", "Ethan", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("EL ADSSI", "Rayan", 15, 9, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GELLRICH", "Lyam", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GEOFFROY", "Constant", 15, 15, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GUERIN", "Arthur", 15, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("HUGUIN", "Amélien", 15, 8, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("KOCH", "Charles", 15, 11, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MALGRAS", "Clovis", 15, 12, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MALGRAS", "Gaspard", 15, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MARCHAND", "Ilona", 15, 18, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MORTIER", "Axel", 15, 13, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PARIS", "Octave", 15, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PIERLOT", "Achille", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SCHAACK", "Roman", 15, 16, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SKALLI", "Nael", 15, 11, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TARTE", "Zélie", 15, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TREVIS", "Jean", 15, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRUNIER", "Noé", 16, 15, 3, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(15, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DORY", "Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DROUILLY WENGER", "Estebane", 16, 10, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("FIEVET-LESSER", "Hector", 16, 15, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(13, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HOMBOURGER", "Paloma", 16, 12, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("KARM DELAUNAY", "Emy", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEDROIT", "Hector", 16, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MORTIER", "Victor", 16, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(20, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("RADENKOVIK", "Margaux", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SIMEONIN", "Judith", 16, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SIMIER", "Margot", 16, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BIDAUD-LARCAT", "Victoire", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLIN", "Louison", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRIMONT", "Camille", 17, 6, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(19, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DELLESTABLE", "Alice", 17, 10, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("EVE", "Grégoire", 17, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("FINANCE", "Sacha", 17, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HAUSBERGER", "Hugo", 17, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("KADEM", "Eva", 17, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LIGIBEL", "Elsa", 17, 14, 2, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MICHAUX", "Baptiste", 17, 16, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("REITER", "Maxime", 17, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RODERMANN", "Georges", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 30), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(13, 30), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SOBAGA", "Adèle", 17, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VAUTRIN-DEHOVE", "Hermione", 17, 11, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VIAL", "Jules", 17, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VIGNERON", "Théo", 17, 14, 2, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("CHANDECLERC", "Alois", 18, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DECRAENE", "Léo", 18, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(20, 31)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(19, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(20, 31))), sessionConstraints.real),
				PlayerEntityConverter.from("DIAS", "Rafael", 18, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(20, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GRENOT", "Martin", 18, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HERVE", "Clémence", 18, 17, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PIA", "Ruben", 18, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("SCHWEITZER", "Bastien", 18, 13, 2, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(21, 0))), sessionConstraints.real)
		);

		public static List<PlayerEntity> real_adult = List.of(
				//new Player("DELAMARRE Emilie", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(15, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("FRASER Keely", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("LUX Guillaume", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				//new Player("LUX Sophie", 99, 13, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(12, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ADLINE", "Thomas", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("ALEXANDRE", "Odin", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("ANTONY", "Nathalie", 99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BASTIAN", "Alexis", 99, 18, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 15), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 15), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("BELLINA", "Léa", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLAIN", "Tristan", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLANCO", "Ivhonne", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLIN", "Merlin", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BLUZAT", "Emeric", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("BRUMBT", "Anaïs", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 45), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("BRUSCHI", "Julien", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DAFONSECA", "Hugo", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DEBRABANT", "Camille", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(13, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DECKER", "Bénédicte", 99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("DESTRACQUE", "Lina", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DIAS", "Carlos", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("DOYEN", "Manon", 99, 17, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("EVRARD", "Antoine", 99, 14, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("FERRARO", "Mathilde", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GERIN", "JeanYves", 99, 16, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(21, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GUIRAUD", "Thomas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("GORCHON", "Djeyda", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("GORCHON", "Jon", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("HERVE", "Yann", 99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("HERVO", "Clémence", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HOMBOURGER", "Malo", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("HOMBOURGER", "Solal", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("JUMEAUX", "Tom", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("JUSSY", "Elodie", 99, 12, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 15), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("KIRCHER", "Nicolas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("LALOT", "Jean-Marc", 99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("LANGLOIS", "Jordan", 99, 17, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("LAUMONT", "Olivier", 99, 14, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LEBEE", "Matthieu", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("LECLERE", "Justine", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LELONG", "Faustine", 99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.SATURDAY, LocalTime.of(14, 0), LocalTime.of(17, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("LOUIS", "Benjamin", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MARCHAL", "Tita", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MARCHAL", "Patrice", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 15), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MARCHAND", "Alexis", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MARIE", "Maxence", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MARTI", "Nathalie", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(20, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MAURY", "Mathieu", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MAYER", "Margaux", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MICHEL", "Aurélie", 99, 13, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MICHEL", "JeanCarl", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MONTAGNE", "Catherine", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("MONTAGNE", "Nicolas", 99, 15, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("MORTIER", "Géraldine", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(21, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 0), LocalTime.of(21, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("OLIVETTI", "Célia", 99, 13, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("PAULY", "Guillaume", 99, 16, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PAYON", "Eliott", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PERON", "Benoit", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("PIETTE", "Cléa", 99, 11, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("RAUCH", "Victor", 99, 16, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("RODERMANN", "Jean", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("ROQUES-CARMES", "Thibault", 99, 16, 1, List.of(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("ROUGET", "Jean-Hugo", 99, 18, 1, List.of(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(20, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("STOLL", "Hugo", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TESTA", "Muriel", 99, 14, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("THOMASSIN", "Lucas", 99, 15, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("TOUTAIN", "Anne-Cécile", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TOUTAIN", "Jean-Baptiste", 99, 12, 1, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("TOURNADRE", "Claire", 99, 12, 1, List.of(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(20, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("VIAL", "Florence", 99, 15, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 0), LocalTime.of(22, 0))), sessionConstraints.real),
				PlayerEntityConverter.from("WEBER", "Quentin", 99, 18, 2, List.of(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(18, 30), LocalTime.of(22, 30)), new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(18, 30), LocalTime.of(22, 30))), sessionConstraints.real),
				PlayerEntityConverter.from("WEBERT", "David", 99, 14, 1, List.of(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0), LocalTime.of(22, 0)), new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(19, 30), LocalTime.of(22, 0))), sessionConstraints.real)
		);
	}
}