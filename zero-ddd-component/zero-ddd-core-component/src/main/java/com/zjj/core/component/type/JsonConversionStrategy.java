package com.zjj.core.component.type;

import com.zjj.autoconfigure.component.json.JsonHelper;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public abstract class JsonConversionStrategy {

    private @Resource JsonHelper jsonHelper;

    public void check(String jsonString) {

    }

    public abstract @NonNull <T> T convert(String jsonString) throws Exception;
    public abstract @NonNull String getType();
}