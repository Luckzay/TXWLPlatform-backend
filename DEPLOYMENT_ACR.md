# 阿里云ACR部署指南

本文档介绍如何将TXWLPlatform后端应用构建为Docker镜像并部署到阿里云容器镜像服务(ACR)。

## 环境准备

1. 注册阿里云账号并开通容器镜像服务(ACR)
2. 创建个人版或企业版实例
3. 安装Docker
4. 准备阿里云访问凭证

## 项目文件说明

本项目包含以下用于部署的文件：

- [Dockerfile](./Dockerfile) - 多阶段Docker构建配置，使用阿里云镜像仓库优化国内访问速度
- [.dockerignore](./.dockerignore) - Docker构建忽略文件配置
- [docker-compose.yml](./docker-compose.yml) - 本地测试环境配置
- [build-docker.sh](./build-docker.sh) - 自动化构建和推送脚本
- [k8s-deployment.yaml](./k8s-deployment.yaml) - Kubernetes部署配置
- [.github/workflows/deploy-acr.yml](./.github/workflows/deploy-acr.yml) - GitHub Actions CI/CD配置
- [DEPLOYMENT_ACR.md](./DEPLOYMENT_ACR.md) - 本部署指南

## 部署步骤

### 1. 登录阿里云ACR

```bash
# 登录阿里云ACR（替换为你的实际仓库域名）
docker login --username=<your-username> registry.cn-hangzhou.aliyuncs.com
```

### 2. 构建并推送镜像

```bash
# 方法一：使用构建脚本
chmod +x build-docker.sh
./build-docker.sh <版本号> <仓库域名> <命名空间>

# 示例：
./build-docker.sh v1.0.0 registry.cn-hangzhou.aliyuncs.com/mycompany

# 方法二：手动构建
docker build -t registry.cn-hangzhou.aliyuncs.com/<命名空间>/txwl-platform:<版本号> .
docker push registry.cn-hangzhou.aliyuncs.com/<命名空间>/txwl-platform:<版本号>
```

### 3. CI/CD自动化部署

本项目提供了GitHub Actions配置，支持自动构建和部署：

1. 在GitHub仓库的Settings -> Secrets and variables -> Actions中添加以下密钥：
   - ACR_USERNAME: 阿里云ACR用户名
   - ACR_PASSWORD: 阿里云ACR密码
   - ACR_NAMESPACE: 阿里云ACR命名空间

2. 当代码推送到main分支时，会自动触发构建流程

### 4. 在阿里云容器服务中部署

#### 通过容器服务控制台部署：

1. 登录阿里云容器服务管理控制台
2. 选择目标集群
3. 点击"部署" -> "创建部署"
4. 配置以下参数（或使用[k8s-deployment.yaml](./k8s-deployment.yaml)）：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: txwl-platform-deployment
spec:
  replicas: 2  # 建议至少2个副本以提高可用性
  selector:
    matchLabels:
      app: txwl-platform
  template:
    metadata:
      labels:
        app: txwl-platform
    spec:
      containers:
      - name: txwl-platform
        image: registry.cn-hangzhou.aliyuncs.com/<命名空间>/txwl-platform:<版本号>
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: url
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: password
        - name: AI_API_KEY
          valueFrom:
            secretKeyRef:
              name: ai-secret
              key: api-key
        - name: AI_API_ENDPOINT
          value: "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions"
        - name: AI_API_MODEL
          value: "qwen-flash"
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
---
apiVersion: v1
kind: Service
metadata:
  name: txwl-platform-service
spec:
  selector:
    app: txwl-platform
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer
```

### 5. 创建Kubernetes Secrets

在部署应用之前，需要创建包含敏感信息的Secrets：

```bash
# 创建数据库Secret
kubectl create secret generic db-secret \
  --from-literal=url="jdbc:mysql://<数据库连接地址>:3306/txwl_platform" \
  --from-literal=username="<数据库用户名>" \
  --from-literal=password="<数据库密码>"

# 创建AI API Secret
kubectl create secret generic ai-secret \
  --from-literal=api-key="<AI_API_KEY>"
```

### 6. 本地测试（可选）

如果您想在本地测试Docker镜像：

```bash
# 构建镜像
docker build -t txwl-platform:test .

# 运行容器（需要先启动MySQL服务）
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://<host>:3306/txwl_platform \
  -e SPRING_DATASOURCE_USERNAME=<username> \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  txwl-platform:test
```

或者使用docker-compose进行完整测试：

```bash
docker-compose up -d
```

## 配置说明

### 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| SPRING_PROFILES_ACTIVE | Spring配置文件环境 | prod |
| SPRING_DATASOURCE_URL | 数据库连接URL | jdbc:mysql://localhost:3306/txwl_platform |
| SPRING_DATASOURCE_USERNAME | 数据库用户名 | root |
| SPRING_DATASOURCE_PASSWORD | 数据库密码 | admin |
| AI_API_KEY | AI API密钥 | sk-68ba763917d04a5e96c263b37f2c2258 |
| AI_API_ENDPOINT | AI API端点 | https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions |
| AI_API_MODEL | AI模型 | qwen-flash |

### 端口

- 应用端口: 8080

## Docker镜像优化特性

1. **多阶段构建**: 构建和运行分离，减小最终镜像大小
2. **非root用户**: 使用非root用户运行应用，提高安全性
3. **JVM优化**: 针对容器环境的JVM参数优化
4. **内存限制**: 合理的内存初始值和最大值设置
5. **健康检查**: 内置健康检查端点，便于Kubernetes管理

## 注意事项

1. 生产环境中请确保使用安全的数据库凭据
2. AI_API_KEY等敏感信息建议使用Kubernetes Secrets或阿里云加密服务存储
3. 请根据实际业务负载调整资源限制
4. 建议开启阿里云容器服务的自动部署功能，与Git仓库集成实现CI/CD
5. 监控应用日志和性能指标，及时调整资源配置
6. 使用HTTPS协议保护数据传输安全

## 故障排除

### 镜像构建失败
- 检查Dockerfile语法
- 确认阿里云镜像仓库地址是否可访问
- 检查网络连接是否正常

### 应用启动失败
- 检查数据库连接配置
- 查看应用日志输出
- 确认环境变量设置正确
- 验证端口是否被正确暴露

### 部署失败
- 检查Kubernetes RBAC权限
- 验证Secrets是否正确创建
- 确认镜像拉取策略设置正确

### 性能问题
- 调整JVM内存参数
- 优化数据库连接池配置
- 检查网络延迟和带宽

## 安全建议

1. 使用最小权限原则配置Kubernetes RBAC
2. 定期更新基础镜像以修复安全漏洞
3. 对敏感信息使用加密存储
4. 配置网络策略限制不必要的访问
5. 开启审计日志以监控系统活动