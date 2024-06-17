# Event Booking

[[_TOC_]]

---

:scroll: **START**

## Introduction

In today's fast-paced world, the convenience of booking systems has become an essential aspect of daily life. From booking tickets for a concert or reserving a spot at a conference, these systems are used widely by individuals and businesses alike.

---

## Task Description

The system will allow users to create, find and reserve tickets for events, view and manage their reservations and to be notified before the event kickoff.

A **user** has:
- name (limited to 100 characters);
- email (valid email format);
- password (minimum 8 characters).

An **event** has:
- name (limited to 100 characters);
- date (in a valid date format);
- available attendees count (positive integer limited to 1000);
- event description (limited to 500 characters).
- category (Concert, Conference, Game)

Develop a set of REST service APIs based on the swagger file provided - [swagger file](event-booking-swagger.yml), that allows users to:

- Create an account;
- User authentication to log into the system;
- Create events;
- Search and reserve tickets for events;
- Send notification to users before event starts.

> Feel free to make assumptions for the design approach. 

## Requirements

While implementing your solution **please take care of the following requirements:**

### Functional requirements

- The REST API methods should be implemented based on the specification provided in the linked swagger file;
- Add 2 new methods, one to **view** your booked events and one to **cancel** your reservation _**(both should be authorized)**_;
- Introduce a periodic task to send notifications for upcoming events to users and create history/audit event log for this.
- No need for UI;

### Non-functional requirements

- The project MUST be buildable and runnable;
- The project MUST have Unit tests;
- The project MUST have a README file with build/run/test instructions (use a DB that can be run locally, e.g. in-memory, via container);
- Any data required by the application to run (e.g. reference tables, dummy data) MUST be preloaded in the database;
- Input/output data MUST be in JSON format;
- Use a framework of your choice, but popular, up-to-date, and long-term support versions are recommended.

---

:scroll: **END**
