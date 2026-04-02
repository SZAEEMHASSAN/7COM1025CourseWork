# 7COM1025CourseWork
this is courseWork project of software engineering practice and experience.

### 1. Model Classes

The initial classes were created to represent the core data:

- **Member**
  - Stores member ID and name

- **Review**
  - Stores rating (1–5) and comment
  - Includes validation for rating range

- **Lesson**
  - Represents a fitness class
  - Includes:
    - day, time slot, exercise type, price
    - fixed capacity (4 members)
    - list of booked members
    - list of reviews
  - Handles:
    - adding/removing members
    - preventing duplicate bookings
    - capacity control
    - calculating average rating

### 2. Initial Testing (MainApp)

A basic `MainApp` was created to test:
- adding members to a lesson
- preventing duplicate entries
- adding reviews
- calculating average rating

This confirmed that the Lesson logic works correctly.

### 3. Booking Class (March 10)

A **Booking** class was added to manage bookings.

It includes:
- booking ID
- member ID
- lesson ID
- booking status

Supported actions:
- cancel booking
- mark as attended
- change lesson

### 4. DataSeeder (March 11)

A `DataSeeder` class was introduced to generate sample data.

It provides:
- 10 members
- lessons for 8 weekends (48 lessons total)

This makes testing easier without manual input.

### 5. FLCSystem (Core Functionality)

The main system class was implemented to handle:

#### Timetable
- view lessons by day
- view lessons by exercise type

#### Booking
- create booking
- prevent duplicate bookings
- enforce lesson capacity

#### Booking Management
- change booking (same ID retained)
- cancel booking

#### Attendance
- mark lesson as attended
- add review (rating + comment)

#### Reports
- monthly lesson report (bookings + ratings)
- monthly income report (based on attendance approximation)

### 6. Updated MainApp

The application now demonstrates:
- loading seeded data
- viewing timetable
- booking a lesson
- marking attendance
- generating reports

## Notes

- Duplicate bookings are not allowed  
- Lesson capacity is limited to 4 members  
- Cancelled or attended bookings cannot be modified  
- Attendance is currently approximated using reviews  
- Month mapping:
  - Month 1 → weekends 1–4  
  - Month 2 → weekends 5–8  

---

## Commit Included

**Add DataSeeder and FLCSystem core functionality**

This includes:
- DataSeeder implementation
- FLCSystem with booking and reporting logic
- Updated MainApp for testing

