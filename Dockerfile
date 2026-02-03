# 多阶段构建：第一阶段 - 构建阶段
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# 设置构建环境
LABEL maintainer="TXWL Platform Team"
LABEL description="TXWL Platform Backend Application Builder"

# 设置工作目录
WORKDIR /build

# 复制Maven配置文件（利用缓存）
COPY pom.xml .

# 下载依赖（这一步会被缓存，除非pom.xml改变）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests -B

# 第二阶段 - 运行阶段
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="TXWL Platform Team"
LABEL description="TXWL Platform Backend Application"
LABEL org.opencontainers.image.source="https://github.com/luckzay/TXWLPlatform-backend"
LABEL org.opencontainers.image.description="Backend service for TXWL Platform"
LABEL org.opencontainers.image.licenses="MIT"

# 安装必要的工具
RUN apk add --no-cache \
    curl \
    bash \
    tzdata && \
    # 清理包管理器缓存
    rm -rf /var/cache/apk/*

# 设置时区为上海
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 创建非root用户（增强安全性）
RUN addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

# 设置工作目录
WORKDIR /app

# 从构建阶段复制JAR文件
COPY --from=builder /build/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

# 创建日志目录并设置权限
RUN mkdir -p /app/logs && \
    chown -R appuser:appuser /app

# 切换到非root用户
USER appuser

# 暴露端口（与Spring Boot配置一致）
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# 设置JVM参数（优化内存使用和性能）
ENV JAVA_OPTS="-Xms512m -Xmx1024m \
              -XX:+UseG1GC \
              -XX:MaxGCPauseMillis=200 \
              -XX:+HeapDumpOnOutOfMemoryError \
              -XX:HeapDumpPath=/app/logs/heapdump.hprof \
              -Djava.security.egd=file:/dev/./urandom \
              -Dfile.encoding=UTF-8 \
              -Duser.timezone=Asia/Shanghai"

# 设置应用特定环境变量
ENV SPRING_PROFILES_ACTIVE=docker
ENV LOGGING_FILE_NAME=/app/logs/application.log
ENV LOGGING_LEVEL_ROOT=INFO

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]