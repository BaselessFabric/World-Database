<h1>World-DB-Spring-REST</h1>
World-DB-Spring 'Stranger Strings' consisting of Murad, Alex, Oliver, Imogen, Patrick, Irina and Daniell 👋.

## Project Overview
This project creates a Java application that uses an SQL database which contains a list of countries and cities, along with other details.
This application allows users to query certain fields within the database, while following the spring architecture layout: Entities->Repository->Service->Controller
We implement RestAPIs and endpoints to allow the following CRUD methods.

- GET /web/cities/add-city: Adds a new city.
- GET /web/cities: Retrieves all cities.
- GET /web/cities/{id}: Retrieves a city by its ID.
- GET /web/cities/update/{id}: Updates an existing city.
- GET /web/cities/delete/{id}: Deletes a city by its ID.
- The above is similar for the countries and countrylanguage controller

## Acceptance Criteria
- Interact with the MySQL World Database
- Use Spring JPA to connect and communicate with the Database
- Use basic CRUD operations
- Provide multiple types of search methods
- Implement the service layer in the application
- Tested with WebMVCTests
- Secure endpoints wiht an API
- Error handling of API endpoints
- Front end website to interact with World Database


## Dependencies
JDK 21, JUnit, Thymeleaf, SpringBoot, Spring Security, Spring Reactive Web, Rest Repositories, MySQL DB Driver

## File Structure
```
main
│
├── java
│   └── org.example.worlddb
│       ├── config
│       ├── controllers
│       ├── model
│       ├── service
│       └── WorldDbApplication
│
├── resources
│   ├── application.properties
│   └── banner.txt
│
test
│
├── java
│   └── org.example.worlddb
│       ├── controller
│       ├── service
│       └── WorldDbApplicationTests
│
target
│
.gitignore
```


## How to Fork the Project

Setup: Ensure you have Java installed on your system. 

    Fork this repository
    Clone the forked repository and import it into your preferred Java IDE
    Add your contributions (code or documentation)
    Commit and push
    Wait for pull request to be merged

Adding pom.xml
You will need to create your own pom.xml file which needs to contain the following
[Spring Web]
[Spring Reactive Web]
[Thymelead]
[Spring Security]
[Rest Repositories]
[Spring Hateoas]
[JDBC API]
[Spring Data JPA]
[Validation]
[Spring Boot Actuator]
[MySQL Driver or the driver for your database]

<h2>Connecting to your database</h2>


To connect to your database please fill out the following in your application.properties file. In addition, ensure that you have the World databse on your local computer.
```
spring.datasource.url=jdbc:mysql://localhost:3306/world
spring.datasource.username=<YOUR USERNAME>
spring.datasource.password=<YOUR PASSWORD>
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.ddl-auto=update

```

## How to use the Program 

Open the project directory: "WorldDB" and open the class "App". Ensure the spring boot application is running:

```
  public static void main(String[] args) {
        SpringApplication.run(WorldDbApplication.class, args);
    }
```
📫 If you encounter any bugs, please open up an issue to let us know.
Alternatively, we welcome suggestions for any updates or improvements you would like to see! 
