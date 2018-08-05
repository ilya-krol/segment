package com.company;

import java.util.Optional;
import java.util.Vector;

class Segment {
    int offset;
    int duration;
    int value;
    Segment(int offset, int duration, int value) {
        this.offset = offset;
        this.duration = duration;
        this.value = value;
    }
}

public class Main {

    static private final int WORKED_STATUS = 1;
    static private final int IDLE_STATUS = 0;

    static private final int SEGMENTS_COUNT = 10000;
    static private final int DURATION = 100;

    static private Optional<Integer> getValue(Vector<Segment> segments, int index) {
        Optional<Segment> segment = segments.stream().filter(item -> item.offset + item.duration - 1 >= index).findFirst();
        return segment.map(s -> s.value);
    }

    static private int calculateTotal(Vector<Segment> segments, int status) {
        int result = 0;
        for (int i = 0; i < SEGMENTS_COUNT * DURATION; i++) {
            Optional<Integer> v = Main.getValue(segments, i);
            if (v.isPresent() && v.get() == WORKED_STATUS) {
                result++;
            }
        }
        return result;
    }

    static private Vector<Segment> initSegments() {
        Vector<Segment> result = new Vector<>(SEGMENTS_COUNT);
        for (int i = 0; i < SEGMENTS_COUNT; i++) {
            if (i % 10 > 0) {
                result.add(new Segment(i * DURATION, DURATION, WORKED_STATUS));
            } else {
                result.add(new Segment(i * DURATION, DURATION, IDLE_STATUS));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Vector<Segment> segments = Main.initSegments();
        long start = System.currentTimeMillis();
        int total = Main.calculateTotal(segments, WORKED_STATUS);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("total: " + total);
        System.out.println("time duration in msec: " + elapsed);
    }
}
