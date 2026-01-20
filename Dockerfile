# 多阶段构建 - 编译阶段
FROM maven:3.8.6-openjdk-17 AS builder

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 运行阶段 - 使用Debian为基础的OpenJDK
FROM openjdk:17-jdk-buster

WORKDIR /app

COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]