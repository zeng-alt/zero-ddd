package com.zjj.main.domain.resource.http;

import com.zjj.main.domain.resource.http.cmd.StockInHttpResourceCmd;
import io.vavr.control.Option;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:23
 */
@Component
public record HttpResourceFactory() {

    public @NonNull Option<HttpResourceAgg> create(StockInHttpResourceCmd cmd) {
        return Option.none();
    }
}
