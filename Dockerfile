FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/spring-boot-postgresql-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-boot-postgresql-0.0.1-SNAPSHOT.jar"]