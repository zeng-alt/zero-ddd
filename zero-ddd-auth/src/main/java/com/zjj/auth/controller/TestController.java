//package com.zjj.auth.controller;
//
//import com.zjj.autoconfigure.component.l2cache.L2Cache;
//import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年10月16日 09:27
// */
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @Autowired
//    private L2CacheManage l2CacheManage;
//
//    @GetMapping("put/{key}/{value}")
//    public void put(@PathVariable String key, @PathVariable String value) {
//        L2Cache<Object, Object> user = l2CacheManage.getL2Cache("user");
//        user.put(key, value);
//    }
//
//
//    @GetMapping("get/{key}")
//    public String get(@PathVariable String key) {
//        L2Cache<Object, Object> user = l2CacheManage.getL2Cache("user");
//        return user.get(key, String.class);
//    }
//
//}
