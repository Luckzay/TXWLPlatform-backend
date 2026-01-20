# 多阶段构建 - 编译阶段
FROM registry.cn-hangzhou.aliyuncs.com/acs/maven:3.8.6-jdk-17 AS builder

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

# 设置Maven阿里云镜像（加速依赖下载）
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

RUN mvn clean package -DskipTests

# 运行阶段
FROM registry.cn-hangzhou.aliyuncs.com/acs/openjdk:17-jre

WORKDIR /app

COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]