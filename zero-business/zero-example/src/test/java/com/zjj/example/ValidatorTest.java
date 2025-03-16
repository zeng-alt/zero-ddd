package com.zjj.example;

import com.zjj.example.entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月24日 14:42
 */
@SpringBootTest
public class ValidatorTest {


    @Autowired
    private Validator validator;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Test
    public void validaTest() {
        User user = new User();
        user.setName("A"); // 设置无效的名字
        user.setAge(30);    // 设置有效的年龄
        NullValidator nullValidator = new NullValidator();
//        validator.validateObject()
//        validator.va
//        validator.validateValue(User.class, "id", "A", NullValidator.class);
//        Validator validator = validator.getValidator();
//        ValidationUtils.invokeValidator( validator, user, new SimpleErrors(user, "user"));
        Set<ConstraintViolation<User>> validate = validator.validate(user);


        for (ConstraintViolation<User> userConstraintViolation : validate) {
            System.out.println(userConstraintViolation);
            System.out.println(messageSourceAccessor.getMessage(userConstraintViolation.getMessageTemplate(), new Object[]{0, 20}));
        }

//        Errors errors = validator.validateObject(user);

        System.out.println();
    }
}
