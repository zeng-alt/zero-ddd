//package com.zjj.security.core.component.supper;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.zjj.cache.component.config.UseTypeInfo;
//import com.zjj.security.core.component.domain.SecurityUser;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.cache.support.NullValue;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年12月10日 21:05
// */
//public class UserDeserializerCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
//    @Override
//    public void customize(Jackson2ObjectMapperBuilder builder) {
////        builder.mixIn(User.class, UseTypeInfo.class);
//        builder.deserializers(UserDeserializerCustomizer.UserDeserializer.INSTANCE);
//    }
//
//
//
////    @JsonDeserialize(using = UserDeserializer.class)
//    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//    public class UseTypeInfo {
//    }
//
//    public static class UserDeserializer extends StdDeserializer<SecurityUser> {
//
//        public static final UserDeserializer INSTANCE = new UserDeserializer();
//
//        protected UserDeserializer() {
//            super(SecurityUser.class);
//        }
//
//        @Override
//        public SecurityUser deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
//            System.out.println();
//            JsonNode node = p.getCodec().readTree(p);
//            String username = node.get("username").asText();
//            String password = node.get("password").asText();
//            boolean enabled = node.get("enabled").asBoolean();
//            boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
//            boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
//            boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            JsonNode authoritiesNode = node.get("authorities");
//            if (authoritiesNode != null && authoritiesNode.isArray()) {
//                for (JsonNode authorityNode : authoritiesNode) {
//                    authorities.add(new SimpleGrantedAuthority(authorityNode.asText()));
//                }
//            }
//            return new SecurityUser(username, password, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired, authorities);
//        }
//
//        @Override
//        public Class<SecurityUser> handledType() {
//            return SecurityUser.class;
//        }
//    }
//}
