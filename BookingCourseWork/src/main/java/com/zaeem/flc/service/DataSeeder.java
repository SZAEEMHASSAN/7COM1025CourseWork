package com.zaeem.flc.service;

import com.zaeem.flc.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

    public static List<Member> seedMembers() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("M01", "Ali"));
        members.add(new Member("M02", "Sara"));
        members.add(new Member("M03", "Usman"));
        members.add(new Member("M04", "Ayesha"));
        members.add(new Member("M05", "Hassan"));
        members.add(new Member("M06", "Hira"));
        members.add(new Member("M07", "Bilal"));
        members.add(new Member("M08", "Maryam"));
        members.add(new Member("M09", "Hamza"));
        members.add(new Member("M10", "Noor"));
        return members;
    }

    public static List<Lesson> seedLessons8Weekends() {
        // Prices (same exercise always same price)
        double yoga = 10.0;
        double zumba = 12.0;
        double boxFit = 15.0;
        double aquacise = 14.0;

        List<Lesson> lessons = new ArrayList<>();

        // 8 weekends, each weekend has 6 lessons (Sat 3 + Sun 3) = 48
        for (int w = 1; w <= 8; w++) {

            // Saturday
            lessons.add(new Lesson("W" + w + "-SAT-MOR-YOGA", w, Day.SATURDAY, TimeSlot.MORNING, "Yoga", yoga));
            lessons.add(new Lesson("W" + w + "-SAT-AFT-ZUMBA", w, Day.SATURDAY, TimeSlot.AFTERNOON, "Zumba", zumba));
            lessons.add(new Lesson("W" + w + "-SAT-EVE-BOXFIT", w, Day.SATURDAY, TimeSlot.EVENING, "Box Fit", boxFit));

            // Sunday
            lessons.add(new Lesson("W" + w + "-SUN-MOR-AQUACISE", w, Day.SUNDAY, TimeSlot.MORNING, "Aquacise", aquacise));
            lessons.add(new Lesson("W" + w + "-SUN-AFT-YOGA", w, Day.SUNDAY, TimeSlot.AFTERNOON, "Yoga", yoga));
            lessons.add(new Lesson("W" + w + "-SUN-EVE-ZUMBA", w, Day.SUNDAY, TimeSlot.EVENING, "Zumba", zumba));
        }

        return lessons;
    }
}