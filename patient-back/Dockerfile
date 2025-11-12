FROM eclipse-temurin:17-jdk

ENV JAVA_OPTS=""

WORKDIR /app

COPY target/patient-back-0.0.1-SNAPSHOT.jar patient-back.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar patient-back.jar"]
