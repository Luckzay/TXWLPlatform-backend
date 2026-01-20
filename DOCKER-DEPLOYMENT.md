# TXWL Platform Docker 部署指南

本文档介绍如何使用 Docker 部署 TXWL Platform 后端应用。

## 1. 快速启动

### 使用 Docker Compose（推荐）
```bash
# 启动完整应用栈（应用 + 数据库）
docker-compose up -d

# 访问应用
# 应用将在 http://localhost:8080 上运行
```

### 单独构建和运行
```bash
# 构建 Docker 镜像
docker build -t txwl-platform-backend .

# 运行应用（需要先启动数据库）
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/txwl_platform \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=admin \
  txwl-platform-backend
```

## 2. 环境变量配置

| 环境变量 | 默认值 | 描述 |
|----------|--------|------|
| SPRING_PROFILES_ACTIVE | docker | Spring Boot 配置文件标识 |
| SPRING_DATASOURCE_URL | jdbc:mysql://localhost:3306/txwl_platform | 数据库连接 URL |
| SPRING_DATASOURCE_USERNAME | root | 数据库用户名 |
| SPRING_DATASOURCE_PASSWORD | admin | 数据库密码 |
| SERVER_PORT | 8080 | 应用运行端口 |
| AI_API_KEY | sk-68ba763917d04a5e96c263b37f2c2258 | AI API 密钥 |
| AI_API_ENDPOINT | https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions | AI API 端点 |
| AI_API_MODEL | qwen-flash | AI 模型名称 |

## 3. 构建 Docker 镜像

### 使用构建脚本
```bash
# 给脚本执行权限
chmod +x build-docker.sh

# 构建本地镜像
./build-docker.sh

# 构建并指定标签
./build-docker.sh --tag v1.0.0

# 构建并推送到私有仓库
./build-docker.sh --registry my-registry.com/team --tag v1.0.0
```

## 4. 部署到阿里云容器镜像服务

```bash
# 设置环境变量
export ACR_REGISTRY="registry.cn-hangzhou.aliyuncs.com"
export ACR_USERNAME="your-username"
export ACR_PASSWORD="your-password"

# 执行部署脚本
chmod +x deploy-acr.sh
./deploy-acr.sh
```

## 5. 生产环境部署

对于生产环境，建议使用以下配置：

```yaml
# docker-compose.prod.yml
version: '3.8'

services:
  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD_FILE: /run/secrets/db_root_password
      MYSQL_DATABASE: txwl_platform
      MYSQL_USER: txwl_user
      MYSQL_PASSWORD_FILE: /run/secrets/db_password
    volumes:
      - mysql_data:/var/lib/mysql
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    restart: unless-stopped
    secrets:
      - db_root_password
      - db_password

  app:
    image: your-registry/txwl-platform-backend:latest
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/txwl_platform
      - SPRING_DATASOURCE_USERNAME=txwl_user
      - SPRING_DATASOURCE_PASSWORD_FILE=/run/secrets/db_password
    ports:
      - "8080:8080"
    depends_on:
      - db
    restart: unless-stopped
    secrets:
      - db_password

volumes:
  mysql_data:

secrets:
  db_root_password:
    file: ./secrets/db_root_password.txt
  db_password:
    file: ./secrets/db_password.txt
```

## 6. 监控和日志

```bash
# 查看容器日志
docker-compose logs -f app

# 查看容器资源使用情况
docker stats txwl_backend

# 进入容器调试
docker exec -it txwl_backend sh
```

## 7. 健康检查

应用提供了健康检查端点：
- `GET /actuator/health` - 基础健康状态
- `GET /actuator/info` - 应用信息

## 8. 故障排除

### 数据库连接问题
- 确保 MySQL 容器已启动
- 检查数据库凭证是否正确
- 确认网络连接正常

### 应用启动问题
- 检查日志输出：`docker-compose logs app`
- 确认环境变量设置正确
- 验证数据库连接参数

### 端口冲突
- 修改 docker-compose.yml 中的端口映射
- 确保目标端口未被占用