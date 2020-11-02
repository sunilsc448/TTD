package com.example.ttd;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringReverserTest {

    private  StringReverser SUT;
    @Before
    public void setUp() throws Exception {
        //Adjust
        SUT = new StringReverser();
    }

    //1. if the string is empty - empty returned
    @Test
    public void reverse_empty_emptyStringReturned(){
        //ACT
        String result = SUT.reverse("");
        //Assert
        Assert.assertThat(result, is(""));
    }
    //2. if it is single char - same single char returned
    @Test
    public void reverse_singleChar_sameStringReturned(){
        String result = SUT.reverse("a");
        //Assert
        Assert.assertThat(result, is("a"));
    }
    //3. if long string - it should be reversed
    @Test
    public void reverse_lengthyString_stringReversed(){
        String result = SUT.reverse("paytm");
        //Assert
        Assert.assertThat(result, is("mtyap"));
    }

    @After
    public void tearDown() throws Exception {
        SUT = null;
    }
}