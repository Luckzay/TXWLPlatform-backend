# 多阶段构建 - 编译阶段
FROM maven:3.8.6-jdk-17 AS builder

WORKDIR /workspace

# 复制项目文件
COPY pom.xml .
COPY src ./src

# 设置Maven镜像源加速（国内）
RUN echo '<?xml version="1.0" encoding="UTF-8"?>' > /usr/share/maven/conf/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' >> /usr/share/maven/conf/settings.xml && \
    echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /usr/share/maven/conf/settings.xml && \
    echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /usr/share/maven/conf/settings.xml && \
    echo '  <mirrors>' >> /usr/share/maven/conf/settings.xml && \
    echo '    <mirror>' >> /usr/share/maven/conf/settings.xml && \
    echo '      <id>alimaven</id>' >> /usr/share/maven/conf/settings.xml && \
    echo '      <name>aliyun maven</name>' >> /usr/share/maven/conf/settings.xml && \
    echo '      <url>https://maven.aliyun.com/repository/public</url>' >> /usr/share/maven/conf/settings.xml && \
    echo '      <mirrorOf>central</mirrorOf>' >> /usr/share/maven/conf/settings.xml && \
    echo '    </mirror>' >> /usr/share/maven/conf/settings.xml && \
    echo '  </mirrors>' >> /usr/share/maven/conf/settings.xml && \
    echo '</settings>' >> /usr/share/maven/conf/settings.xml

# 下载依赖并编译构建
RUN mvn clean package -DskipTests

# 运行阶段 - 使用官方镜像
FROM openjdk:17-jdk-slim

WORKDIR /app

# 从构建阶段复制jar文件
COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]