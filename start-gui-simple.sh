#!/bin/bash

# 图片水印工具GUI启动脚本（简化版）

echo "启动图片水印工具GUI..."

# 检查Java版本
if ! java -version 2>&1 | grep -q "version"; then
    echo "错误: 未找到Java运行环境，请先安装Java 11或更高版本"
    exit 1
fi

# 检查JAR文件是否存在
JAR_FILE="target/image-watermark-1.0.0.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "错误: JAR文件不存在，请先运行 mvn package"
    exit 1
fi

echo "Java版本信息:"
java -version

echo "正在启动GUI应用程序..."
echo "注意: 如果出现JavaFX相关错误，请确保Java版本支持JavaFX或安装JavaFX运行时"

# 尝试不同的启动方式
echo "尝试方式1: 直接运行JAR文件"
java -jar "$JAR_FILE" 2>&1 | head -20

echo ""
echo "如果上述方式失败，请尝试以下命令手动启动:"
echo "java --add-modules javafx.controls,javafx.fxml,javafx.swing -jar $JAR_FILE"
echo ""
echo "或者安装JavaFX运行时后重新运行"
