#!/bin/bash

echo "=== 图片水印工具演示 ==="
echo

# 检查JAR文件是否存在
if [ ! -f "target/image-watermark-1.0.0.jar" ]; then
    echo "错误: JAR文件不存在，请先运行 mvn package"
    exit 1
fi

echo "1. 显示程序帮助信息:"
echo "java -jar target/image-watermark-1.0.0.jar --help"
echo
java -jar target/image-watermark-1.0.0.jar --help
echo

echo "2. 显示程序版本信息:"
echo "java -jar target/image-watermark-1.0.0.jar --version"
echo
java -jar target/image-watermark-1.0.0.jar --version
echo

echo "=== 使用示例 ==="
echo
echo "基本用法:"
echo "java -jar target/image-watermark-1.0.0.jar /path/to/image.jpg"
echo "java -jar target/image-watermark-1.0.0.jar /path/to/photos/"
echo
echo "自定义选项:"
echo "java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -f 32 -c \"255,0,0\" -p BOTTOM_LEFT"
echo "java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -c \"#FF0000\" -p TOP_RIGHT -v"
echo
echo "=== 注意事项 ==="
echo "- 请将 /path/to/photos/ 替换为实际的图片文件或目录路径"
echo "- 程序会在原目录的父目录下创建名为 '原目录名_watermark' 的新目录"
echo "- 处理后的图片会保存在新目录中，原图片保持不变"
echo "- 支持 JPG、PNG、TIFF、BMP、GIF 格式"
echo
