package com.example.ttd.NameValidator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserInputValidatorTest {
    UserInputValidator SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new UserInputValidator();
    }

    @Test
    public void isValidFullName_validFullName_trueReturned() throws Exception{
        boolean isValid = SUT.isValidFullName("valid full name");
        assertThat(isValid, is(true));
    }

    @Test
    public void isValidFullName_invalidFullName_trueReturned() throws Exception{
        boolean isValid = SUT.isValidFullName("");
        assertThat(isValid, is(false));
    }

    @Test
    public void isValidUserName_validUserName_trueReturned() throws Exception{
        boolean isValid = SUT.isValidUsername("userName");
        assertThat(isValid, is(true));
    }

    @Test
    public void isValidUserName_invalidUserName_trueReturned() throws Exception{
        boolean isValid = SUT.isValidUsername("");
        assertThat(isValid, is(false));
    }
}