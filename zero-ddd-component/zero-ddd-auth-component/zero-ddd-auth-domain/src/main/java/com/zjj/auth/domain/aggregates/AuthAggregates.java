//package com.zjj.auth.domain.aggregates;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.zjj.auth.domain.entities.GrantTypeAuth;
//import com.zjj.auth.domain.entities.TenantEntity;
//import com.zjj.auth.domain.valueobjects.ClientEntity;
//import com.zjj.component.exception.AuthException;
//import io.vavr.control.Option;
//import lombok.Data;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zengJiaJun
// * @crateTime 2024年06月17日 19:41
// * @version 1.0
// */
//@Data
//public class AuthAggregates {
//    private String uuid;
//    private ClientEntity clientEntity;
//    private TenantEntity tenantEntity;
//    private String code;
//    private GrantTypeAuth grantTypeAuth;
//
//    public void auth(String body) {
//        Gson gson = new GsonBuilder().create();
//        Map<String, String> map = gson.fromJson(body, HashMap.class);
//        Option.of(clientEntity).getOrElseThrow(() -> new AuthException("clientEntity is null")).check(map);
//        tenantEntity.check();
//        grantTypeAuth.check(map);
//    }
//}
