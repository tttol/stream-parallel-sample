package parallel;

import repository.SampleRepository;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Parallel {
    private final Logger log = Logger.getLogger(Parallel.class.getName());
    private final SampleRepository sampleRepository = new SampleRepository();

    public void runParallel() {
        log.info("[START] runParallel");
        IntStream.range(0, 5).parallel().forEach(e -> {
            try {
                sleep(e);
            } catch (InterruptedException ex) {
                log.warning("[ABEND] runParallel");
                throw new RuntimeException(ex);
            }
        });
        log.info("[END] runParallel");
    }

    private void sleep(int num) throws InterruptedException {
        try {
            if (num == 3) {
                throw new ParallelFailedException("3 is invalid!!");
            }
            Thread.sleep(1000);
            log.info("process-%d is succeeded".formatted(num));
        } catch (Exception e) {
            log.warning("process-%d is failed.".formatted(num));
            sampleRepository.insertResultIntoDb(num);
        }
    }

    private static class ParallelFailedException extends RuntimeException {
        public ParallelFailedException(String message) {
            super(message);
        }
    }
}
