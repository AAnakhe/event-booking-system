## INSTRUCTIONS ON HOW TO RUN THE PROJECT LOCALLY

1. Clone the repo from git lab: 

- git clone (repo url)

**Open terminal, on projects directory and run the following commands**

2. Clean the project to remove previous builds:

- ./mvnw clean

3. Run the test methods:

- ./mvnw test

4. Build and run project:

- ./mvnw spring-boot:run

Alternatively, after cleaning the project you can run project directly from your IDE and run the test method manually from the individual test classes.

- *visit http://localhost:8080/swagger-ui/index.html#/ on your browser to display the endpoints on swagger ui then tests various endpoints*


## Technologies Used 

- Maven
- Java 21
- SpringBoot 3.3.0
- Spring Security
- H2 database
- Junit
- Mockito
- Mailtrap sandbox


**NB**

- Periodic email notification is sent to users notifying them of the start of an event, a day before the event starts at 10:30 am, mailtrap sandbox for testing is used for sending emails.

- An endpoint was added to manually trigger email notification for booked events starting the next day to the logged in user and the process saved in the event log (for the purpose of testing).