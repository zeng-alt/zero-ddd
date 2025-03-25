package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.HttpEntityStatus;
import com.zjj.autoconfigure.component.core.ResponseEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月14日 21:45
 */
public class ResponseEntityTest {

    @Test
    public void testResult() {
        HttpEntityStatus entity = ResponseEntity.of("1234");
        assertThat(entity)
                .isInstanceOf(ResponseEntity.class)
                .satisfies(e -> {
                    assertThat(e.getStatus()).isEqualTo(200);
                    assertThat(e.getBody())
                            .isInstanceOf(String.class)
                            .isEqualTo("1234");
                });
    }
}
