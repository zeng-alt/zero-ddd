package com.zjj.tenant.database.component;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.zaxxer.hikari.HikariDataSource;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 21:36
 */
public class TestLoadingCache {


    @Test
    public void test1() {

        HashMap<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("a1", "b1");
        map.put("a2", "b2");
        map.put("a3", "b3");


        LoadingCache<String, String> cache = Caffeine
                .newBuilder()
                .removalListener(
                        (RemovalListener<String, String>) (key, value, cause) -> {
                            System.out.println(value);
                        }
                ).build(map::get);


        Assertions.assertEquals("b", cache.get("a"));
    }
}
