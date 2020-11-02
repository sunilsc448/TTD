package com.example.ttd.Counter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FitnessTrackerTest {
    FitnessTracker SUT;
    @Before
    public void setUp() throws Exception {
        SUT = new FitnessTracker();
    }

    @Test
    public void step() {
        SUT.step();
        assertThat(SUT.getTotalSteps(), is(1));
    }

    @Test
    public void runStep() {
        SUT.runStep();
        assertThat(SUT.getTotalSteps(), is(2));
    }
}