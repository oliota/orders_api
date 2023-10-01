# Step 4: Configuring application.properties

## Configuring Database Connection in application.properties

1. **Open `application.properties`**: Open the `application.properties` file in your Spring Boot project.

2. **Database Connection Properties**: Configure the database connection properties in the `application.properties` file:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/api_2023_pt
    spring.datasource.username=postgres
    spring.datasource.password=postgresql
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

[<< Back](../database-configuration.md)