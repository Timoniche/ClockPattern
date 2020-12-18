package dulaev.events.statistic;

import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;

public class EventsStatisticRPM implements EventsStatistic {
    public static Double minutesInHour = ((Long)HOURS.getDuration().toMinutes()).doubleValue();

    @Override
    public void incEvent(String name) {

    }

    @Override
    public Double getEventStatisticByName(String name) {
        return null;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        return null;
    }

    @Override
    public void printStatistic() {

    }
}
