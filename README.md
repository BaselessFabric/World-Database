<h1>World-DB-Spring-REST</h1>
World-DB-Spring 'Elite Seven' consisting of Howard, Patrick W, Patrick S, Selam, Alistar, Alex and Phoenix 👋.

## Project Overview
This project creates a Java applicaiton that uses an SQL database which contains a list of countries and cities, along with other details.
This applicaiton allows users to query certain fields within the database, while following the spring architecture layout: Entities->Repository->Service->Controller
We implement RestAPIs and endpoints to allow the following CRUD methods.

- POST /city: Adds a new city.
- GET /cities: Retrieves all cities.
- GET /city/{id}: Retrieves a city by its ID.
- GET /city/name: Retrieves cities by their name.
- PUT /city/{id}: Updates an existing city.
- DELETE /city/{id}: Deletes a city by its ID.
- The above is similar for the countries controller

## Acceptance Criteria
- Interact with the MySQL World Database
- Use Spring JPA to connect and communicate with the Database
- Use basic CRUD operations
- Provide multiple types of search methods
- Implement the service layer in your application
- Tested with WebMVCTests
- GUI to be provided via swaggar
- Secure endpoints wiht an API
- Error handling of API endpoints


## Dependencies
JDK 21, JUnit, Mockito, SpringBoot, Spring Reactive Web, Rest Repositories, MariaDB Driver

## File Structure
```
main
│
├── java
│   └── org.example.dungeonsanddebugerss
│       ├── controllers
│       ├── model
│       ├── service
│       └── DungeonsAndDebugerssApplication
│
├── resources
│   ├── application.properties
│   └── banner.txt
│
test
│
├── java
│   └── org.example.dungeonsanddebugerss
│       ├── controller
│       ├── service
│       └── DungeonsAndDebugerssApplicationTest
│
target
│
.gitignore
```


## How to Fork the Project

Setup: Ensure you have Java installed on your system. 

    Fork this repository
    Clone the forked repository and import it into yout preferred Java IDE
    Add your contributions (code or documentation)
    Commit and push
    Wait for pull request to be merged

Adding pom.xml
You will need to create your own pom.xml file which needs to contain the following
[Spring Web]
[Spring Reactive Web]
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

```

## How to use the Program 

Open the project directory: "Dungeons and Debuggerss" and open the class "App". Ensure the spring boot application is running:

```
  public static void main(String[] args) {
        SpringApplication.run(DungeonsAndDebugerssApplication.class, args);
    }
```
```
          <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.1.0</version>
        </dependency>
```
In order for swagger to work with Spring WebFlux application ensure that springdoc-openapi-webflux depdency is inside the pom.xml file. For more information on how to install swagger onto your project please use the following guide:
<br></br>
https://www.baeldung.com/spring-rest-openapi-documentation
<br></br>
![image](https://github.com/HowardC04/World-DB-Spring-REST/assets/167005819/c05ca051-cc18-4ec0-9a58-092332eeaff1)

Within the Swagger you can see that we have our crud methods grouped togther. Swaggar allows users to input endpoints to see how the server responds, it will include the status codes, response body and response headers
When using Swagger UI it shows the curl that was submitted and the response section shows the JSON response from the
endpoints on the tomcat server.


In addition to this swagger allows you to input an API key to test any authorization filters within the server.

To enhance maintainability we created logging functionality using java.util.logging. Our colour-coded logger allows you to easily track the flow of the program, record the state when an important event happens and capture errors or exceptions that occur during runtime. This can be used through the Log class and it's static methods.


##  

📫 If you encounter any bugs, please open up an issue to let us know.
Alternatively, we welcome suggestions for any updates or improvements you would like to see! 
