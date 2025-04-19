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

## **Week 8 & (Spring break)**
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
- 03/23/2025 – 3 hours – Completed secure climb logging by removing hardcoded user ID from logClimb.jsp and updating 
ClimbController to associate climbs with the logged-in user via Cognito sub. Verified correct user_id is stored in 
the database. Reviewed wireframes, identified missing JSPs and controllers (Edit Climb, User Profile, Track 
Progress), and planned next steps for dashboard integration.
- 03/23/2025 - 2 hours - Created Dashboard servlet to load user-specific climb and gym data. Updated Auth servlet 
to redirect to /dashboard after login. Verified personalized gym list and last log date rendering in index.jsp.
- 03/23/2025 – 2.5 hours – Created GymPage servlet and built gymPage.jsp to display user-specific climbs by selected 
gym. Implemented filtering logic using session-based Cognito sub and joined climb and gym data for authenticated users. 
Connected dashboard links to pass gymId and confirmed correct data shows per user.
- 03/24/2025 – 2.5 hours – Created `editClimb.jsp` to pre-fill climb info for editing.
  Built `EditClimb` servlet to load the selected climb and gym list.
  Connected the update functionality in ClimbController to save changes.
- 03/24/2025 – 1.5 hours – Created `UserProfile` controller and `userProfile.jsp`
- 03/24/2025 – 2 hours – Created `TrackProgress` controller and `trackProgress.jsp`

## **Week 9**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
-- 03/25/2025 - 2.5 hour - Reading material, week 9 videos/activity
- 03/25/2025 - 4 hour - week 9 exercise
- 03/29/2025 - 7 hour - week 9 exercise
-
- ### **Individual Project (BoulderBook)**
- 03/27/2025 - 2 hours - Added Leaflet JS and CSS to gymManagement.jsp
- 03/27/2025 - 1.5 hour - Implemented logout functionality with Cognito
- 03/29/2025 – 3 hours – Created `GymWebService`, `ObjectMapperContextResolver`, and `RestApplication` for RESTful web services

- ### **Group Project (Urban Photography API**
- 3/26/2025 - 3 hours - Met as a group, worked through everything for the week and finished the README

## **Week 10**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- 04/02/2025 to 04/03.2025 - 3 hour - week 10 stuff and activity 1, activity 2
- ### **Individual Project (BoulderBook)**
- 04/06/2025 - 5 hours - Created style.css for consistent styling across the app.
Added reusable `header.jsp` and `footer.jsp` for layout structure and navigation.
Applied styling improvements to all JSPs for better UI/UX.
Implemented admin-only functionality for gym deletion (based on Cognito group membership).
Updated GymPage controller to allow admins to view all user climbs at a gym, while regular users only see their own.
- 04/07/2025 - 4 hours - Addressed all areas for improvement noted in my Checkpoint 3 review.

- ### **Group Project (Urban Photography API)**
- 04/02/2025 - 1 hour - Met up with group and added things to the README that was missing and worked together 
as a group to figure out what we will all work on for the week.
- 04/03/2025 - 4 hour - Worked on the `LocationService`, `Location`, and `RestApplication`.

## **Week 11**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
- Work week for the Group Project
- ### **Individual Project (BoulderBook)**
- **04/13/2025 – 3 hours**  
  Added gym and date range filtering to the **Track Progress** page.  
  Updated the controller to calculate dynamic stats like total climbs, success rate, best grade, hardest climb, and most attempts.  
  Displayed filtered results in a climb log table with success/fail indicators.
- **04/13/2025 – 2 hours**  
  Improved logging across all major servlets to track user actions, climb events, and admin activity.  
  Added and expanded DAO test coverage to include saving, updating, deleting, and filtered queries.
- **04/13/2025 – 3 hours**  
  Added full server-side validation and user feedback for climbing and gym forms.  
  Implemented input checking, error redirects, and JSTL error message blocks in JSPs.
  **Files updated:**
  - `ClimbController.java`
  - `logClimb.jsp`
  - `editClimb.jsp`
  - `GymManagement.java`
  - `gymManagement.jsp`

- ### **Group Project (Urban Photography API)**
- 04/08/2025 - 1.5 hour - Created a new branch and added `LocationService`, `RestApplication` and added it to 
GitHub and created a pull request for it.
- 04/10/2025 - 4 hours - Implemented and tested DAO classes for Location, Subject, and Image entities. Added 
necessary constructors to Coordinates, updated BestTime entity mapping, and configured .gitignore. Merged changes 
via GitHub pull request.
- 04/11/2025 – 5 hours – Finished and tested GET and POST endpoints for the /locations service. Verified functionality 
using Postman and added screenshots showing the new location being returned in the GET response. Made several key updates:
  - Added LocationService methods for GET and POST
  - Updated entity annotations (e.g., @JsonManagedReference, @JsonBackReference) to prevent infinite loops during JSON serialization
  - Updated pom.xml with correct Jersey and Jackson dependencies and versions
  - Created RestApplication to register base /services path
  - Confirmed full functionality and created a pull request for review

## **Week 12**
- **[Date]** - **[Time Spent]** - **[Task Completed]**
- ### **Weekly Course Material**
-- 03/25/2025 - 2.5 hour - 
-
- ### **Individual Project (BoulderBook)**
- 04/18/2025 – 2 hours – Completed unit test review, manual UI testing, servlet optimization check, and confirmed no performance issues or major bugs.

- ### **Group Project (Urban Photography API**
- 04/14/2025 – 2 hours – Held final group meeting to verify everything was working correctly and recorded our individual project presentation videos.
