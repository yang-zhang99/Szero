//package com.szero.system;
//
//
//import com.szero.system.controller.dto.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.annotations.BeforeClass;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//public class SysOpenLoginTests {
//
//    @Autowired
//    private Validator validator;
//
////    @BeforeClass
////    public static void setValidator() {
////        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
////        validator = factory.getValidator();
////    }
//
//    @Test
//    public void manufacturerIsNull() {
//
//
//        User user = new User();
//        user.setUsername("123");
//        user.setPassword("1");
//
//        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
//        System.out.println(constraintViolations);
//        assertEquals(1, constraintViolations.size());
//        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
//    }
//
//
//}
