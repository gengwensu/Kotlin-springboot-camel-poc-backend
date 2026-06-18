# Subscription Management Aggregator

Proof of concept for a Spring Boot + Kotlin backend service that acts as an aggregation and orchestration layer for subscription-management workflows.

## Goals

* Evaluate Kotlin adoption within a Spring Boot ecosystem
* Establish a modern development workflow using Gradle Kotlin DSL
* Build an aggregation layer for subscription management workflows
* Validate MySQL 8.4 LTS compatibility with Docker Compose
* Enable local development with Rancher Desktop or Docker Desktop
* Support Testcontainers for integration testing
* Prepare for future Apache Camel integration and orchestration patterns
* Prepare for future deployment to AWS (ECS/EKS)
* Establish CI/CD practices using GitHub Actions

## Technology Stack

| Component                | Version  |
| ------------------------ | -------- |
| Java                     | 21       |
| Kotlin                   | 1.9.25   |
| Spring Boot              | 3.5.15   |
| Gradle                   | 8.x      |
| MySQL                    | latest   |
| Flyway                   | (Spring) |
| Testcontainers           | (Spring) |
| Docker / Rancher Desktop | Current  |
| GitHub Actions           | Current  |

## Project Structure

```text
submgmt-aggregator/
├── build.gradle.kts
├── settings.gradle.kts
├── compose.yaml
├── docs/
│   └── architecture.md
└── src/
    ├── main/
    │   ├── kotlin/
    │   │   └── com/hnp/submgmt/
    │   │       └── SubMgmtApplication.kt
    │   └── resources/
    │       ├── application.yaml
    │       └── db/migration/
    └── test/
        └── kotlin/
            └── com/hnp/submgmt/
                ├── PocApplicationTests.kt
                ├── TestPocApplication.kt
                └── TestcontainersConfiguration.kt
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

Local database is provided via Docker Compose:

```bash
docker compose up -d
```

This starts a MySQL container configured in `compose.yaml`:

```text
MySQL: latest
Database: mydatabase
User: myuser
Password: secret
Port: 3306
```

Database migrations are managed through Flyway.

Migration scripts location:

```text
src/main/resources/db/migration/
```

Spring Boot Docker Compose support automatically detects and configures connection to the containerized MySQL instance.

## Testing

Run all tests:

```bash
./gradlew test
```

The project includes:

* Basic context load test (`PocApplicationTests.kt`)
* Testcontainers configuration for MySQL-backed integration tests
* Test-specific Spring Boot application configuration

Integration tests use Testcontainers to spin up MySQL containers automatically.

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

* Apache Camel routes for workflow orchestration
* REST API controllers for subscription operations
* JPA entities and repositories for persistence
* Flyway database migrations
* OAuth2 / OIDC authentication
* AWS deployment pipeline (ECS or EKS)
* OpenTelemetry observability
* GitHub Actions CI/CD
* Integration tests using Testcontainers
* Contract testing

See `docs/architecture.md` for detailed architectural direction.

## Documentation

* [Architecture](docs/architecture.md) - Detailed architectural decisions and design direction

## References

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Kotlin](https://kotlinlang.org/)
* [Apache Camel](https://camel.apache.org/)
* [Flyway](https://flywaydb.org/)
* [Testcontainers](https://testcontainers.com/)
* [GitHub Actions](https://github.com/features/actions)
* [AWS ECS](https://aws.amazon.com/ecs/) / [EKS](https://aws.amazon.com/eks/)

