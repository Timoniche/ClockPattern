package dulaev.events.statistic;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;

import static java.time.temporal.ChronoUnit.HOURS;

public class EventsStatisticRPM implements EventsStatistic {
    public static Double MINUTES_IN_HOUR = 60.0;
    public static int EXPIRATION_TIME_HOURS = 1;

    private final Clock clock;
    private final ArrayDeque<Pair<String, Instant>> queueEvents;
    private final HashMap<String, Integer> countEvents;

    public EventsStatisticRPM(Clock clock) {
        this.clock = clock;
        queueEvents = new ArrayDeque<>();
        countEvents = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        Instant curTime = clock.now();
        clearOldEvents(curTime);
        queueEvents.add(new Pair<>(name, curTime));
        countEvents.compute(name, (k, v) -> (v == null) ? 1 : v + 1);
    }

    @Override
    public Double getEventStatisticByName(String name) {
        clearOldEvents(clock.now());
        Integer eventCount = countEvents.getOrDefault(name, 0);
        return eventCount.doubleValue() / MINUTES_IN_HOUR;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        clearOldEvents(clock.now());
        return countEvents.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                v -> v.getValue().doubleValue() / MINUTES_IN_HOUR));

    }

    @Override
    public void printStatistic() {
        System.out.println(getAllEventStatistic());
    }

    private void clearOldEvents(Instant timeCall) {
        while (queueEvents.size() > 0 && lastIsExpired(timeCall)) {
            Pair<String, Instant> lastEvent = queueEvents.poll();
            assert lastEvent != null;
            String lastEventName = lastEvent.getKey();
            Integer lastEventCounts = countEvents.get(lastEventName);
            if (lastEventCounts == 1) {
                countEvents.remove(lastEventName);
            } else {
                countEvents.computeIfPresent(lastEventName, (k, v) -> v - 1);
            }

        }
    }

    private boolean lastIsExpired(Instant timeCall) {
        assert queueEvents.peek() != null;
        Instant lastEventTime = queueEvents.peek().getValue();
        return HOURS.between(lastEventTime, timeCall) >= EXPIRATION_TIME_HOURS;
    }

}
