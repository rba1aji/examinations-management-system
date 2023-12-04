RUN chmod +x /app/gradlew
# Use the official Gradle image as a build stage
FROM gradle:7.3-jdk17 AS build
MAINTAINER balaji
ARG DB_URL
ARG DB_PWD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Set environment variables for Gradle build
ENV DB_URL=$DB_URL
ENV DB_PWD=$DB_PWD

RUN ./gradlew build --no-daemon

# Use a slim version of OpenJDK 17 as the final image
FROM eclipse-temurin:17-jdk-alpine
RUN apk --no-cache update && apk --no-cache add netcat-openbsd
RUN mkdir /app
RUN mkdir /app/logs
# Copy JAR from the build stage
COPY --from=build /home/gradle/src/build/libs/examination-management-system-0.0.1-SNAPSHOT.jar /app/examination-management-system.jar

WORKDIR /app

CMD ["java", "-jar", "examination-management-system.jar"]
EXPOSE 7001
