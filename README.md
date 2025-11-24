This project focuses on Java class design, public interfaces, and software testing using JUnit. It enhances understanding of object-oriented programming, test-driven development, and clean API design through the EventForGlobalPlanning class, which models globally scheduled events using the Java Time API.

Project Overview

The goal of this workshop is to improve skills in:

Designing Java classes with clear, complete, and convenient public interfaces

Implementing methods that support real scheduling logic

Using JUnit to create structured unit tests

Applying test-driven development principles

Creating acceptance test data and validating application behavior

Using time zones and local date/time handling in Java

The project simulates a small team workflow, separating responsibilities into class implementer, class user, and class tester roles.

🔧Key Features
1. Class Implementation

Implemented the conflictsWith() method to detect overlapping events based on start and end times across time zones.

Customized the toString() method to display a shortened description and local start time suitable for planner cells.

2. Class Usage

Built a simple client application to display events and allow time-zone adjustments.

Demonstrated how the updated toString() integrates with UI/console output.

3. Unit Testing

Created JUnit test classes to validate:

conflict detection logic

time conversions

correct string formatting

Developed acceptance test cases and verified application output.

🚀 How to Run
1. Clone the Repository
git clone https://github.com/<your-username>/<your-workshop3-repo>.git

2. Open in IntelliJ IDEA

File → Open

Select the project directory

3. Run the Test Suite

Right-click the test folder

Select Run All Tests

JUnit output will appear in the test runner window

4. Run the Client Application

Navigate to your main client class (e.g., Main.java)

Right-click → Run 'Main'

This will display events in the selected time zone using the updated EventForGlobalPlanning behavior.

Project Structure
src/
 ├── main/
 │     ├── java/
 │     │      ├── planner/        # Client code
 │     │      ├── model/          # EventForGlobalPlanning revised class
 │     │      └── util/           # Any helpers (optional)
 │
 └── test/
       └── java/
             └── model/           # JUnit tests for EventForGlobalPlanning

📌Why This Project Is Important

This workshop builds real-world development skills:

Understanding and designing clean public interfaces

Writing JUnit tests that catch logical errors early

Handling global time zones, a common challenge in production systems

Practicing documentation through Javadoc and test reports

These skills are directly applicable to internships, software engineering roles, and collaborative team projects.
