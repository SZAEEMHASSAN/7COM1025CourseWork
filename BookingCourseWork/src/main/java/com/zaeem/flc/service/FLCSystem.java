package com.zaeem.flc.service;

import com.zaeem.flc.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class FLCSystem {

    private final Map<String, Member> members = new HashMap<>();
    private final Map<String, Lesson> lessons = new HashMap<>();
    private final Map<String, Booking> bookings = new HashMap<>();

    private int bookingCounter = 1;

    private int attendedCountForLesson(String lessonId) {
        int count = 0;
        for (Booking b : bookings.values()) {
            if (b.getLessonId().equals(lessonId) && b.getStatus() == BookingStatus.ATTENDED) {
                count++;
            }
        }
        return count;
    }

    public FLCSystem(List<Member> memberList, List<Lesson> lessonList) {
        for (Member m : memberList) members.put(m.getMemberId(), m);
        for (Lesson l : lessonList) lessons.put(l.getLessonId(), l);
    }

    // --- Timetable views ---
    public List<Lesson> viewByDay(Day day) {
        return lessons.values().stream()
                .filter(l -> l.getDay() == day)
                .sorted(Comparator.comparingInt(Lesson::getWeekendNo).thenComparing(Lesson::getTimeSlot))
                .collect(Collectors.toList());
    }

    public List<Lesson> viewByExercise(String exerciseType) {
        return lessons.values().stream()
                .filter(l -> l.getExerciseType().equalsIgnoreCase(exerciseType))
                .sorted(Comparator.comparingInt(Lesson::getWeekendNo).thenComparing(Lesson::getDay).thenComparing(Lesson::getTimeSlot))
                .collect(Collectors.toList());
    }

    // --- Booking ---
    public Booking bookLesson(String memberId, String lessonId) {
        Member member = members.get(memberId);
        Lesson lesson = lessons.get(lessonId);

        if (member == null || lesson == null) return null;

        // duplicate booking not allowed (same member + same lesson)
        boolean duplicate = bookings.values().stream()
                .anyMatch(b -> b.getMemberId().equals(memberId)
                        && b.getLessonId().equals(lessonId)
                        && b.getStatus() != BookingStatus.CANCELLED);

        if (duplicate) return null;

        // capacity check
        if (!lesson.addMember(memberId)) return null;

        String bookingId = "B" + String.format("%04d", bookingCounter++);
        Booking booking = new Booking(bookingId, memberId, lessonId);
        bookings.put(bookingId, booking);
        return booking;
    }

    // --- Change booking (keep same booking ID) ---
    public boolean changeBooking(String bookingId, String newLessonId) {
        Booking booking = bookings.get(bookingId);
        Lesson newLesson = lessons.get(newLessonId);

        if (booking == null || newLesson == null) return false;
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) return false;

        String memberId = booking.getMemberId();
        String oldLessonId = booking.getLessonId();
        Lesson oldLesson = lessons.get(oldLessonId);

        // try add to new lesson first (capacity + duplicate)
        if (!newLesson.addMember(memberId)) return false;

        // release old seat
        if (oldLesson != null) oldLesson.removeMember(memberId);

        booking.changeLesson(newLessonId);
        return true;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return false;
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) return false;

        Lesson lesson = lessons.get(booking.getLessonId());
        if (lesson != null) lesson.removeMember(booking.getMemberId());

        booking.cancel();
        return true;
    }

    public boolean attendLesson(String bookingId, int rating, String comment) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return false;
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.ATTENDED) return false;

        Lesson lesson = lessons.get(booking.getLessonId());
        if (lesson == null) return false;

        Review review = new Review(rating, comment);
        lesson.addReview(review);

        booking.markAttended();
        return true;
    }

    // --- Reports ---
    // monthNo: 1 or 2 (simple mapping: month1 = weekends 1..4, month2 = weekends 5..8)
    public void printMonthlyLessonReport(int monthNo) {

        int startW = (monthNo == 1) ? 1 : 5;
        int endW = (monthNo == 1) ? 4 : 8;

        System.out.println("=== Monthly Lesson Report (Month " + monthNo + ") ===");

        lessons.values().stream()
                .filter(l -> l.getWeekendNo() >= startW && l.getWeekendNo() <= endW)
                .sorted(Comparator.comparingInt(Lesson::getWeekendNo)
                        .thenComparing(Lesson::getDay)
                        .thenComparing(Lesson::getTimeSlot))
                .forEach(l -> {

                    int attended = attendedCountForLesson(l.getLessonId());

                    System.out.printf("%s | attended=%d | avgRating=%.2f%n",
                            l.getLessonId(),
                            attended,
                            l.averageRating());
                });
    }
    

    public void printMonthlyIncomeReport(int monthNo) {
        int startW = (monthNo == 1) ? 1 : 5;
        int endW = (monthNo == 1) ? 4 : 8;

        Map<String, Double> incomeByType = new HashMap<>();

        for (Lesson l : lessons.values()) {
            if (l.getWeekendNo() < startW || l.getWeekendNo() > endW) continue;

            // income is based on attended members. Here we approximate by counting reviews as attended count.
            // (Later we can improve: track attended per booking)
            int attendedCount = l.getReviews().size();

            double income = attendedCount * l.getPrice();
            incomeByType.merge(l.getExerciseType(), income, Double::sum);
        }

        System.out.println("=== Monthly Champion Exercise Type Report (Month " + monthNo + ") ===");
        incomeByType.forEach((k, v) -> System.out.printf("%s income = %.2f%n", k, v));

        String champion = incomeByType.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        System.out.println("Champion exercise type: " + champion);
    }

    // helper for UI
    public boolean memberExists(String memberId) { return members.containsKey(memberId); }
    public boolean lessonExists(String lessonId) { return lessons.containsKey(lessonId); }
    public boolean bookingExists(String bookingId) { return bookings.containsKey(bookingId); }
}