# Spring Boot Demo Projects

This repository contains small Spring Boot projects that demonstrate different Spring Boot concepts. Each project is stored in its own numbered folder and includes its own README with project-specific instructions.

## Projects

### 01profileDemo

Demonstrates Spring Boot profiles and shows how the same application variables can have different values in the `default`, `dev`, `test`, and `prod` environments.

The application provides a `/profile` endpoint that displays the active profile and its resolved configuration values.

See [01profileDemo/README.md](01profileDemo/README.md) for the complete example and profile values.

## Prerequisites

Install the following before running the projects:

- Java 17 or later
- Apache Maven

Confirm that they are available:

```powershell
java -version
mvn -version
```

## Running a project

From the repository folder, enter the project you want to run:

```powershell
cd .\01profileDemo\
```

Run it with its default configuration:

```powershell
mvn spring-boot:run
```

Run it with a specific Spring profile in PowerShell:

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
mvn spring-boot:run "-Dspring-boot.run.profiles=test"
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

Keep the complete `-D` argument inside quotes in PowerShell. The selected profile tells Spring Boot which `application-{profile}.properties` file to load in addition to the base `application.properties` file.

After the application starts, open <http://localhost:8080/profile> to view the active profile and configuration values.

## Command explanations

| Command | Explanation |
|---|---|
| `cd .\01profileDemo\` | Moves the terminal into the selected project folder. |
| `mvn spring-boot:run` | Compiles and starts the Spring Boot application using Maven. |
| `"-Dspring-boot.run.profiles=dev"` | Passes `dev` to Spring Boot as the active profile. Replace `dev` with `test` or `prod` when needed. |
| `mvn test` | Compiles the project and runs its automated tests. |
| `mvn clean package` | Deletes old build output, runs the tests, and creates an executable JAR inside the `target` folder. |
| `java -jar target/profile-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev` | Runs the packaged JAR with the `dev` profile. |

Stop a running application by pressing `Ctrl+C` in its terminal.
