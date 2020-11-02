package com.example.ttd.intervalCode;

import com.example.ttd.intervalCode.Interval;

public class IntervalOverlapDetector {
    //Interval(-1, 5), Interval2(6, 8)
    public boolean isOverlapping(Interval interval1, Interval interval2 ){
        return interval1.getEnd() > interval2.getStart() &&  interval1.getStart() < interval2.getEnd();
    }
}
