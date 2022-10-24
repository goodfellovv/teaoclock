FROM mongo:6.0.2
FROM openjdk:17-slim
COPY ./target/mongo-teaoclock.jar /mongo-teaoclock.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "mongo-teaoclock.jar"]