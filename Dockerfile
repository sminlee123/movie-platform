FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY *.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]