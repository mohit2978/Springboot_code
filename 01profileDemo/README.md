# 01profileDemo

This Spring Boot application demonstrates how the same variables receive different values from different profiles.

## Profiles

| Profile | Environment | Message | Database URL | Feature enabled |
|---|---|---|---|---|
| default | default | Hello from the default profile | `jdbc:h2:mem:defaultdb` | false |
| dev | development | Hello from the DEV profile | `jdbc:h2:mem:devdb` | true |
| test | testing | Hello from the TEST profile | `jdbc:h2:mem:testdb` | true |
| prod | production | Hello from the PROD profile | `jdbc:postgresql://prod-server:5432/profiledb` | false |

The values are defined in `src/main/resources/application.properties` and the three `application-{profile}.properties` files.

## Run the application

From the parent `Springboot_code` folder, enter the project folder first:

```powershell
cd .\01profileDemo\
```

Run `01profileDemo` with the default profile:

```powershell
mvn spring-boot:run
```

Run `01profileDemo` with a selected profile in PowerShell (keep the complete `-D` argument inside quotes):

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
mvn spring-boot:run "-Dspring-boot.run.profiles=test"
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

In Command Prompt, macOS, or Linux shells, the quotes are optional.

Alternatively, after `mvn package`, run the JAR with:

```shell
java -jar target/profile-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

Open <http://localhost:8080/profile> to see the active profile and its resolved values as JSON.

## Run the test

```shell
mvn test
```
