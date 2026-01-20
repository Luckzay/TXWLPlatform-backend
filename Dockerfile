# 多阶段构建 - 编译阶段
FROM eclipse-temurin:17-jdk-slim AS builder

WORKDIR /workspace

# 复制项目文件
COPY pom.xml .
COPY src ./src

# 下载依赖并编译构建
RUN ./mvnw clean package -DskipTests

# 如果在构建时已经准备好jar文件，则直接使用此版本
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制预构建的jar文件
COPY target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]