# Logging aggregation and Logback Configuration 

This project serves as a practical example for understanding how a logging aggregation system pipeline works in a distributed runtime environment
and Logback configuration in the context of a Spring-boot application.
The configuration set in this example is for learning purposes and should not be used in a production environment.

This repository contains the source code for two articles, which provides a more detailed explanation:
-**[Undestanding Logback](https://medium.com/@facuramallo8/understanding-logback-66044df087ed)**
-**[Logging aggregation system](https://medium.com/@facuramallo8/logging-aggregation-system-d94f60f92dd0)**

## How to run it

To build and run this project, ensure you have the following installed:
- Java 17
- Docker and Docker-Compose V2

### Step 1
Before running the project, build the `fluentd` image for the first time by executing the following commands:

- `cd fluentd-image-build`
- `docker build -t custom-fluentd:latest ./`

These commands generate the image with all the necessary plugins for the project. 
If interested, inspect the files inside the "fluentd-image-build" folder or refer to the following links for more information:
- [Fluentd-docker-image](https://github.com/fluent/fluentd-docker-image)
- [Fluentd Docs](https://docs.fluentd.org/)

### Step for the curious ones
For a deeper understanding of how to configure Logback, consider modifying the files
**logback-access-spring.xml** or **logback-spring.xml** located inside the resources package 
- Refer to my article **[Undestanding Logback](https://medium.com/@facuramallo8/understanding-logback-66044df087ed)** to lear more about how to configure Logback.
Modify what you want to later see the result in Kibana. 

### Step 2
The first time you run the project or whenever you make changes to the source code, generate the ``.jar``
files needed to execute the spring-boot application in a docker container. To do it, you can use the gradle task `bootBuildImage` running the command:

- `./gradlew bootBuildImage`

You can customize this task by adding a code block to the '**build.gradle.kts**' file:

```
 tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "logging_example_app"
    environment.apply {
        put("BP_JVM_VERSION","17")
        put("spring.profiles.active","k8s")
    }
 }
```
### Step 3
- Run the following commands: 
  `docker-compose up`
- Wait for all the containers to be ready and running
- Make a **GET** request to  `http://localhost:8080/log`
- Open your browser and go to  `http://localhost:5601` and follow the steps in the last part of my article [Common Platform Dashboard](https://common-platform.mpi-internal.com/applications/es-microfc/scmspain/ms-re--ads-publication-api)
to see the logs in Kibana


## How to run the tests

To compile the project and run all the tests execute:  
`./gradlew build`

