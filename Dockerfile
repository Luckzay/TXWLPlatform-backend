# 多阶段构建 - 编译阶段
FROM registry.cn-hangzhou.aliyuncs.com/aliyunmaven/maven:3.8.6-amazoncorretto-17 AS builder

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 运行阶段
FROM registry.cn-hangzhou.aliyuncs.com/acs/amazoncorretto:17

WORKDIR /app

COPY --from=builder /workspace/target/TXWLPlatform-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]