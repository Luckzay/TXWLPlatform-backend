# 多阶段构建 - 编译阶段
FROM maven:3.8.6-openjdk-17-slim AS builder

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 运行阶段 - 使用带具体版本号的镜像
FROM openjdk:17.0.2-slim

WORKDIR /app

COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]