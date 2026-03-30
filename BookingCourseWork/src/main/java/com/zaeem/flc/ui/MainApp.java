package com.zaeem.flc.ui;

import com.zaeem.flc.model.Day;
import com.zaeem.flc.service.DataSeeder;
import com.zaeem.flc.service.FLCSystem;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        FLCSystem system = new FLCSystem(
                DataSeeder.seedMembers(),
                DataSeeder.seedLessons8Weekends()
        );

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== FLC Booking System ===");
            System.out.println("1. View timetable (by day)");
            System.out.println("2. View timetable (by exercise)");
            System.out.println("3. Book a lesson");
            System.out.println("4. Change a booking");
            System.out.println("5. Cancel a booking");
            System.out.println("6. Attend a lesson (review + rating)");
            System.out.println("7. Monthly lesson report");
            System.out.println("8. Monthly champion income report");
            System.out.println("9. Exit");

            System.out.print("Choose option: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1" -> {
                    System.out.print("Enter day (SATURDAY/SUNDAY): ");
                    String d = sc.nextLine().trim().toUpperCase();
                    try {
                        system.viewByDay(Day.valueOf(d)).forEach(System.out::println);
                    } catch (Exception e) {
                        System.out.println("Invalid day.");
                    }
                }
                case "2" -> {
                    System.out.print("Enter exercise name (e.g., Yoga): ");
                    String ex = sc.nextLine().trim();
                    system.viewByExercise(ex).forEach(System.out::println);
                }
                case "3" -> {
                    System.out.print("Enter memberId (e.g., M01): ");
                    String memberId = sc.nextLine().trim();
                    System.out.print("Enter lessonId (e.g., W1-SAT-MOR-YOGA): ");
                    String lessonId = sc.nextLine().trim();

                    var booking = system.bookLesson(memberId, lessonId);
                    if (booking == null) System.out.println("Booking FAILED (invalid/duplicate/full).");
                    else System.out.println("Booking SUCCESS. BookingId = " + booking.getBookingId());
                }
                case "4" -> {
                    System.out.print("Enter bookingId (e.g., B0001): ");
                    String bookingId = sc.nextLine().trim();
                    System.out.print("Enter new lessonId: ");
                    String newLessonId = sc.nextLine().trim();

                    boolean ok = system.changeBooking(bookingId, newLessonId);
                    System.out.println(ok ? "Change SUCCESS." : "Change FAILED (invalid/full/not allowed).");
                }
                case "5" -> {
                    System.out.print("Enter bookingId (e.g., B0001): ");
                    String bookingId = sc.nextLine().trim();

                    boolean ok = system.cancelBooking(bookingId);
                    System.out.println(ok ? "Cancel SUCCESS." : "Cancel FAILED (invalid/not allowed).");
                }
                case "6" -> {
                    System.out.print("Enter bookingId (e.g., B0001): ");
                    String bookingId = sc.nextLine().trim();

                    System.out.print("Enter rating (1-5): ");
                    int rating;
                    try {
                        rating = Integer.parseInt(sc.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Invalid rating.");
                        break;
                    }

                    System.out.print("Enter review text: ");
                    String comment = sc.nextLine();

                    boolean ok = system.attendLesson(bookingId, rating, comment);
                    System.out.println(ok ? "Attend SUCCESS." : "Attend FAILED (invalid/not allowed).");
                }
                case "7" -> {
                    System.out.print("Enter month number (1 or 2): ");
                    try {
                        int month = Integer.parseInt(sc.nextLine().trim());
                        system.printMonthlyLessonReport(month);
                    } catch (Exception e) {
                        System.out.println("Invalid month.");
                    }
                }
                case "8" -> {
                    System.out.print("Enter month number (1 or 2): ");
                    try {
                        int month = Integer.parseInt(sc.nextLine().trim());
                        system.printMonthlyIncomeReport(month);
                    } catch (Exception e) {
                        System.out.println("Invalid month.");
                    }
                }
                case "9" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}