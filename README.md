# Simple Library Management System

This project is a simple library management application. 
## Used technologies:
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok Annotation

## Requirements
For building and running the application you need:
- JDK 
- Lombok
- Maven

## Setup
- Open in your IDE of choice able to run Java (preferably IntellijIDEA), navigate to src/main/com/library/system/LibrarySystemApplication.java to find the main method to run. Project is set to run on port 8080. Language is set to Java 11
- After you run the project one way or another, open your browser and URL http://localhost:8080/h2-console.
- Login to H2 database with these details:
  ![](additional/console.PNG)
- You should then be able to execute queries on tables created in H2 in-memory database as you go along.
  ![](additional/console2.PNG)

## API
- As per your requirements, I have created 4 endpoints:
1. Initial request that should populate the database with values for testing: GET http://localhost:8080/populate
   ![](/additional/request1.PNG)
2. Endpoint accepting new books into database: POST http://localhost:8080/api/v1/book with JSON body as example:
   ![](/additional/request2.PNG)
3. Endpoint to delete book from database: DELETE http://localhost:8080/api/v1/book with JSON body as example:
   ![](/additional/request3.PNG)
4. Endpoint for user to loan a book: POST http://localhost:8080/api/v1/loanbook/1 with JSON body as example:
   ![](/additional/request4.PNG)
5. Endpoint for user to return a book: POST http://localhost:8080/api/v1/loanbook/1 with JSON body as example:
   ![](/additional/request5.PNG)

## REST Client
- I used Firefox plugin RESTClient for testing, I have included the RESTClient_dump.json file that you can import in your Firefox RESTClient plugin to test the API with ease.
