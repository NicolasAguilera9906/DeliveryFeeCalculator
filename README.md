# DeliveryFeeCalculator

## Content

  - [Prerequisites](#Prerequisites)
  - [Execute](#execute)
  - [Use](#use)
  - [Diagrams](#diagrams)
  - [Tests](#tests)
  - [Javadoc generation](#Generation of Javadoc)
  - [Author](#author)
  - [License](#license)

## Prerequisites

* [Gradle](https://gradle.org/) - Dependency Management

* [Java 8](https://www.oracle.com/co/java/technologies/javase/javase-jdk8-downloads.html) -  Development Environment 

* [Git](https://git-scm.com/) - Version Control System

* [Postman API Platform](https://www.postman.com/) - API platform for building and using APIs

## Execute

1. Build the project

```
gradle build
```

2. Runs this project as a Spring Boot application.

```
gradlew bootrun
```

4. Generating the documentation

```
gradlew javadoc
```

## Use

1. [Execute](#Prerequisites) the project

2. Open postman 

3. Create a new Post request and enter the following endpoint 

```
http://localhost:8080/deliveries/fees/calculate
```

![app](resources/img/postmanRequest.png) 

3 Enter the Delivery information in a request payload (JSON) in the body of the request.

![app](resources/img/postmanJSON.png) 

4. Send the request and you will get a JSON response with the result of the fee calculation.

![app](resources/img/postmanResult.png)





