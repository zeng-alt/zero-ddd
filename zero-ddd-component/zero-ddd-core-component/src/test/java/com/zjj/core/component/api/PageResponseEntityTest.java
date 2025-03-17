package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.HttpEntityStatus;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月13日 13:50
 */
class PageResponseEntityTest {

    @Test
    void testOfNullableArray() {
//        CompletableFuture.failedFuture()

        PageResponseEntity<String> entity = PageResponseEntity.ofNullable((String[]) null);
        assertThat(entity)
                .satisfies(e -> {
                    assertThat(e.getStatusCode()).isEqualTo(404);
                    assertThat(e.getBody())
                            .satisfies(p -> {
                                assertThat(p.getPageNum()).isEqualTo(1);
                                assertThat(p.getPageSize()).isZero();
                                assertThat(p.getTotal()).isZero();
                                assertThat(p.getData())
                                        .isInstanceOf(Collection.class)
                                        .hasSize(0)
                                        .containsExactly();

                            });
                });
        entity = PageResponseEntity.ofNullable();
        assertThat(entity)
                .satisfies(e -> {
                    assertThat(e.getStatusCode()).isEqualTo(404);
                    assertThat(e.getBody())
                            .satisfies(p -> {
                                assertThat(p.getPageNum()).isEqualTo(1);
                                assertThat(p.getPageSize()).isZero();
                                assertThat(p.getTotal()).isZero();
                                assertThat(p.getData())
                                        .isInstanceOf(Collection.class)
                                        .hasSize(0)
                                        .containsExactly();

                            });
                });
        entity = PageResponseEntity.ofNullable("123");
        assertThat(entity)
                .satisfies(e -> {
                    assertThat(e.getStatusCode()).isEqualTo(200);
                    assertThat(e.getBody())
                            .satisfies(p -> {
                                assertThat(p.getPageNum()).isEqualTo(1);
                                assertThat(p.getPageSize()).isZero();
                                assertThat(p.getTotal()).isEqualTo(1L);
                                assertThat(p.getData())
                                        .isInstanceOf(Collection.class)
                                        .hasSize(1)
                                        .containsExactly("123");

                            });
                });

        entity = PageResponseEntity.ofNullable("123", "456");
        assertThat(entity)
                .satisfies(e -> {
                    assertThat(e.getStatusCode()).isEqualTo(200);
                    assertThat(e.getBody())
                            .satisfies(p -> {
                                assertThat(p.getPageNum()).isEqualTo(1);
                                assertThat(p.getPageSize()).isZero();
                                assertThat(p.getTotal()).isEqualTo(2L);
                                assertThat(p.getData())
                                        .isInstanceOf(Collection.class)
                                        .hasSize(2)
                                        .containsExactly("123", "456");

                            });
                });
    }

    @Test
    void testCreated() {
        PageResponseEntity<String> entity = PageResponseEntity.ok().page(1, 10).total(100L).data("11223");
        assertThat(entity).satisfies(e -> {
            assertThat(e.getStatusCode()).isEqualTo(200);
            assertThat(e.getBody()).satisfies(b -> {
                assertThat(b.getPageNum()).isEqualTo(1);
                assertThat(b.getPageSize()).isEqualTo(10);
                assertThat(b.getTotal()).isEqualTo(100L);
                assertThat(b.getData()).hasSize(1).containsExactly("11223");
            });
        });
    }

    @Test
    void testResult() {
        HttpEntityStatus entity = PageResponseEntity.of("1234");
        assertThat(entity)
                .isInstanceOf(PageResponseEntity.class)
                .satisfies(e -> {
                    assertThat(e.getStatusCode()).isEqualTo(200);
                    assertThat(e.getBody())
                            .isInstanceOf(PageEntity.class)
                            .extracting(p -> (PageEntity<Collection<String>>) p)
                            .satisfies(p -> {
                                assertThat(p.getPageNum()).isEqualTo(1);
                                assertThat(p.getPageSize()).isZero();
                                assertThat(p.getTotal()).isEqualTo(1L);
                                assertThat(p.getData())
                                        .isInstanceOf(Collection.class)
                                        .hasSize(1)
                                        .containsExactly("1234");

                            });
                });
    }
}
