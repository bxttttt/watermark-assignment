#!/bin/bash

# 测试报告生成函数
generate_test_report() {
    REPORT_FILE="test-results/test-report.md"
    
    echo "生成测试报告: $REPORT_FILE"
    
    cat > "$REPORT_FILE" << EOF
# Java图片水印工具测试报告

## 测试概述

- **测试时间**: $(date)
- **测试环境**: $(uname -s) $(uname -r)
- **Java版本**: $(java -version 2>&1 | head -1)
- **测试图片数量**: $(ls -1 test-data/images 2>/dev/null | wc -l)

## 测试用例

### 测试用例1: 基本功能测试
- **命令**: \`java -jar target/image-watermark-1.0.0.jar test-data/images\`
- **参数**: 默认参数（字体大小24，白色，右下角位置）
- **结果**: 
EOF

    if [ -d "test-data/images_watermark" ]; then
        echo "- ✅ 成功创建输出目录: test-data/images_watermark" >> "$REPORT_FILE"
        echo "- 📁 输出文件数量: $(ls -1 test-data/images_watermark 2>/dev/null | wc -l)" >> "$REPORT_FILE"
    else
        echo "- ❌ 未创建输出目录" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

### 测试用例2: 自定义字体大小和颜色
- **命令**: \`java -jar target/image-watermark-1.0.0.jar test-data/images -f 36 -c "255,0,0" -v\`
- **参数**: 字体大小36，红色，详细输出
- **结果**: 
EOF

    if [ -d "test-data/images_watermark" ]; then
        echo "- ✅ 成功处理图片" >> "$REPORT_FILE"
        echo "- 📁 输出文件数量: $(ls -1 test-data/images_watermark 2>/dev/null | wc -l)" >> "$REPORT_FILE"
    else
        echo "- ❌ 处理失败" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

### 测试用例3: 不同位置测试
- **命令**: \`java -jar target/image-watermark-1.0.0.jar test-data/images -p TOP_LEFT -s "_top_left"\`
- **参数**: 左上角位置，自定义后缀
- **结果**: 
EOF

    top_left_files=$(ls -1 test-data/images_watermark/*_top_left.* 2>/dev/null | wc -l)
    if [ $top_left_files -gt 0 ]; then
        echo "- ✅ 成功创建左上角位置水印文件" >> "$REPORT_FILE"
        echo "- 📁 左上角位置文件数量: $top_left_files" >> "$REPORT_FILE"
    else
        echo "- ❌ 位置测试失败" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

### 测试用例4: 十六进制颜色和居中位置
- **命令**: \`java -jar target/image-watermark-1.0.0.jar test-data/images -c "#00FF00" -p CENTER -s "_center"\`
- **参数**: 绿色（十六进制），居中位置
- **结果**: 
EOF

    center_files=$(ls -1 test-data/images_watermark/*_center.* 2>/dev/null | wc -l)
    if [ $center_files -gt 0 ]; then
        echo "- ✅ 成功创建居中位置水印文件" >> "$REPORT_FILE"
        echo "- 📁 居中位置文件数量: $center_files" >> "$REPORT_FILE"
    else
        echo "- ❌ 居中测试失败" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

### 测试用例5: 单个文件处理
- **命令**: \`java -jar target/image-watermark-1.0.0.jar test-data/images/test1.jpg -f 48 -c "0,0,255" -p BOTTOM_RIGHT\`
- **参数**: 字体大小48，蓝色，右下角位置
- **结果**: 
EOF

    PARENT_DIR="test-data"
    if [ -d "$PARENT_DIR/images_watermark" ]; then
        echo "- ✅ 成功创建单文件处理输出目录: $PARENT_DIR/images_watermark" >> "$REPORT_FILE"
        echo "- 📁 输出文件数量: $(ls -1 $PARENT_DIR/images_watermark 2>/dev/null | wc -l)" >> "$REPORT_FILE"
    else
        echo "- ❌ 单文件处理失败" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

## 测试日志

EOF

    # 添加测试日志内容
    for log_file in test-results/*.log; do
        if [ -f "$log_file" ]; then
            echo "### $(basename "$log_file" .log)" >> "$REPORT_FILE"
            echo "\`\`\`" >> "$REPORT_FILE"
            cat "$log_file" >> "$REPORT_FILE"
            echo "\`\`\`" >> "$REPORT_FILE"
            echo >> "$REPORT_FILE"
        fi
    done
    
    cat >> "$REPORT_FILE" << EOF

## 输出文件统计

EOF

    # 统计输出文件
    echo "| 测试类型 | 输出目录 | 文件数量 |" >> "$REPORT_FILE"
    echo "|---------|---------|---------|" >> "$REPORT_FILE"
    
    if [ -d "test-data/images_watermark" ]; then
        total_count=$(ls -1 test-data/images_watermark 2>/dev/null | wc -l)
        echo "| 所有测试 | test-data/images_watermark | $total_count |" >> "$REPORT_FILE"
        
        basic_count=$(ls -1 test-data/images_watermark/*_watermarked.* 2>/dev/null | wc -l)
        echo "| 基本功能 | *_watermarked.* | $basic_count |" >> "$REPORT_FILE"
        
        top_left_count=$(ls -1 test-data/images_watermark/*_top_left.* 2>/dev/null | wc -l)
        echo "| 位置测试 | *_top_left.* | $top_left_count |" >> "$REPORT_FILE"
        
        center_count=$(ls -1 test-data/images_watermark/*_center.* 2>/dev/null | wc -l)
        echo "| 居中测试 | *_center.* | $center_count |" >> "$REPORT_FILE"
    fi
    
    cat >> "$REPORT_FILE" << EOF

## 测试总结

EOF

    # 计算成功和失败的测试用例
    total_tests=5
    success_count=0
    
    # 测试1: 基本功能
    if [ -d "test-data/images_watermark" ] && [ $(ls -1 test-data/images_watermark/*_watermarked.* 2>/dev/null | wc -l) -gt 0 ]; then
        success_count=$((success_count + 1))
    fi
    
    # 测试2: 自定义参数
    if [ -d "test-data/images_watermark" ] && [ $(ls -1 test-data/images_watermark/*_watermarked.* 2>/dev/null | wc -l) -gt 0 ]; then
        success_count=$((success_count + 1))
    fi
    
    # 测试3: 位置测试
    if [ $(ls -1 test-data/images_watermark/*_top_left.* 2>/dev/null | wc -l) -gt 0 ]; then
        success_count=$((success_count + 1))
    fi
    
    # 测试4: 居中测试
    if [ $(ls -1 test-data/images_watermark/*_center.* 2>/dev/null | wc -l) -gt 0 ]; then
        success_count=$((success_count + 1))
    fi
    
    # 测试5: 单文件处理
    if [ -d "test-data/images/images_watermark" ]; then
        success_count=$((success_count + 1))
    fi
    
    failed_count=$((total_tests - success_count))
    
    echo "- **总测试用例**: $total_tests" >> "$REPORT_FILE"
    echo "- **成功**: $success_count" >> "$REPORT_FILE"
    echo "- **失败**: $failed_count" >> "$REPORT_FILE"
    echo "- **成功率**: $(( success_count * 100 / total_tests ))%" >> "$REPORT_FILE"
    
    if [ $success_count -eq $total_tests ]; then
        echo "- **测试状态**: ✅ 全部通过" >> "$REPORT_FILE"
    else
        echo "- **测试状态**: ⚠️ 部分失败" >> "$REPORT_FILE"
    fi
    
    echo
    echo "测试报告已生成: $REPORT_FILE"
}
