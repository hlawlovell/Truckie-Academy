package com.example.augmentedhighereducationfortruckdrivers.Models;

import java.util.List;

public class Lesson {
    public String name;
    public List<Segment> segments;


    public Lesson(String name, int amountSegments, List<Segment> segments) {
        this.name = name;
        this.segments = segments;
    }

}

