# BoulderBook: A Bouldering Progress Tracking App

This repository serves as the Individual Project for Madison College's Enterprise Java Class.

## Problem Statement

Bouldering has become an increasingly popular sport, offering climbers a dynamic and engaging way to challenge
themselves. However, despite the growth of indoor climbing gyms and outdoor climbing spots, there is no widely
adopted system for tracking individual progress. Most climbing gyms do not provide a way to log climbs, track
different styles, or analyze long-term improvement.

To address this gap, **BoulderBook** is a Java-based web application designed to help climbers log their sessions,
track their progress, and analyze their climbing history. With **BoulderBook**, users can:

- Log their climbing sessions and choose the type of climb, such as slab, overhang, dyno, etc.
- Record grades and track their progress over time.
- Keep track of the gyms they visit and log climbs under each gym.
- Use filtering to see the average level of climbs they complete, set personal goals, and stay motivated.

When climbing indoors, **BoulderBook** will help climbers stay motivated, track their progress, and improve their skills.

---

## Project Technologies/Techniques

- **Security/Authentication**
  - AWS Cognito
- **Database**
  - MySQL 8.x
- **ORM Framework**
  - Hibernate (JPA)
- **Dependency Management**
  - Maven
- **Web Services Consumed Using Java**
  - OpenStreetMap Nominatim API
- **CSS**
  - Bootstrap or Materialize (TBD)
- **Data Validation**
  - Bootstrap Validator (Front-end)
  - Hibernate Validator (Back-end)
- **Logging**
  - Log4J2
- **Hosting**
  - AWS
- **Tech I'd like to explore as part of this work:**
  - CI tools in AWS (AWS CodePipeline or GitHub Actions)
  - OpenStreetMap API integration
  - Hibernate Search (if full-text search is needed)
- **Project Lombok**
- **Unit Testing**
  - JUnit for test coverage
- **IDE**
  - IntelliJ IDEA

---

## Design

- [User Stories](DesignDocuments/userStories.md)
- [Screen Design](DesignDocuments/Screens.md)

---

## [Project Plan](ProjectPlan.md)

---

## Documentation of Progress

#### [TimeLog](timeLog.md)