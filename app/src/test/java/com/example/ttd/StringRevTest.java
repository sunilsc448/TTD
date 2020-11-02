package com.example.ttd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringRevTest {

    StringReverser SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new StringReverser();
    }

    @Test
    public void reverse_emptyString_emptyStringReturned() throws Exception{
        String result = SUT.reverse("");
        Assert.assertThat(result, is(""));
    }

    @Test
    public void reverse_singleCharacter_sameStringReturned() throws Exception{
        String result = SUT.reverse("a");
        Assert.assertThat(result, is("a"));
    }

    @Test
    public void reverse_longString_reversedStringReturned() throws Exception{
        String result = SUT.reverse("paytm");
        Assert.assertThat(result, is("mtyap"));
    }
}