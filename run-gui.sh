#!/bin/bash

# 图片水印工具GUI启动脚本

echo "启动图片水印工具GUI..."

# 检查Java版本
if ! java -version 2>&1 | grep -q "version"; then
    echo "错误: 未找到Java运行环境，请先安装Java 11或更高版本"
    exit 1
fi

# 设置Java模块路径（如果需要）
export JAVA_OPTS="--module-path /usr/lib/jvm/java-11-openjfx/lib --add-modules javafx.controls,javafx.fxml,javafx.swing"

# 运行GUI应用程序
echo "正在启动GUI应用程序..."
mvn javafx:run

echo "GUI应用程序已退出"
