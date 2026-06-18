# Business Context

## Overview

The subscription platform is driven primarily by Marketing requirements rather than technical configuration needs.

Marketing teams need the ability to manage:

* Subscription plans
* Pricing
* Promotions
* Campaign-specific offerings
* Product-to-plan mappings
* Business configuration related to customer subscriptions

These configurations are currently stored in MySQL and managed through Directus, which provides a web-based administrative interface for non-technical users.

## Current Architecture

```text
Marketing Users
        ↓
     Directus
        ↓
      MySQL
        ↓
 Spring Boot Services
        ↓
 Customer-facing APIs
```

### Key Characteristics

* Marketing can manage subscription-related data without engineering involvement.
* Configuration data is persisted in MySQL.
* Directus provides automatic CRUD interfaces, permissions, and APIs.
* Spring Boot services consume the configuration data to drive subscription and pricing behavior.
* The platform has been operating successfully for more than 10 years with minimal maintenance effort.

## Directus Assessment

Directus has proven to be a stable and effective solution for this use case.

Benefits include:

* Existing MySQL integration
* Minimal operational overhead
* Role-based access control
* Auditability
* REST and GraphQL APIs
* Self-service capabilities for business users
* No need to build or maintain a custom administrative application

Although Directus licensing has changed in recent years, the platform continues to provide free usage through the v9.x release line. Given the long operational history and low maintenance burden, there is currently no strong business justification for replacing it.

## Why Spring Cloud Config Is Not a Good Replacement

Spring Cloud Config Server solves a different problem.

It is intended for application configuration such as:

* Service endpoints
* Environment-specific settings
* Feature flags
* Cache settings
* Timeouts
* Infrastructure configuration

Examples:

```yaml
pricing:
  cache-ttl: 60s

feature:
  promotion-engine-enabled: true
```

In contrast, subscription plans, promotions, and pricing are business-managed data:

```text
Plan Premium = $19.99/month
Promotion SUMMER25 = 20% discount
Effective July 1 – July 31
Eligible Customer Segment = New Customers
```

These business entities require:

* Relational data modeling
* Effective dates
* Business validation
* User-friendly editing
* Audit history
* Role-based access
* Queryability
* Potential future approval workflows

These capabilities are naturally supported by a database-backed solution and are not the primary purpose of Spring Cloud Config.

### Recommended Separation of Concerns

```text
Spring Cloud Config
    = How the application runs

MySQL + Directus
    = What the business sells
```

## Alternative Solutions Considered

Several alternatives were evaluated:

### Appsmith

Open-source internal application platform.

Pros:

* Flexible UI development
* Strong integration capabilities

Cons:

* Requires building and maintaining custom administrative screens

### Budibase

Open-source internal tools platform.

Pros:

* Fast CRUD generation
* Self-hosted

Cons:

* Additional platform to learn and maintain

### NocoDB

Open-source Airtable alternative.

Pros:

* Simple spreadsheet-style user experience

Cons:

* Less suitable for complex relational business models

### Custom Admin Portal

Spring Boot + React/Next.js administration application.

Pros:

* Complete flexibility

Cons:

* Significant development and maintenance effort

## Strategic Recommendation

Continue using:

```text
Spring Boot
+ MySQL
+ Flyway
+ Directus
```

Focus engineering effort on higher-value business capabilities such as:

* Subscription lifecycle management
* Pricing and promotion engines
* Validation rules
* API design
* Security
* Observability
* Operational reliability

Directus should remain the administrative layer unless one of the following becomes true:

1. Marketing workflows become significantly more complex.
2. Licensing requirements become incompatible with organizational policies.
3. The business requires a highly customized user experience that generic administrative platforms cannot provide.

Given the platform's decade-long stability and low maintenance costs, the current architecture remains a pragmatic and cost-effective solution.
