package well_tennis_club.timefold.application;

import ai.timefold.solver.benchmark.api.PlannerBenchmark;
import ai.timefold.solver.benchmark.api.PlannerBenchmarkFactory;

public class BenchmarkApp {
	public static void main(String[] args) {
		PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource(
				"benchmarkConfig.xml");

		PlannerBenchmark benchmark = benchmarkFactory.buildPlannerBenchmark();
		benchmark.benchmarkAndShowReportInBrowser();
	}
}
