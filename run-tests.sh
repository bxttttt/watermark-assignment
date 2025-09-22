#!/bin/bash

echo "=== Java图片水印工具测试脚本 ==="
echo "测试时间: $(date)"
echo

# 设置测试目录
TEST_DIR="test-data/images"
RESULTS_DIR="test-results"
JAR_FILE="target/image-watermark-1.0.0.jar"

# 创建结果目录
mkdir -p "$RESULTS_DIR"

# 检查JAR文件是否存在
if [ ! -f "$JAR_FILE" ]; then
    echo "错误: JAR文件不存在，请先运行 mvn package"
    exit 1
fi

# 检查测试图片是否存在
if [ ! -d "$TEST_DIR" ] || [ -z "$(ls -A $TEST_DIR)" ]; then
    echo "错误: 测试图片不存在，请先运行测试图片生成器"
    exit 1
fi

echo "1. 测试基本信息"
echo "测试目录: $TEST_DIR"
echo "结果目录: $RESULTS_DIR"
echo "JAR文件: $JAR_FILE"
echo "测试图片数量: $(ls -1 $TEST_DIR | wc -l)"
echo

# 清理之前的结果
rm -rf test-data/images_watermark

echo "2. 测试用例1: 基本功能测试（默认参数）"
echo "命令: java -jar $JAR_FILE $TEST_DIR"
echo "---"
java -jar "$JAR_FILE" "$TEST_DIR" > "$RESULTS_DIR/test1-basic.log" 2>&1
echo "测试1完成，日志保存到: $RESULTS_DIR/test1-basic.log"
echo

echo "3. 测试用例2: 自定义字体大小和颜色"
echo "命令: java -jar $JAR_FILE $TEST_DIR -f 36 -c \"255,0,0\" -v"
echo "---"
java -jar "$JAR_FILE" "$TEST_DIR" -f 36 -c "255,0,0" -v > "$RESULTS_DIR/test2-custom.log" 2>&1
echo "测试2完成，日志保存到: $RESULTS_DIR/test2-custom.log"
echo

echo "4. 测试用例3: 不同位置测试"
echo "命令: java -jar $JAR_FILE $TEST_DIR -p TOP_LEFT -s \"_top_left\""
echo "---"
java -jar "$JAR_FILE" "$TEST_DIR" -p TOP_LEFT -s "_top_left" > "$RESULTS_DIR/test3-position.log" 2>&1
echo "测试3完成，日志保存到: $RESULTS_DIR/test3-position.log"
echo

echo "5. 测试用例4: 十六进制颜色和居中位置"
echo "命令: java -jar $JAR_FILE $TEST_DIR -c \"#00FF00\" -p CENTER -s \"_center\""
echo "---"
java -jar "$JAR_FILE" "$TEST_DIR" -c "#00FF00" -p CENTER -s "_center" > "$RESULTS_DIR/test4-hex-color.log" 2>&1
echo "测试4完成，日志保存到: $RESULTS_DIR/test4-hex-color.log"
echo

echo "6. 测试用例5: 单个文件处理"
FIRST_IMAGE=$(ls -1 $TEST_DIR | head -1)
echo "命令: java -jar $JAR_FILE $TEST_DIR/$FIRST_IMAGE -f 48 -c \"0,0,255\" -p BOTTOM_RIGHT"
echo "---"
java -jar "$JAR_FILE" "$TEST_DIR/$FIRST_IMAGE" -f 48 -c "0,0,255" -p BOTTOM_RIGHT > "$RESULTS_DIR/test5-single.log" 2>&1
echo "测试5完成，日志保存到: $RESULTS_DIR/test5-single.log"
echo

echo "7. 收集测试结果"
echo "---"

# 检查输出目录
echo "检查输出目录:"
if [ -d "test-data/images_watermark" ]; then
    echo "✓ 批量处理输出目录已创建: test-data/images_watermark"
    echo "  输出文件数量: $(ls -1 test-data/images_watermark | wc -l)"
    ls -la test-data/images_watermark/
else
    echo "✗ 批量处理输出目录未创建"
fi

if [ -d "test-data/images_top_left" ]; then
    echo "✓ 位置测试输出目录已创建: test-data/images_top_left"
    echo "  输出文件数量: $(ls -1 test-data/images_top_left | wc -l)"
fi

if [ -d "test-data/images_center" ]; then
    echo "✓ 居中测试输出目录已创建: test-data/images_center"
    echo "  输出文件数量: $(ls -1 test-data/images_center | wc -l)"
fi

# 检查单个文件处理结果
PARENT_DIR=$(dirname "$TEST_DIR")
if [ -d "$PARENT_DIR/images_watermark" ]; then
    echo "✓ 单文件处理输出目录已创建: $PARENT_DIR/images_watermark"
    echo "  输出文件数量: $(ls -1 $PARENT_DIR/images_watermark | wc -l)"
fi

echo
echo "8. 生成测试报告"
source generate_test_report.sh
generate_test_report

echo
echo "=== 测试完成 ==="
echo "所有测试日志保存在: $RESULTS_DIR/"
echo "测试报告: $RESULTS_DIR/test-report.md"
