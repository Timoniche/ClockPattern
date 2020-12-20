package dulaev.events.statistic;

import lombok.Setter;

import java.time.Instant;

public class SetableClock implements Clock {
    @Setter private Instant now;

    public SetableClock(Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }
}
