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
  - JSON Web Tokens (JWT) for secure authentication
- **Database**
  - MySQL 8.x
- **ORM Framework**
  - Hibernate
- **Dependency Management**
  - Maven
- **Web Services Consumed Using Java**
  - OpenStreetMap Nominatim API
- **RESTful Web Services**
  - Jersey (JAX-RS) for building RESTful APIs
  - Jackson for JSON serialization and deserialization
- **CSS**
  - Bootstrap for responsive design and UI components
  - Custom CSS for page-specific styling
- **Data Validation**
  - Bootstrap Validator (Front-end)
  - Hibernate Validator (Back-end)
- **Logging**
  - Log4J2 for application logging
- **Testing**
  - JUnit (JUnit 4 and JUnit 5) for unit and integration testing
- **Mapping and Geolocation**
  - Leaflet.js for interactive maps
- **Frontend Development**
  - HTML, CSS, JSP for UI rendering
  - JavaScript (ES6) for client-side interactivity
  - AJAX and Fetch API for asynchronous data fetching
- **Hosting**
  - AWS Elastic Beanstalk for deployment (planned, but not used due to cost concerns)
  - Local deployment for development and testing
- **Development Tools**
  - IntelliJ IDEA for coding and debugging
  - MySQL Workbench for database management
  - Postman for API testing
  - Git/GitHub for version control
- **Unit Testing**
  - JUnit for test coverage and validation
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