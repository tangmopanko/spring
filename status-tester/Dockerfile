FROM openjdk:17-ea-11-jdk-slim as builder 
WORKDIR app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17-ea-11-jdk-slim 
WORKDIR app
ENV port 8080
ENV spring.profiles.active local
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]