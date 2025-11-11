# Resonance Platform – API Automation Testing (REST Assured)

### Overview

This project contains automated API test scripts for the Resonance Platform, a web-based issue/ticket management system.
The automation is implemented using Java + REST Assured + TestNG, designed to validate CRUD operations for ticket management with clean, modular, and maintainable code structure.

This repository is part of a complete QA deliverable set, aligned with:
* Requirement & Acceptance Criteria – Resonance Platform v2.0
* Test Plan – Resonance Platform v2.0

Both documents define functional scope, API behavior, and test scenarios used to build this automation project.

### Objective

The goal of this API automation project is to:
* Validate core API functionalities of the Ticket module
* Confirm data integrity across Create, Read, Update, and Delete operations
* Ensure API compliance with defined Acceptance Criteria
* Provide reusable API test suites for regression and smoke testing
* Demonstrate professional QA Automation engineering skills

API Module Covered: Ticket Management

This API suite includes validation for the following endpoints:
* POST - Create Ticket
* GET - Ticket by ID
* PUT - Update Ticket
* DELETE - Delete Ticket

Run with Terminal
./gradlew clean test -Psuite="login.xml"
./gradlew clean test -Psuite="testng.xml"

Cek detail document disini :
https://drive.google.com/drive/folders/1pEWFltsicnHxRJf3NIzQtcrDAL9l374O?usp=sharing 