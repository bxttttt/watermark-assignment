#!/bin/bash

# 图片水印工具测试套件
# 测试各种功能和参数组合

echo "=========================================="
echo "图片水印工具测试套件"
echo "=========================================="
echo "测试时间: $(date)"
echo

# 设置测试环境
TEST_DIR="test-results"
JAR_FILE="target/image-watermark-1.0.0.jar"
TEST_IMAGES_DIR="test-images"

# 检查JAR文件是否存在
if [ ! -f "$JAR_FILE" ]; then
    echo "错误: JAR文件不存在，请先运行 mvn package"
    exit 1
fi

# 检查测试图片是否存在
if [ ! -d "$TEST_IMAGES_DIR" ] || [ -z "$(ls -A $TEST_IMAGES_DIR)" ]; then
    echo "错误: 测试图片目录不存在或为空"
    exit 1
fi

# 创建测试结果目录
mkdir -p "$TEST_DIR"

# 测试计数器
test_count=0
success_count=0
error_count=0

# 测试函数
run_test() {
    local test_name="$1"
    local command="$2"
    local expected_result="$3"
    
    test_count=$((test_count + 1))
    echo "测试 $test_count: $test_name"
    echo "命令: $command"
    
    # 执行测试
    if eval "$command" > "$TEST_DIR/test_${test_count}.log" 2>&1; then
        echo "✓ 测试通过"
        success_count=$((success_count + 1))
    else
        echo "✗ 测试失败"
        error_count=$((error_count + 1))
    fi
    echo
}

echo "开始执行测试用例..."
echo

# 测试1: 基本功能测试 - 默认参数
run_test "基本功能测试" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR" \
    "应该成功处理所有测试图片"

# 测试2: 单文件处理
run_test "单文件处理" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR/test1.jpg" \
    "应该成功处理单个文件"

# 测试3: 自定义字体大小
run_test "自定义字体大小" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -f 32" \
    "应该使用32号字体"

# 测试4: 自定义颜色 - RGB格式
run_test "自定义颜色(RGB)" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -c '255,0,0'" \
    "应该使用红色水印"

# 测试5: 自定义颜色 - 十六进制格式
run_test "自定义颜色(HEX)" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -c '#00FF00'" \
    "应该使用绿色水印"

# 测试6: 不同位置测试
run_test "水印位置测试" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -p TOP_LEFT" \
    "应该在左上角显示水印"

# 测试7: 详细输出模式
run_test "详细输出模式" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -v" \
    "应该显示详细处理信息"

# 测试8: 自定义文件后缀
run_test "自定义文件后缀" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -s '_test'" \
    "应该使用自定义后缀"

# 测试9: 组合参数测试
run_test "组合参数测试" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -f 28 -c '0,0,255' -p BOTTOM_RIGHT -s '_blue'" \
    "应该使用蓝色28号字体在右下角显示"

# 测试10: 帮助信息
run_test "帮助信息" \
    "java -jar $JAR_FILE --help" \
    "应该显示帮助信息"

# 测试11: 版本信息
run_test "版本信息" \
    "java -jar $JAR_FILE --version" \
    "应该显示版本信息"

# 测试12: 错误处理 - 无效路径
run_test "错误处理(无效路径)" \
    "java -jar $JAR_FILE /nonexistent/path" \
    "应该正确处理错误"

# 测试13: 错误处理 - 无效参数
run_test "错误处理(无效参数)" \
    "java -jar $JAR_FILE $TEST_IMAGES_DIR -f -10" \
    "应该正确处理无效参数"

echo "=========================================="
echo "测试结果汇总"
echo "=========================================="
echo "总测试数: $test_count"
echo "成功: $success_count"
echo "失败: $error_count"
echo "成功率: $(( success_count * 100 / test_count ))%"
echo

# 生成详细测试报告
cat > "$TEST_DIR/test-report.md" << EOF
# 图片水印工具测试报告

## 测试概述
- 测试时间: $(date)
- 总测试数: $test_count
- 成功: $success_count
- 失败: $error_count
- 成功率: $(( success_count * 100 / test_count ))%

## 测试环境
- Java版本: $(java -version 2>&1 | head -n 1)
- 操作系统: $(uname -s) $(uname -m)
- JAR文件: $JAR_FILE

## 测试用例详情
EOF

# 添加每个测试的详细结果
for i in $(seq 1 $test_count); do
    if [ -f "$TEST_DIR/test_${i}.log" ]; then
        echo "### 测试 $i" >> "$TEST_DIR/test-report.md"
        echo "\`\`\`" >> "$TEST_DIR/test-report.md"
        cat "$TEST_DIR/test_${i}.log" >> "$TEST_DIR/test-report.md"
        echo "\`\`\`" >> "$TEST_DIR/test-report.md"
        echo >> "$TEST_DIR/test-report.md"
    fi
done

echo "详细测试报告已生成: $TEST_DIR/test-report.md"

# 检查输出目录
echo
echo "检查生成的输出目录:"
find . -name "*_watermark" -type d 2>/dev/null | head -5

echo
echo "检查生成的图片文件:"
find . -name "*_watermarked*" -type f 2>/dev/null | head -10

echo
echo "测试完成！"
