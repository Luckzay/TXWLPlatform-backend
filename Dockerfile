# 多阶段构建 - 构建阶段
FROM registry.cn-hangzhou.aliyuncs.com/acs/openjdk:17-jdk-slim AS builder

# 设置工作目录
WORKDIR /app

# 复制pom.xml并下载依赖（利用Docker缓存层）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并构建
COPY src ./src
RUN mvn clean package -DskipTests

# 运行阶段
FROM registry.cn-hangzhou.aliyuncs.com/acs/openjdk:17-jre-slim

# 安装必要的工具并创建非root用户
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/* \
    && groupadd -r spring && useradd -r -g spring spring

# 设置工作目录
WORKDIR /app

# 从构建阶段复制jar文件
COPY --from=builder /app/target/TXWLPlatform-backend-*.jar app.jar

# 更改文件所有者
RUN chown -R spring:spring /app
USER spring:spring

# 暴露端口
EXPOSE 8080

# JVM参数优化，适用于容器环境
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -Xms256m -Xmx512m"

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]