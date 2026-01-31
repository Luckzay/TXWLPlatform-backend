# 多阶段构建 - 第一阶段：构建应用
FROM eclipse-temurin:17-jdk-alpine AS builder

# 设置工作目录
WORKDIR /app

# 复制 Maven 包装器和 pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 下载依赖（利用Docker缓存层）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用包
RUN mvn clean package -DskipTests

# 第二阶段：运行应用
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="TXWL Platform Team"
LABEL description="TXWL Platform Backend Application"

# 创建非root用户（增强安全性）
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 修改文件权限
RUN chown -R spring:spring /app

# 切换到非root用户
USER spring

# 暴露端口（与Spring Boot配置一致）
EXPOSE 8080

# 设置JVM参数（优化内存使用）
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError"

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
