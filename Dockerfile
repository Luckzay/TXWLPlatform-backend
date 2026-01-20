# 多阶段构建 - 编译阶段
# 使用阿里云镜像仓库的Maven镜像以提高构建速度
FROM registry.cn-hangzhou.aliyuncs.com/acs/maven:3.8.6-openjdk-17 AS builder

WORKDIR /app

# 首先复制pom.xml以利用Docker缓存层
COPY pom.xml .

# 配置阿里云Maven镜像以加速依赖下载
RUN mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>' > /root/.m2/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0">' >> /root/.m2/settings.xml && \
    echo '  <mirrors>' >> /root/.m2/settings.xml && \
    echo '    <mirror>' >> /root/.m2/settings.xml && \
    echo '      <id>aliyunmaven</id>' >> /root/.m2/settings.xml && \
    echo '      <mirrorOf>*</mirrorOf>' >> /root/.m2/settings.xml && \
    echo '      <name>阿里云公共仓库</name>' >> /root/.m2/settings.xml && \
    echo '      <url>https://maven.aliyun.com/repository/public</url>' >> /root/.m2/settings.xml && \
    echo '    </mirror>' >> /root/.m2/settings.xml && \
    echo '  </mirrors>' >> /root/.m2/settings.xml && \
    echo '</settings>' >> /root/.m2/settings.xml

# 下载依赖
RUN mvn dependency:resolve-plugins -B

# 复制源代码并编译
COPY . .
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# 运行阶段
# 使用阿里云镜像仓库的OpenJDK JRE镜像，基于Alpine Linux以减小体积
FROM registry.cn-hangzhou.aliyuncs.com/acs/openjdk:17-jre-alpine

LABEL maintainer="TXWLPlatform Team" \
      description="Backend service for TXWLPlatform - Optimized for Alibaba Cloud ACR" \
      version="1.0.0"

# 更新包管理器并安装wget用于健康检查
RUN apk add --no-cache wget

# 创建非root用户以提高安全性
RUN addgroup -g 1001 -S txwlplatform && \
    adduser -u 1001 -S txwlplatform -G txwlplatform

WORKDIR /app

# 从构建阶段复制打包好的jar文件
COPY --from=builder /app/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

# 更改文件所有者
RUN chown txwlplatform:txwlplatform app.jar

# 切换到非root用户
USER txwlplatform

EXPOSE 8080

# 环境变量设置 - 可通过阿里云ACR构建参数传入
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -XX:+UseContainerSupport -Xmx512m"

# 健康检查 - 检查应用是否正常运行
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health 2>/dev/null || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]