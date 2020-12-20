import com.google.common.collect.Maps;
import dulaev.events.statistic.AtomicSetableClock;
import dulaev.events.statistic.Clock;
import dulaev.events.statistic.EventsStatisticRPM;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsStatisticRPMTest {

    @Test
    public void testInc() {
        Instant curTime = Instant.now();
        Clock clock = new AtomicSetableClock(curTime);
        EventsStatisticRPM rpm = new EventsStatisticRPM(clock);
        rpm.incEvent("aaa3");
        rpm.incEvent("bbb2");
        rpm.incEvent("aaa3");
        rpm.incEvent("ccc1");
        rpm.incEvent("bbb2");
        rpm.incEvent("aaa3");

        Map<String, Double> expected  = new HashMap<>() {{
            put("aaa3", 3 / 60.0);
            put("bbb2", 2 / 60.0);
            put("ccc1", 1 / 60.0);
        }};

        assertTrue(Maps.difference(expected, rpm.getAllEventStatistic()).areEqual());
    }

    @Test
    public void testGapHour() {
        Instant curTime = Instant.now();
        AtomicSetableClock clock = new AtomicSetableClock(curTime);
        EventsStatisticRPM rpm = new EventsStatisticRPM(clock);
        rpm.incEvent("init");
        clock.plus(Duration.ofHours(1));
        Map<String, Double> expected  = new HashMap<>();

        assertTrue(Maps.difference(expected, rpm.getAllEventStatistic()).areEqual());
    }

    @Test
    public void testGapHalfHour() {
        Instant curTime = Instant.now();
        AtomicSetableClock clock = new AtomicSetableClock(curTime);
        EventsStatisticRPM rpm = new EventsStatisticRPM(clock);
        rpm.incEvent("init");
        clock.plus(Duration.ofMinutes(30));
        Map<String, Double> expected  = new HashMap<>() {{
            put("init", 1 / 60.0);
        }};

        assertTrue(Maps.difference(expected, rpm.getAllEventStatistic()).areEqual());
    }
}
