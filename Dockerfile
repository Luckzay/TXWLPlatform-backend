# 多阶段构建 - 第一阶段：构建应用
FROM eclipse-temurin:17-jdk-alpine AS builder

# 设置工作目录
WORKDIR /app

# 复制 Maven 文件
COPY pom.xml .
COPY src ./src

# 构建应用
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# 第二阶段：运行应用
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="TXWL Platform Team"
LABEL description="TXWL Platform Backend Application"

# 创建非root用户
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring
USER 1001:1001

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]