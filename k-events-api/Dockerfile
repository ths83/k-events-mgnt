# Build the application
FROM maven:3.8.4-openjdk-11-slim AS BUILDER
COPY . .
RUN mvn clean install

# Build the image
FROM openjdk:11.0-jre-slim-buster
LABEL maintainer="Thomas Perron"
LABEL version="1.0.0"
LABEL name="k-events-api"
COPY --from=BUILDER target/k-events-api-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]