## Requirements

Project contains a maven wrapper, so there is no need for a maven installation.
It only requires java to be installed. Source code has been developed on java 21.
Application can be packed as a Docker Image without the need of a Docker installation. It's packed using maven JIB plugin.   

## How to build and run the application

- In order to test/build/package all the modules, you can execute the intended action from root/parent folder on a terminal: e.g. `./mvnw clean install`
- In order to start the spring boot application, you can execute from the application folder: `../mvnw spring-boot:run`
- For simplicity reasons, E2E tests are included as an application tests, therefore those are executed in that phase. You can execute only the E2E tests from application folder using `../mvnw test -Dtest=*E2ETest`
- To build and push docker image using JIB from maven, you can execute from application folder `../mvnw compile jib:build -Djib.to.auth.username=username -Djib.to.auth.password=pass -Djib.to.image=alexmartinezvila/products-prices:tag`

### H2 Console is enabled in -> http://localhost:8080/h2-console 
    URL: jdbc:h2:mem:db
    User: sa
    Keep password empty


### Swagger UI -> http://localhost:8080/swagger-ui/index.html

Postman collection is also available in postman folder.