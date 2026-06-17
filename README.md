# Kotlin Spring Boot Camel POC Backend

Proof of concept project for evaluating a modern Spring Boot and Kotlin stack as a replacement or evolution path for existing Java-based microservices.

## Goals

* Evaluate Kotlin adoption within a Spring Boot ecosystem
* Establish a modern development workflow using Gradle Kotlin DSL
* Explore Apache Camel integration and orchestration patterns
* Validate MySQL 8.4 compatibility
* Enable local development with containers
* Prepare for future deployment to AWS (ECS/EKS)
* Establish CI/CD practices using GitHub Actions

## Technology Stack

| Component                | Version |
| ------------------------ | ------- |
| Java                     | 21      |
| Kotlin                   | 1.9.x   |
| Spring Boot              | 3.5.x   |
| Gradle                   | 8.x     |
| MySQL                    | 8.4 LTS |
| Flyway                   | Latest  |
| Testcontainers           | Latest  |
| Docker / Rancher Desktop | Current |
| GitHub Actions           | Current |

## Project Structure

```text
src/
├── main/
│   ├── kotlin/
│   │   └── com/hnp/poc
│   └── resources/
│       ├── application.yml
│       └── db/migration
└── test/
    └── kotlin/
```

## Prerequisites

* Java 21
* Rancher Desktop or Docker Desktop
* Git
* IntelliJ IDEA Ultimate or Community Edition

Verify installation:

```bash
java -version
docker version
```

## Build

```bash
./gradlew clean build
```

## Run

```bash
./gradlew bootRun
```

Application will be available at:

```text
http://localhost:8080
```

## Database

Planned local database:

```text
MySQL 8.4 LTS
```

Database migrations are managed through Flyway.

Migration scripts:

```text
src/main/resources/db/migration
```

## Testing

Run all tests:

```bash
./gradlew test
```

Run integration tests:

```bash
./gradlew integrationTest
```

(Planned)

## Development Workflow

```text
Feature Branch
    ↓
Local Build
    ↓
Unit Tests
    ↓
Git Commit
    ↓
GitHub Actions
    ↓
Container Build
    ↓
Deployment
```

## Future Enhancements

* Apache Camel routes
* OAuth2 / OIDC authentication
* AWS deployment pipeline
* OpenTelemetry observability
* Kubernetes support
* Testcontainers-based integration testing
* GitHub Actions CI/CD
* Contract testing

## References

* Spring Boot
* Kotlin
* Apache Camel
* Flyway
* Testcontainers
* GitHub Actions
* AWS ECS / EKS

