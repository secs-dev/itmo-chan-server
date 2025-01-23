FROM gradle:7.4.2-jdk17 as builder

WORKDIR /app

COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle.kts /app/
COPY settings.gradle.kts /app/
COPY src /app/src

RUN chmod +x gradlew

RUN ./gradlew build

FROM openjdk:17

WORKDIR /app

COPY ./build/libs/ItmochanBackend.jar .

CMD ["java","-jar", "ItmochanBackend.jar"]

EXPOSE 8080