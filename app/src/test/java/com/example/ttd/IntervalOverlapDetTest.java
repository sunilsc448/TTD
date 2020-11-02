package com.example.ttd;

import com.example.ttd.intervalCode.Interval;
import com.example.ttd.intervalCode.IntervalOverlapDetector;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IntervalOverlapDetTest {
    private IntervalOverlapDetector SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new IntervalOverlapDetector();
    }

    @Test
    public void isOverlapping_interval1BeforeInterval2_falseReturned() {
       Interval interval1 = new Interval(-1, 5);
       Interval interval2 = new Interval(6, 9);
       boolean result = SUT.isOverlapping(interval1, interval2);
       assertThat(result, is(false));
    }

    @Test
    public void isOverlapping_interval1AfterInterval2_falseReturned() {
        Interval interval1 = new Interval(6, 9);
        Interval interval2 = new Interval(-1, 5);
        boolean result = SUT.isOverlapping(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isOverlapping_interval1ContainsInterval2_trueReturned() {
        Interval interval1 = new Interval(1, 10);
        Interval interval2 = new Interval(3, 5);
        boolean result = SUT.isOverlapping(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isOverlapping_o_trueReturned() {
        Interval interval1 = new Interval(4, 6);
        Interval interval2 = new Interval(2, 3);
        boolean result = SUT.isOverlapping(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isOverlapping_interval1RightAdjacentInterval2_trueReturned() {
        Interval interval1 = new Interval(1, 5);
        Interval interval2 = new Interval(5, 8);
        boolean result = SUT.isOverlapping(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isOverlapping_interval1LeftAdjacentInterval2_trueReturned() {
        Interval interval1 = new Interval(1, 5);
        Interval interval2 = new Interval(-1, 1);
        boolean result = SUT.isOverlapping(interval1, interval2);
        assertThat(result, is(false));
    }
}