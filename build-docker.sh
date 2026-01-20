#!/bin/bash

# 构建Docker镜像并推送到阿里云ACR
# 使用前请先登录阿里云ACR: docker login --username=<your-username> registry.cn-hangzhou.aliyuncs.com

set -e  # 遇到错误立即退出

# 镜像版本号，默认为当前时间戳
IMAGE_VERSION=${1:-$(date +%Y%m%d-%H%M%S)}
# 阿里云ACR仓库地址
REGISTRY_URL=${2:-"registry.cn-hangzhou.aliyuncs.com"}
# 命名空间
NAMESPACE=${3:-"<your-namespace>"}
# 项目名称
PROJECT_NAME="txwl-platform"

echo "开始构建镜像..."
echo "镜像版本: $IMAGE_VERSION"
echo "仓库地址: $REGISTRY_URL"
echo "命名空间: $NAMESPACE"
echo "项目名称: $PROJECT_NAME"

# 检查是否已登录到阿里云ACR
if ! docker info | grep -q "$REGISTRY_URL"; then
    echo "警告: 未检测到已登录到 $REGISTRY_URL，请先执行: docker login --username=<your-username> $REGISTRY_URL"
    read -p "是否继续? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# 构建Docker镜像
echo "正在构建Docker镜像..."
docker build -t ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:${IMAGE_VERSION} .

if [ $? -eq 0 ]; then
    echo "Docker镜像构建成功!"
    
    # 标记latest标签
    docker tag ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:${IMAGE_VERSION} ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:latest
    
    echo "正在推送镜像到阿里云ACR..."
    docker push ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:${IMAGE_VERSION}
    docker push ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:latest
    
    if [ $? -eq 0 ]; then
        echo "镜像推送成功!"
        echo "镜像地址: ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:${IMAGE_VERSION}"
        echo "最新镜像: ${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:latest"
        
        echo "\n部署命令参考:"
        echo "kubectl run txwl-platform --image=${REGISTRY_URL}/${NAMESPACE}/${PROJECT_NAME}:${IMAGE_VERSION} --port=8080"
    else
        echo "镜像推送失败，请检查Docker是否已登录阿里云ACR"
        exit 1
    fi
else
    echo "Docker镜像构建失败!"
    exit 1
fi