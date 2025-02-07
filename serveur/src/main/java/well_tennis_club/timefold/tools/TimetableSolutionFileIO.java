package well_tennis_club.timefold.tools;

import ai.timefold.solver.jackson.impl.domain.solution.JacksonSolutionFileIO;

import well_tennis_club.timefold.domain.Timetable;

public class TimetableSolutionFileIO extends JacksonSolutionFileIO<Timetable> {
	public TimetableSolutionFileIO() {
		super(Timetable.class);
	}
}
