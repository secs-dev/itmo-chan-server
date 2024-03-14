FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY ./build/libs/ItmochanBackend.jar .
CMD ["java","-jar", "ItmochanBackend.jar"]
