#!/bin/bash

# 图片水印工具GUI启动脚本

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

# 启动GUI应用程序
echo "正在启动GUI应用程序..."
java --module-path /opt/homebrew/lib/openjfx/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -jar "$JAR_FILE"

echo "GUI应用程序已退出"
