# Ndosi Test Automation Project 2: Weather API

## Overview

This project automates the API testing for [Open WeatherMap](https://openweathermap.org/stations) using **RestAssured**
with **Java** and **TestNG**.

It validates the functionalities of the API.

<br/>

## Prerequisites

Before setting up the project, ensure you have the following installed:

* **Java JDK 21**
* **Apache Maven 3.6 or higher**
* **Git**

<br/>

## Setup instructions

1. Clone the repository

```bash
git clone https://github.com/vraagakrishna/automation.testing.project.api
cd automation.testing.project.api 
```

2. Run the tests and generate the test report

```bash
mvn clean test -DWEATHER_API_KEY=YOUR_API_KEY
```

<br/>

## Setup in IntelliJ IDEA (Optional)

1. Import the project

* Open IntelliJ IDEA.
* Select **File** -> **Open**, and choose the cloned project folder.
* Wait for IntelliJ to download all Maven dependencies.

2. Open the `testng.xml` file


3. **Right-click** -> **Run 'testng.xml'**

<br/>

## Project Structure

```
.
├── src
│   └── test
│       └── java
│           ├── common
│           │   └── BasePaths.java
│           ├── model                           # Data models
│           │   └── PostStation.java
│           ├── requestbuilder                  # API requests
│           │   └── WeatherApiRequestBuilder.java 
│           ├── tests                           # Test classes
│           │   └── WeatherApiTests.java 
│           └── utils                           # Helper classes
│               └── StationTestData.java
├── pom.xml
├── README.md
└── testng.xml
```

<br/>

## Assessment requirements

Perform functional API testing using Postman (or your preferred Custom API tool) against the scenario below. Treat this
as the first time that testing has been performed against this API.

### Test Scenario

You have been tasked to test/create the following scenario:

- [ ] Get the newly registered weather stations info
- [ ] Update the station's info
- [ ] Delete this weather station and confirm it has been deleted
- [x] Register a weather station

### Objectives

Keep the following objectives in mind when creating your test suite:

- [ ] Show how you can perform chaining of requests (passing data from one response to another request)
- [ ] Implement assertions for your postman calls (pass/fail criteria)
- [ ] Perform parameterisation for any dynamic configs or paths
- [x] Create the expected API calls for the scenario above in ~~Postman~~ RestAssured
- [x] Create tests to demonstrate how you would also test negative scenarios and data validation
