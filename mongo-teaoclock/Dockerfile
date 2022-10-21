FROM openjdk:17-slim
COPY ./target/mongo-teaoclock.jar /mongo-teaoclock.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "mongo-teaoclock.jar"]