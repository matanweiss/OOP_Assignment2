import java.time.Instant;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    private Instant start;
    private Instant finish;

    @Test
    public void MyTest() {
        String[] a = Ex2_1.createTextFiles(20, 1, 10);
        start = Instant.now();
        logger.info(() -> String.valueOf(Ex2_1.getNumOfLines(a)));
        finish = Instant.now();
        logger.info(() -> "getNumOfLines: " + Duration.between(start, finish).toMillis());
        start = Instant.now();
        logger.info(() -> String.valueOf(Ex2_1.getNumOfLinesThreads(a)));
        finish = Instant.now();
        logger.info(() -> "getNumOfLinesThreads: " + Duration.between(start, finish).toMillis());
        start = Instant.now();
        logger.info(() -> String.valueOf(Ex2_1.getNumOfLinesThreadPool(a)));
        finish = Instant.now();
        logger.info(() -> "getNumOfLinesThreadPool: " + Duration.between(start, finish).toMillis());
    }
}
