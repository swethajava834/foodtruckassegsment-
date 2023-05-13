# Food Truck Assessment

FoodTruckAssessment is a web application API built to provide various endpoints to provide information about food trucks.

## Dependencies

To run this application, you will need to have the following installed:

- Docker (Docker desktop should be installed to run docker-compose)
- Java 17 (or Higher version)
- Maven 3.8.7
- Spring Boot 3.0.6

## Running the Application

To run the application,

Step 1: execute `docker-compose up --build`.
Step 2: visit for API documentation at http://localhost:8080/swagger-ui/index.html.

If you run `docker-compose down` all existing schemas will be removed and data will be removed.

You can skip running test by `mvn package -DskipTests`