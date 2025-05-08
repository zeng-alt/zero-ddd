package com.zjj.excel.component.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.extensions.excel.streaming.StreamingXlsxItemReader;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月14日 09:47
 */
public class TestSpringBatchExcel {

    @Test
    public void testStreamingXlsxItemReader() {
        StreamingXlsxItemReader reader = new StreamingXlsxItemReader();
        JobLauncher jobLauncher;
    }
}
