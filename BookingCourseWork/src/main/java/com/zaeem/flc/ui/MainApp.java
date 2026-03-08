package com.zaeem.flc.ui;

import com.zaeem.flc.model.*;

public class MainApp {
    public static void main(String[] args) {

        Lesson lesson = new Lesson("W1-SAT-MOR-YOGA", 1, Day.SATURDAY, TimeSlot.MORNING, "Yoga", 10.0);

        System.out.println(lesson);
        System.out.println("Add M01: " + lesson.addMember("M01"));
        System.out.println("Add M01 again (duplicate): " + lesson.addMember("M01"));

        lesson.addReview(new Review(5, "Great"));
        lesson.addReview(new Review(4, "Nice"));

        System.out.println("Average rating: " + lesson.averageRating());
        System.out.println(lesson);
    }
}
