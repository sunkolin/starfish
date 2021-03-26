package com.starfish.validator;

import com.starfish.trial.Validator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * ValidatorToolTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-25
 */
public class ValidatorTest {

    @Test
    public void emailTest() {
        boolean result = true;
        try {
            Validator.validateEmail("starfish@qq.com", -1, "邮箱格式不正确");
        } catch (Exception e) {
            result = false;
        }
        assertTrue(result);
    }

}
