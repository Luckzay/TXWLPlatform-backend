# 多阶段构建 - 编译阶段
FROM maven:3.8.6-openjdk-17 AS builder

WORKDIR /workspace

# 复制项目文件
COPY pom.xml .
COPY src ./src

# 下载依赖并编译构建
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:17-jre-slim

WORKDIR /app

# 从构建阶段复制jar文件
COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]