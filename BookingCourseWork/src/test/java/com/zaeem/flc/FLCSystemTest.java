package com.zaeem.flc;

import com.zaeem.flc.model.Booking;
import com.zaeem.flc.service.DataSeeder;
import com.zaeem.flc.service.FLCSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FLCSystemTest {

    private FLCSystem system;

    @BeforeEach
    void setUp() {
        system = new FLCSystem(
                DataSeeder.seedMembers(),
                DataSeeder.seedLessons8Weekends()
        );
    }

    @Test
    void testBookLessonSuccess() {
        Booking booking = system.bookLesson("M01", "W1-SAT-MOR-YOGA");
        assertNotNull(booking);
        assertEquals("M01", booking.getMemberId());
        assertEquals("W1-SAT-MOR-YOGA", booking.getLessonId());
    }

    @Test
    void testDuplicateBookingFails() {
        Booking first = system.bookLesson("M01", "W1-SAT-MOR-YOGA");
        Booking second = system.bookLesson("M01", "W1-SAT-MOR-YOGA");

        assertNotNull(first);
        assertNull(second);
    }

    @Test
    void testCapacityLimitFailsOnFifthBooking() {
        assertNotNull(system.bookLesson("M01", "W1-SAT-MOR-YOGA"));
        assertNotNull(system.bookLesson("M02", "W1-SAT-MOR-YOGA"));
        assertNotNull(system.bookLesson("M03", "W1-SAT-MOR-YOGA"));
        assertNotNull(system.bookLesson("M04", "W1-SAT-MOR-YOGA"));

        Booking fifth = system.bookLesson("M05", "W1-SAT-MOR-YOGA");
        assertNull(fifth);
    }

    @Test
    void testCancelBookingSuccess() {
        Booking booking = system.bookLesson("M01", "W1-SAT-MOR-YOGA");
        assertNotNull(booking);

        boolean cancelled = system.cancelBooking(booking.getBookingId());
        assertTrue(cancelled);
    }

    @Test
    void testAttendLessonSuccess() {
        Booking booking = system.bookLesson("M01", "W1-SAT-MOR-YOGA");
        assertNotNull(booking);

        boolean attended = system.attendLesson(booking.getBookingId(), 5, "Great lesson");
        assertTrue(attended);
    }
}