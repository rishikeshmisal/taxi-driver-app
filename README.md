# microservice-taxi-driver-app

A microservice to provide APIs to simulate Taxi Driver allocation app.
With the ability to register users, driver and assign drivers to users on demand


### Pe-requisites
 - Java 1.8
 - Maven 3+

You can build the application using 
```
mvn clean install -DskipTests
```

- If the project is zipped, unzip the file and perform the following

- There are two ways to run the project
     - To create an embedded postgres server within the application if a
     configured postgres database server is not readily available.
        ```
        java -jar -Dspring.profiles.active=embedded target/taxi-driver-0.0.1.jar 
        ```
     - The other way is to have a dedicated postgres database outside of the application.
     You will require the following environment variables to be configured with the appropriate values.
        ```
         export DB_USERNAME=postgres
         export DB_PASSWORD=postgres
         export DB_CONN_URL=jdbc:postgresql://localhost:5432/postgres
        ```
        Subsequently run the following command
        ```
        java -jar -Dspring.profiles.active=cloud target/taxi-driver-0.0.1.jar 
        ```
- Note flyway database versioning is used to create application database with either externally configured postgres instance or 
using embedded postgres

- The project also contains a Postman Collection with all the endpoints.

- Swagger-2.0 is also enabled and can be access from `/swagger-ui.html` request path.