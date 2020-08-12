FROM adoptopenjdk/openjdk11:alpine-jre
RUN ./mvnw clean package
COPY target/mocker.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
