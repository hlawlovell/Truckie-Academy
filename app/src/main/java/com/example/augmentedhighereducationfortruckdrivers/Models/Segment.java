package com.example.augmentedhighereducationfortruckdrivers.Models;

import java.util.List;

public class Segment {
    public String segmentText;
    public List<Question> questions;

    public Segment(String segmentText, List<Question> questions) {
        this.segmentText = segmentText  ;
        this.questions = questions;
    }
}
