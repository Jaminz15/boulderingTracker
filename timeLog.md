# Time Log

## **Week 1**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- 01/22/2025 - 2 hours - Thought of project ideas and reviewed requirements
- 01/23/2025 - 1.5 hours - Set up things for the class, worked on exercise 1

## **Week 2**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 01/31/2025 - 1 hours - Reading all the material for week 2
- 02/01/2025 - 2 hours - Watching videos
- 02/01/2025 - 5 hours - Created `BoulderingTracker` and added the changes to it
- 02/01/2025 - 2 hours - Created `README.md` and documented problem statement
- 02/01/2025 - .5 hours - Created `timeLog.md`, reviewed commit process, and planned initial files  
- 02/02/2025 - 8 hours - Worked on exercise 2

## **Week 3**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 01/06/2025 - 1.5 hours - Reading all the material for week 3
- 02/06/2025 - 5 hours - Watching videos, demo, and setup
- 02/07/2025 - 6 hours - Worked on exercise 3
- ### **Individual Project (BoulderBook)**
- 02/09/2025 - 4 hours - Worked on Checkpoint 1
## **Week 4**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 02/14/2025 - 2 hours - Reading all the material for week 3
- 02/14/2025 - 2 hours - Watching demo videos
- 02/15/2025 - 2 hours - Watching demo videos
- 02/15/2025 - 3 hours - Exercise 4 walkthrough, finishing the test
- ### **Individual Project (BoulderBook)**
- 02/10/2025 - 3 hours - Worked on Checkpoint 1 (Wireframes/Screens.md)
- 02/16/2025 - 1 hour - First draft of database design
- 02/16/2025 - 1 hour - Created development version of the database (Gym & Climb tables)
- 02/16/2025 - 1 hour - Implemented Climb Entity
- 02/16/2025 - 1 hour - Implemented Gym Entity

## **Week 5**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 02/19/2025 - 2 hour - Reading material and week 5 videos/demos
- 02/20/2025 - 5 hour - week 5 videos/demos
- 02/21/2025 - 3 hour - week 5 videos/demos
- ### **Individual Project (BoulderBook)** 
- 02/17/2025 - 2 hours - Added changes from Checkpoint 1 feedback (updated project plan, user stories, and wireframes).
- 02/17/2025 - 2 hours - Updated database schema (added `users` table, linked `climb` to `user_id`).
- 02/17/2025 - 2 hours - Updated `Climb.java`, `Gym.java`, and created `User.java` with correct relationships and `toString()` fixes.
- 02/22/2025 - 1.5 hour - week 5 exercise for Individual Project, created `GenericDao`
- 02/22/2025 - 3 hour - week 5 exercise for Individual Project, created test database, created `ClimbDaoTest`
- 02/23/2025 - 2.5 hours - Wrote `GymDaoTest` and `UserDaoTest`, verified cascading deletes.
- 02/23/2025 - 1 hours - Ran full test coverage, validated passing tests, and finalized documentation.


## **Week 6**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 02/25/2025 - 2 hour - Reading material and week 5 videos/demos
- 02/26/2025 - 1 hours - Added `climbResults.jsp`, connected database and project to AWS
- 02/26/2025 - .5 hours - Added a button to `index.jsp` to navigate to climb results page and display table
- 02/26/2025 - 6 hours - Exercise 6, setting up AWS with my project
- 
- ### **Individual Project (BoulderBook)** 
- 02/27/2025 - 4 hours - Implemented Gym Management feature, 
Created `GymManagement.java` servlet, Built `gymManagement.jsp` page Implemented functionality to list, add, and delete gyms

## **Week 6**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 03/02/2025 - 2.5 hour - Reading material and week 6 videos/demos
-
- ### **Individual Project (BoulderBook)**
- 03/03/2025 - 4 hours - Implemented Climb Logging Feature, Created `ClimbController.java` servlet,
  built `logClimb.jsp` page for users to log climbs, integrated gym selection, grade dropdown, and form submission,
  fixed data persistence issue.
- 03/04/2025 - 2 hours - Created ERD for database, added screenshots, and checked over everything for checkpoint 2.

## **Week 7**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 03/06/2025 - 2.5 hour - Reading material and week 7 videos/demos/exercise
- 03/08/2025 - 4 hour - Week 7 exercise
- ### **Individual Project (BoulderBook)**
- **03/09/2025** - **11 hours**
  - Integrated **AWS Cognito authentication**
  - Fixed **preferred_username** issue in Cognito
  - Updated **database schema** to store Cognito user attributes
  - Ensured **logs are removed from GitHub** and updated **.gitignore**
  - Verified **sign-up and login process** with Cognito 
  - Implemented **role-based authorization** (Admin/User) to restrict climb log visibility

## **Week 8**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 03/13/2025 - 1.5 hours - Reading material
- 03/14/2025 - 2 hours -week 7 videos/demos/exercise
- 03/15/2025 - 4.5 hours - Week 8 exercise, OpenStreetMap Nominatim API into BoulderBook project
- 03/21/2025 - 2 hours - Got OpenStreetMapDao to use a static AppConfig.getProperty() call instead of reading the file every time the DAO is created.
   - removed duplicate test and added check to make sure user isn't deleted when climb is deleted
   - Replace println with Log4J logger in GymManagement servlet
   - Replace scriptlets with JSTL and EL in climbResults.jsp
- ### **Individual Project (BoulderBook)**