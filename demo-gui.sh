#!/bin/bash

# 图片水印工具演示脚本

echo "=========================================="
echo "图片水印工具演示"
echo "=========================================="
echo

# 检查JAR文件是否存在
JAR_FILE="target/image-watermark-1.0.0.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "错误: JAR文件不存在，请先运行 mvn package"
    exit 1
fi

echo "由于GUI版本需要JavaFX运行时环境，我们使用命令行版本来演示功能："
echo

# 创建演示图片目录
DEMO_DIR="demo-images"
if [ ! -d "$DEMO_DIR" ]; then
    mkdir -p "$DEMO_DIR"
    echo "创建演示目录: $DEMO_DIR"
fi

# 生成演示图片
echo "1. 生成演示图片..."
java -cp target/classes:target/test-classes com.watermark.TestImageGenerator 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ 演示图片生成成功"
else
    echo "✗ 演示图片生成失败"
fi

echo

# 演示基本功能
echo "2. 演示基本功能 - 默认参数"
echo "命令: java -jar $JAR_FILE test-images"
echo "---"
java -jar "$JAR_FILE" test-images 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ 基本功能演示成功"
    echo "✓ 输出目录: test-images_watermark"
else
    echo "✗ 基本功能演示失败"
fi

echo

# 演示自定义参数
echo "3. 演示自定义参数"
echo "命令: java -jar $JAR_FILE test-images -f 32 -c \"255,0,0\" -p BOTTOM_LEFT -v"
echo "---"
java -jar "$JAR_FILE" test-images -f 32 -c "255,0,0" -p BOTTOM_LEFT -v 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ 自定义参数演示成功"
else
    echo "✗ 自定义参数演示失败"
fi

echo

# 演示帮助信息
echo "4. 演示帮助信息"
echo "命令: java -jar $JAR_FILE --help"
echo "---"
java -jar "$JAR_FILE" --help | head -20
echo "..."

echo

# 检查输出结果
echo "5. 检查输出结果"
if [ -d "test-images_watermark" ]; then
    echo "✓ 输出目录存在"
    echo "✓ 生成的文件数量: $(ls -1 test-images_watermark | wc -l)"
    echo "✓ 生成的文件:"
    ls -la test-images_watermark/ | head -5
else
    echo "✗ 输出目录不存在"
fi

echo

echo "=========================================="
echo "演示完成！"
echo "=========================================="
echo
echo "GUI版本启动说明："
echo "由于系统缺少JavaFX运行时，GUI版本无法直接启动。"
echo "要使用GUI版本，请："
echo "1. 安装JavaFX运行时环境"
echo "2. 或者使用支持JavaFX的IDE（如IntelliJ IDEA）"
echo "3. 或者使用在线JavaFX运行环境"
echo
echo "命令行版本功能完整，可以正常使用所有功能。"
echo "详细使用方法请参考 README.md 文件。"
