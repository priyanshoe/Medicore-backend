FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

CMD ["sh", "-c", "java -jar target/*.jar"]
