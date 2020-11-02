package com.example.ttd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MyActivityTest {
    MyActivity SUT;

    @Before
    public void setup() throws Exception {
        SUT = new MyActivity();
    }

    @Test
    public void onStart_incrementsCountByOne() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        int result = SUT.getCount();
        // Assert
        assertThat(result, is(1));
    }
}