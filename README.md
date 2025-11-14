# Ndosi Test Automation Project 2: API Testing

## Overview

This project automates the API testing using **RestAssured** with **Java** and **TestNG** for:

* [Open WeatherMap](https://openweathermap.org/stations)
* [ReqRes](https://reqres.in/)
* [Ndosi Automation](https://www.ndosiautomation.co.za/API/swagger-ui.html)

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
mvn clean test -DWEATHER_API_KEY=YOUR_WEATHER_API_KEY -DREQRES_API_KEY=YOUR_REQRES_API_KEY
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
│           ├── listeners                       # Listeners
│           │   ├── SuiteRepeaterListener.java
│           │   └── TestLoggerListener.java
│           ├── model                           # Data models
│           │   ├── ndosiautomation             # Data models for Ndosi Automation
│           │   │   ├── Failure.java
│           │   │   ├── GetProfileResponse.java
│           │   │   ├── GetProfileResponseData.java
│           │   │   ├── LoginRequest.java
│           │   │   ├── LoginResponse.java
│           │   │   ├── LoginResponseData.java
│           │   │   ├── LoginResponseUserData.java
│           │   │   ├── PasswordRequest.java
│           │   │   ├── RegisterRequest.java
│           │   │   ├── RegisterResponse.java
│           │   │   ├── RegisterResponseData.java
│           │   │   └── UpdateRequest.java
│           │   ├── reqres                      # Data models for reqres
│           │   │   ├── Failure.java
│           │   │   ├── GetResouce.java
│           │   │   ├── GetResourceData.java
│           │   │   ├── GetSingleResource.java
│           │   │   ├── GetSingleUser.java
│           │   │   ├── GetUser.java
│           │   │   ├── GetUserData.java
│           │   │   └── LoginRegisterUser.java
│           │   └── weatherapi                  # Data models for weather api
│           │       ├── Failure.java
│           │       ├── GetStation.java
│           │       ├── GetStationList.java
│           │       └── PostStation.java
│           ├── requestbuilder                  # API requests
│           │   ├── ndosiautomation             # API requests for Ndosi Automation
│           │   │   ├── BaseNdosiAutomationRequestBuilder.java
│           │   │   └── NdosiAutomationRequestBuilder.java
│           │   ├── reqres                      # API requests for reqres
│           │   │   ├── BaseReqResApiRequestBuilder.java
│           │   │   └── ReqResApiRequestBuilder.java 
│           │   ├── weatherapi                  # API requests for weather api
│           │   │   ├── BaseWeatherApiRequestBuilder.java
│           │   │   └── WeatherApiRequestBuilder.java 
│           │   └── BaseRequestBuilder.java
│           ├── tests                           # Test classes
│           │   ├── ndosiautomation             # Test classes for Ndosi Automation
│           │   │   ├── AuthTests.java
│           │   │   ├── NdosiAutomationTests.java
│           │   │   └── UserProfileTests.java 
│           │   ├── reqres                      # Test classes for reqres
│           │   │   ├── AuthTests.java
│           │   │   ├── ReqResApiTests.java
│           │   │   ├── ResourceTests.java
│           │   │   └── UserTests.java 
│           │   └── weatherapi                  # Test classes for weather api
│           │       └── WeatherApiTests.java  
│           └── utils                           # Helper classes
│               ├── AllureUtils.java
│               ├── JwtUtils.java
│               ├── NdosiAutomationTestData.java
│               ├── RandomNumberGenerator.java
│               ├── ReqResTestData.java
│               ├── StationTestData.java
│               ├── ValidateFormats.java
│               ├── ValidateNdosiAutomationUtils.java
│               └── ValidateReqResUtils.java
├── pom.xml
├── README.md
└── testng.xml
```

<br/>

## [Open WeatherMap](https://openweathermap.org/stations)

Perform functional API testing using Postman (or your preferred Custom API tool) against the scenario below. Treat this
as the first time that testing has been performed against this API.

### Test Scenario

You have been tasked to test/create the following scenario:

- [x] Register a weather station
- [x] Get the newly registered weather stations info
- [x] Update the station's info
- [x] Delete this weather station and confirm it has been deleted

### Objectives

Keep the following objectives in mind when creating your test suite:

- [x] Create the expected API calls for the scenario above in ~~Postman~~ RestAssured
- [x] Create tests to demonstrate how you would also test negative scenarios and data validation
- [x] Show how you can perform chaining of requests (passing data from one response to another request)
- [x] Implement assertions for your postman calls (pass/fail criteria)
- [x] Perform parameterisation for any dynamic configs or paths

<br/>

## [ReqRes](https://reqres.in/)

ReqRes is a hosted REST-API ready to respond to your AJAX requests. It's perfect for testing and prototyping with real
API responses without needing to set up your own backend.

The API provides realistic data and follows REST conventions, making it ideal for practicing API testing scenarios.

### Available Endpoints

- [x] Users: GET, POST, PUT, DELETE operations for user management
- [x] Authentication: Login and registration endpoints
- [x] Resources: Generic resource CRUD operations
- [x] Delayed Responses: Test timeout and loading scenarios
- [x] Pagination: Practice handling paginated API responses

### Testing Scenarios to Practice

- [x] User Management: Create, read, update, and delete users
- [x] Authentication Flow: Test login/register with valid and invalid credentials
- [x] Data Validation: Test required fields, data types, and formats
- [x] Response Validation: Verify status codes, headers, and response structure
- [x] CRUD Operations: Full create-read-update-delete workflow testing
- [x] Edge Cases: Test with invalid IDs, missing data, and error scenarios

### Key Features for Testing

- [ ] No Setup Required: Ready-to-use API with ~~realistic responses~~ (works without api key sometimes; register works
  with hard-coded user only)
- [x] Realistic Data: User profiles with avatars and personal information
- [x] Proper HTTP Status Codes: 200, ~~201~~, 204, 400, 404 responses (no endpoint returned with 201 status code)
- [x] Delayed Responses: Test timeouts with ?delay=3 parameter
- [X] CORS (Cross-Origin Resource Sharing) Enabled: Perfect for frontend testing and AJAX requests

<br/>

## [Ndosi Automation](https://www.ndosiautomation.co.za/API/swagger-ui.html)

The Ndosi Automation API is part of the Ndosi Automation learning platform. Check the API documentation to explore the
available endpoints and understand what testing scenarios you can practice.

Visit the Swagger documentation below to see the actual API structure and available operations.

## Getting Started

- [x] Review the API documentation using the link below
- [x] Explore the available endpoints and their parameters
- [x] Set up your testing environment (Postman, Insomnia, etc.)
- [x] Create test cases based on the documented endpoints
- [x] Practice different HTTP methods and response validation

## Testing Best Practices

- [x] Test both successful and error scenarios
- [x] Validate response status codes and data formats
- [x] Practice request chaining where applicable
- [x] Test with different input parameters and edge cases
- [x] Document your test cases and expected results

<br/>