package com.example.ttd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PositiveNumberValidatorTest {
    PositiveNumberValidator SUT;

    @Before
    public void setup(){
        SUT = new PositiveNumberValidator();
    }

    @Test
    public void isPositive_negativeNumber_falseReturned() {
        boolean result = SUT.isPositive(-1);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isPositive_zero_falseReturned() {
        boolean result = SUT.isPositive(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isPositive_positive_trueReturned() {
        boolean result = SUT.isPositive(1);
        Assert.assertThat(result, is(true));
    }
}