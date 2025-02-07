package well_tennis_club.timefold.solver.collectors;

import ai.timefold.solver.core.api.score.stream.uni.UniConstraintCollector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import well_tennis_club.timefold.domain.PlayerSessionLink;
import well_tennis_club.timefold.domain.Session;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SessionPerDayCollector implements UniConstraintCollector<PlayerSessionLink, List<Session>, List<DayOfWeek>> {
	@Override
	public @NonNull Supplier<List<Session>> supplier() {
		return ArrayList::new;
	}

	@Override
	public @NonNull BiFunction<List<Session>, PlayerSessionLink, Runnable> accumulator() {
		return (sessions, playerSessionLink) -> {
			Session session = playerSessionLink.getSession();
			sessions.add(session);
			return () -> sessions.remove(session);
		};
	}

	@Override
	public @Nullable Function<List<Session>, List<DayOfWeek>> finisher() {
		return sessions -> {
			List<DayOfWeek> days = new ArrayList<>();
			for (int i = 0; i < sessions.size() - 1; i++) {
				Session session = sessions.get(i);
				for (int j = i + 1; j < sessions.size(); j++) {
					Session otherSession = sessions.get(j);
					if (Objects.equals(session.getDay(), otherSession.getDay())) {
						days.add(session.getDay());
					}
				}
			}
			return days;
		};
	}
}
