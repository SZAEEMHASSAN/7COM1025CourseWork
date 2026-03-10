package com.zaeem.flc.model;

public class Booking {

    private String bookingId;
    private String memberId;
    private String lessonId;
    private BookingStatus status;

    public Booking(String bookingId, String memberId, String lessonId) {
        this.bookingId = bookingId;
        this.memberId = memberId;
        this.lessonId = lessonId;
        this.status = BookingStatus.BOOKED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public void markAttended() {
        this.status = BookingStatus.ATTENDED;
    }

    public void changeLesson(String newLessonId) {
        this.lessonId = newLessonId;
        this.status = BookingStatus.CHANGED;
    }
}