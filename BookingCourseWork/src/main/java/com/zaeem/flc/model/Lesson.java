package com.zaeem.flc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lesson {

    public static final int CAPACITY = 4;

    private String lessonId;
    private int weekendNo;              // 1..8
    private Day day;                    // SATURDAY/SUNDAY
    private TimeSlot timeSlot;          // MORNING/AFTERNOON/EVENING
    private String exerciseType;        // Yoga, Zumba...
    private double price;

    private List<String> bookedMemberIds = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public Lesson(String lessonId, int weekendNo, Day day, TimeSlot timeSlot, String exerciseType, double price) {
        this.lessonId = lessonId;
        this.weekendNo = weekendNo;
        this.day = day;
        this.timeSlot = timeSlot;
        this.exerciseType = exerciseType;
        this.price = price;
    }

    public String getLessonId() { return lessonId; }
    public int getWeekendNo() { return weekendNo; }
    public Day getDay() { return day; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public String getExerciseType() { return exerciseType; }
    public double getPrice() { return price; }

    public List<String> getBookedMemberIds() {
        return Collections.unmodifiableList(bookedMemberIds);
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public boolean isFull() {
        return bookedMemberIds.size() >= CAPACITY;
    }

    // add member only if not duplicate and capacity available
    public boolean addMember(String memberId) {
        if (memberId == null || memberId.isBlank()) return false;
        if (bookedMemberIds.contains(memberId)) return false;
        if (isFull()) return false;
        bookedMemberIds.add(memberId);
        return true;
    }

    public boolean removeMember(String memberId) {
        return bookedMemberIds.remove(memberId);
    }

    public void addReview(Review review) {
        if (review == null) throw new IllegalArgumentException("Review cannot be null");
        reviews.add(review);
    }

    public double averageRating() {
        if (reviews.isEmpty()) return 0.0;
        int sum = 0;
        for (Review r : reviews) sum += r.getRating();
        return (double) sum / reviews.size();
    }

    @Override
    public String toString() {
        return lessonId + " | W" + weekendNo + " " + day + " " + timeSlot +
                " | " + exerciseType + " | £" + price +
                " | booked: " + bookedMemberIds.size() + "/" + CAPACITY;
    }
}
