package dulaev.events.statistic;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicSetableClock implements Clock {
    private AtomicReference<Instant> now;

    public AtomicSetableClock(Instant now) {
        this.now = new AtomicReference<Instant>(now);
    }

    public void setNow(Instant now) {
        this.now.set(now);
    }

    public void plus(TemporalAmount timeDelta) {
        now.updateAndGet(it -> it.plus(timeDelta));
    }

    public void minus(TemporalAmount timeDelta) {
        now.updateAndGet(it -> it.minus(timeDelta));
    }

    @Override
    public Instant now() {
        return now.get();
    }
}
