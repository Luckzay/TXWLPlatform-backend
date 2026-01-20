# 单阶段构建 - 使用同一个基础镜像
FROM openjdk:17.0.2-jdk-slim

WORKDIR /workspace

# 安装Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 复制项目文件
COPY pom.xml .
COPY src ./src

# 编译并运行
RUN mvn clean package -DskipTests

WORKDIR /app

RUN cp /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]