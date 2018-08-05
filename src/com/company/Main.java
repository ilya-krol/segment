package com.company;

import java.net.InetAddress;
import java.util.*;

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

    private static Optional<Integer> getValue(Vector<Segment> segments, int index) {
        Optional<Segment> segment = segments.stream().filter(item -> item.offset + item.duration - 1 >= index).findFirst();
        return segment.map(s -> s.value);
    }

    static private Optional<Integer> getValue2(Vector<Segment> segments, int index) {
        for (Segment item: segments) {
            if (item.offset + item.duration - 1 >= index) {
                return Optional.of(item.value);
            }
        }
        return Optional.empty();
    }

    static int calculateTotal(Vector<Segment> segments, int status) {
        int result = 0;
        for (int i = 0; i < SEGMENTS_COUNT * DURATION; i++) {
            Optional<Integer> v = getValue2(segments, i);
            if (v.isPresent() && v.get() == status) {
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
        Vector<Segment> segments = initSegments();
        long start = System.currentTimeMillis();
        int total = calculateTotal(segments, WORKED_STATUS);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("total: " + total);
        System.out.println("time duration in msec: " + elapsed);
    }
}
