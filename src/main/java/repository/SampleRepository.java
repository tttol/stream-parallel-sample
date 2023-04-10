package repository;

import java.util.logging.Logger;

public class SampleRepository {
    private final Logger log = Logger.getLogger(SampleRepository.class.getName());

    public void insertResultIntoDb(int processNum) {
        log.info("insert into some_table.");
    }
}
