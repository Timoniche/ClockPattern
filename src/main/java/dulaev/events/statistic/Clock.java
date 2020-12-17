package dulaev.events.statistic;

import java.time.Instant;

public interface Clock {
    Instant now();
}
