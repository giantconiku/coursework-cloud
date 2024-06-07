FROM openjdk:17-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY target/*.jar user-portal-app.jar
ENTRYPOINT ["java","-jar","user-portal-app.jar"]