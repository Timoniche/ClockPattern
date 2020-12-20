package dulaev.events.statistic;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant curTime = Instant.now();
        Clock clock = new AtomicSetableClock(curTime);
        EventsStatisticRPM rpm = new EventsStatisticRPM(clock);
        rpm.incEvent("aaa");
        rpm.incEvent("bbb");
        rpm.incEvent("aaa");
        rpm.printStatistic();
    }
}

