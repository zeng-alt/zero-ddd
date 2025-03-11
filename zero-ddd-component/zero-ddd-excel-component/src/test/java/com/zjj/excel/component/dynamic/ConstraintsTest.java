package com.zjj.excel.component.dynamic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.excel.component.dynamic.constraints.SizeImpl;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Constructor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 17:16
 */
@SpringBootTest
public class ConstraintsTest {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private Validator validator;

    @Test
    public void testSize() throws JsonProcessingException {
        SizeImpl size = new SizeImpl("4", "5");
        String s = objectMapper.writeValueAsString(size);
        SizeImpl size1 = objectMapper.readValue(s, SizeImpl.class);
        SizeValidatorForCharSequence validator1 = new SizeValidatorForCharSequence();
        validator1.initialize(size1);
        Constructor<SizeImpl> resolvableConstructor = BeanUtils.getResolvableConstructor(SizeImpl.class);
        SizeImpl size2 = BeanUtils.instantiateClass(resolvableConstructor, "出错", "1", "2");
        System.out.println(validator1.isValid("123456789", null));

    }
}
