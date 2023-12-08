### How to build and run the application

- In order to test/build/package all the modules, you can execute the intended action from root/parent folder e.g. bash terminal from root folder `./mvnw clean install`
- In order to start the spring boot application, you can execute from application folder `../mvnw spring-boot:run`
- For simplicity reasons, E2E tests are included as part of application testing, therefore are executed in that phase. You can only execute E2E tests from application folder using `../mvnw test -Dtest=*E2ETest`
- To build and push docker image using JIB from maven, you can execute from application folder `../mvnw compile jib:build -Djib.to.auth.username=username -Djib.to.auth.password=pass -Djib.to.image=alexmartinezvila/products-prices:latest`
