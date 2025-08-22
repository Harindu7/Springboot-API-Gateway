FROM eclipse-temurin:21-jre
ADD target/api_gateway.jar api_gateway.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "api_gateway.jar"]