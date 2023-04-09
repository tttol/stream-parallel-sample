import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelTest {
    private final Logger log = Logger.getLogger(ParallelTest.class.getName());

    @Test
    @DisplayName("並列処理が直列処理よりも高速に処理できていること")
    void parallelTest() {
        assertThat(runSerial() - runParallel()).isGreaterThan(0);
    }

    private long runParallel() {
        var startTime = System.currentTimeMillis();
        log.info("[START] runParallel[%d]".formatted(startTime));

        IntStream.range(0, 10).parallel().forEach(e -> {
            try {
                sleep(e);
            } catch (InterruptedException ex) {
                log.warning("[ABEND] runParallel");
                throw new RuntimeException(ex);
            }
        });

        var executionTime = System.currentTimeMillis() - startTime;
        log.info("[END] runParallel [%d] milli sec".formatted(executionTime));

        return executionTime;
    }

    private long runSerial() {
        var startTime = System.currentTimeMillis();
        log.info("[START] runSerial");

        IntStream.range(0, 5).forEach(e -> {
            try {
                sleep(e);
            } catch (InterruptedException ex) {
                log.warning("[ABEND] runSerial");
                throw new RuntimeException(ex);
            }
        });

        var executionTime = System.currentTimeMillis() - startTime;
        log.info("[END] runSerial [%d] milli sec".formatted(executionTime));

        return executionTime;
    }

    private void sleep(int num) throws InterruptedException {
        log.info("process-%d".formatted(num));
        Thread.sleep(1000);
    }
}
