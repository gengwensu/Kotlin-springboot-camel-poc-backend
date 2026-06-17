# Architecture

## Overview

`sub-mgmt-aggregator` is a Spring Boot + Kotlin backend service intended to act as an aggregation and orchestration layer for subscription-management workflows.

The service is currently a proof of concept, but it is structured with a production-oriented direction:

* Spring Boot as the application framework
* Kotlin as the primary implementation language
* Java 21 as the runtime baseline
* MySQL 8.4 LTS as the target relational database
* Flyway for database migrations
* Apache Camel for integration/orchestration workflows
* Docker/Rancher Desktop for local infrastructure
* GitHub Actions for CI/CD
* Testcontainers for future integration testing

## Architectural Intent

The service is intended to sit between client-facing applications and multiple backend systems.

```text
Client / Frontend / API Consumer
        |
        v
sub-mgmt-aggregator
        |
        +-- Subscription systems
        +-- Payment provider
        +-- Identity / SSO provider
        +-- Customer profile systems
        +-- Notification systems
        +-- Reporting / analytics systems
```

The aggregator should not become a large monolith. Its purpose is to coordinate workflows, normalize responses, and hide backend complexity from API consumers.

## Responsibilities

The service may eventually be responsible for:

* Subscription lookup
* Subscription lifecycle orchestration
* Payment-related integration
* Customer/account aggregation
* External system coordination
* API response normalization
* Workflow-level validation
* Audit and operational logging
* Observability and health reporting

## Non-Responsibilities

The service should avoid owning responsibilities that belong elsewhere:

* It should not become the source of truth for all subscription data.
* It should not replace dedicated payment systems.
* It should not duplicate identity-provider functionality.
* It should not contain frontend-specific presentation logic.
* It should not perform long-running batch processing unless explicitly designed for it.

## Proposed Layering

```text
controller
    |
service
    |
orchestration / camel routes
    |
client / adapter
    |
external systems
```

Suggested package structure:

```text
com.hnp.subscription.aggregator
├── config
├── controller
├── service
├── domain
├── repository
├── client
├── camel
├── dto
└── exception
```

## API Layer

Controllers should be thin.

They should handle:

* HTTP request mapping
* Input validation
* Authentication context handoff
* Response status mapping

They should not contain orchestration or business workflow logic.

## Service Layer

Services should contain business-level use cases.

Example:

```text
SubscriptionAggregationService
PaymentAuthorizationService
CustomerSubscriptionService
```

The service layer should coordinate domain logic and delegate integration concerns to Camel routes or client/adapters.

## Camel Integration Layer

Apache Camel should be used where routing, transformation, orchestration, retries, or integration patterns add value.

Good Camel use cases:

* Calling multiple backend systems
* Routing based on subscription state
* Transforming external payloads
* Retrying transient integration failures
* Coordinating payment/subscription workflows
* Integrating with queues or event systems

Avoid Camel for simple one-step CRUD operations where a normal Spring service is clearer.

## Persistence

MySQL 8.4 LTS is the target local and cloud-compatible database baseline.

Flyway should own schema migration history.

Migration files should live under:

```text
src/main/resources/db/migration
```

Example:

```text
V1__create_subscription_tables.sql
V2__add_payment_reference.sql
V3__add_subscription_indexes.sql
```

Hibernate should validate schema in stable environments rather than generate it automatically.

Recommended setting:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

## Configuration

Prefer YAML for new configuration.

Recommended files:

```text
application.yml
application-local.yml
application-dev.yml
application-test.yml
application-prod.yml
```

Secrets should not be committed to source control.

Local secrets can be supplied through environment variables or ignored local override files.

## Testing Strategy

Recommended test layers:

```text
Unit tests
    Fast tests for services, mappers, validators, and domain logic

Spring slice tests
    Controller and repository tests with focused Spring context loading

Integration tests
    Testcontainers-backed tests using MySQL 8.4

End-to-end workflow tests
    Future tests for complete subscription orchestration flows
```

The basic `contextLoads()` test should stay lightweight and should not depend on a running database unless explicitly configured.

## Local Development

Expected local flow:

```text
Rancher Desktop / Docker-compatible runtime
        |
docker compose
        |
MySQL 8.4
        |
Spring Boot app
```

Typical commands:

```bash
./gradlew clean build
./gradlew bootRun
docker compose up -d
```

## CI/CD Direction

Initial GitHub Actions workflow should:

1. Check out source
2. Set up Java 21
3. Run Gradle build
4. Run unit tests
5. Build application artifact

Later workflow stages may include:

1. Build Docker image
2. Push image to Amazon ECR
3. Deploy to ECS or EKS
4. Run smoke tests
5. Publish deployment status

## Deployment Direction

Potential AWS deployment targets:

* ECS Fargate for simpler container deployment
* EKS if Kubernetes orchestration becomes necessary
* RDS MySQL 8.4 or Aurora MySQL-compatible 8.4 for database

Initial preference should be the simplest deployment model that satisfies operational requirements.

## Observability

The service should expose standard Spring Boot Actuator endpoints.

Recommended future observability stack:

* Actuator health checks
* Structured JSON logs
* Micrometer metrics
* OpenTelemetry tracing
* CloudWatch, Prometheus, Grafana, or equivalent platform tooling

## Security Direction

Security is expected to evolve toward OAuth2/OIDC integration.

Likely future dependencies:

* Spring Security
* OAuth2 Client
* OAuth2 Resource Server

Security should be introduced deliberately after the basic application, database, and integration patterns are stable.

## Key Architecture Decisions

| Decision            | Current Direction                             |
| ------------------- | --------------------------------------------- |
| Language            | Kotlin primary, Java allowed during migration |
| Runtime             | Java 21                                       |
| Framework           | Spring Boot 3.5.x                             |
| Database            | MySQL 8.4 LTS                                 |
| Migration Tool      | Flyway                                        |
| Build Tool          | Gradle Kotlin DSL                             |
| Integration Layer   | Apache Camel where useful                     |
| Local Containers    | Rancher Desktop / Docker-compatible runtime   |
| CI/CD               | GitHub Actions                                |
| Integration Testing | Testcontainers                                |

## Open Questions

* Should this service be deployed to ECS or EKS?
* Which backend systems are in scope for the first real workflow?
* Is Camel required for the first workflow, or should it be introduced later?
* What is the target authentication provider?
* Should the service expose internal APIs only or public client-facing APIs?
* What operational SLAs are expected?
* What data, if any, should this service own locally?
