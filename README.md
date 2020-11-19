# Warehouse API

Simple RESTful API implementation using Java [Spring Boot](http://projects.spring.io/spring-boot/).
A software for managing products and articles which they are made from.
## Functionalities:
- List products with quantity based on articles stock.
- List articles
- Import products from file
- Import articles from file
- Sell products

## Requirements
For building and running the application you need:

- [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven 3](https://maven.apache.org)

## Build project 
`mvn clean install`

## Build without tests
`mvn clean install -DskipTests`

## Run project
`mvn spring-boot:run`
___
### See [Swagger UI](http://localhost:8080/swagger-ui.html) on your localhost for the API docs
