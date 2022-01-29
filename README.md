# Software Engineering Summer 2022 Internship / Berlin
## DeliveryFeeCalculator - BackEnd (Java)

Program to calculate the delivery fee. This code is needed when a customer is ready with their shopping cart and we’d like to show them how much the delivery will cost. The delivery price depends on the cart value, the number of items in the cart, the time of the order, and the delivery distance.

## Content

  - [Prerequisites](#Prerequisites)
  - [Execute](#execute)
  - [Use](#use)
  - [Tests](#tests)
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

![app](resources/img/postmanOpen.JPG) 

3 Enter the Delivery information in a request payload (JSON) in the body of the request. Here is an example : 

```
{"cart_value": 790, "delivery_distance": 2235, "number_of_items": 4, "time": "2021-10-12T13:00:00Z"}
```

![app](resources/img/postmanRequest.JPG) 

4. Send the request and you will get a JSON response with the result of the fee calculation.

![app](resources/img/postmanResult.JPG)

## Tests

1. To run the unit tests

```
gradlew test
```

2. 19 unit tests were performed

![app](resources/img/testResult.JPG)

3. The application's services have a coverage of more than 90%. This report was made with [Jacoco](https://www.eclemma.org/jacoco/)

![app](resources/img/jacocoReport.JPG)

## Built with

* [Gradle](https://gradle.org/) - Dependency Management

## Author

* Nicolás Aguilera Contreras

## License

This project is under GNU General Public License - see the [LICENSE](LICENSE) file for details










