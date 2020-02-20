package com.example.augmentedhighereducationfortruckdrivers.Models;


import java.util.Date;

// Maps to users/userid/lessons/lesson in firestore
public class UserLessonData {
    private Date lastUpdated;
    private String name;
    private Long score;

    public UserLessonData() { }

    public UserLessonData(Date lastUpdated, String name, Long score) {
        this.name = name;
        this.score = score;
        this.lastUpdated = lastUpdated;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public Long getScore() { return score; }

    public void setScore(Long score) { this.score = score; }

    public Date getLastUpdated() { return lastUpdated; }

    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}
