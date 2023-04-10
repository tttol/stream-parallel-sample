package parallel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SampleRepository;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

public class ParallelTest {
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @InjectMocks
    private Parallel parallel;

    @Mock
    private SampleRepository sampleRepository;

    @Test
    @DisplayName("並列処理が直列処理よりも高速に処理できていること")
    void parallelTest() {
        parallel.runParallel();
        verify(sampleRepository, only()).insertResultIntoDb(anyInt());
    }
}
