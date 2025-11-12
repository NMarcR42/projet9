FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/frontend-0.0.1-SNAPSHOT.jar frontend.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "frontend.jar"]
