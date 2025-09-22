# 图片水印工具测试报告

## 测试概述
- 测试时间: 2025年 9月22日 星期一 11时19分18秒 CST
- 总测试数: 13
- 成功: 11
- 失败: 2
- 成功率: 84%

## 测试环境
- Java版本: openjdk version "21.0.7" 2025-04-15 LTS
- 操作系统: Darwin arm64
- JAR文件: target/image-watermark-1.0.0.jar

## 测试用例详情
### 测试 1
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 关闭
===================

创建输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:07.372 java[47886:5392900] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:07.372 java[47886:5392900] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 2
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images/test1.jpg
字体大小: 24
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 关闭
===================

创建输出目录: /Users/bxt/Desktop/_watermark/test-images/test-images_watermark
开始处理 1 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images/test-images_watermark
2025-09-22 11:19:08.337 java[47893:5393092] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:08.337 java[47893:5393092] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 1/1 个文件
```

### 测试 3
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 32
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:09.461 java[47905:5393195] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:09.461 java[47905:5393195] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 4
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: 255,0,0 (java.awt.Color[r=255,g=0,b=0])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:10.673 java[47917:5393310] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:10.673 java[47917:5393310] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 5
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: #00FF00 (java.awt.Color[r=0,g=255,b=0])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:11.890 java[47921:5393378] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:11.890 java[47921:5393378] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 6
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 左上角
文件后缀: _watermarked
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:13.119 java[47932:5393475] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:13.119 java[47932:5393475] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 7
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 右下角
文件后缀: _watermarked
详细模式: 开启
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
读取到拍摄日期: 2025-09-22 (test5.jpg)
2025-09-22 11:19:14.393 java[47944:5393558] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:14.393 java[47944:5393558] +[IMKInputSession subclass]: chose IMKInputSession_Modern
✓ 已处理: test5.jpg
读取到拍摄日期: 2025-09-22 (test4.png)
✓ 已处理: test4.png
读取到拍摄日期: 2025-09-22 (test1.jpg)
✓ 已处理: test1.jpg
读取到拍摄日期: 2025-09-22 (test3.jpg)
✓ 已处理: test3.jpg
读取到拍摄日期: 2025-09-22 (test2.png)
✓ 已处理: test2.png
处理完成! 成功处理 5/5 个文件
```

### 测试 8
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 24
水印颜色: 255,255,255 (java.awt.Color[r=255,g=255,b=255])
水印位置: 右下角
文件后缀: _test
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:15.603 java[47949:5393636] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:15.603 java[47949:5393636] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 9
```
=== 图片水印工具 ===
输入路径: /Users/bxt/Desktop/_watermark/test-images
字体大小: 28
水印颜色: 0,0,255 (java.awt.Color[r=0,g=0,b=255])
水印位置: 右下角
文件后缀: _blue
详细模式: 关闭
===================

使用现有输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
开始处理 5 个图片文件...
输出目录: /Users/bxt/Desktop/_watermark/test-images_watermark
2025-09-22 11:19:16.809 java[47954:5393691] +[IMKClient subclass]: chose IMKClient_Modern
2025-09-22 11:19:16.809 java[47954:5393691] +[IMKInputSession subclass]: chose IMKInputSession_Modern
处理完成! 成功处理 5/5 个文件
```

### 测试 10
```
Usage: image-watermark [-hvV] [-c=<colorString>] [-f=<fontSize>]
                       [-p=<positionString>] [-s=<fileSuffix>] <inputPath>
为图片添加基于EXIF拍摄时间的水印工具
      <inputPath>   图片文件或目录路径
  -c, --color=<colorString>
                    水印颜色，格式：red,green,blue 或 #RRGGBB (默认:
                      255,255,255)
  -f, --font-size=<fontSize>
                    字体大小 (默认: 24)
  -h, --help        Show this help message and exit.
  -p, --position=<positionString>
                    水印位置: TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
                      CENTER, TOP_CENTER, BOTTOM_CENTER, LEFT_CENTER,
                      RIGHT_CENTER (默认: BOTTOM_RIGHT)
  -s, --suffix=<fileSuffix>
                    输出文件名后缀 (默认: _watermarked)
  -v, --verbose     显示详细输出
  -V, --version     Print version information and exit.
```

### 测试 11
```
1.0.0
```

### 测试 12
```
错误: 输入路径不存在: /nonexistent/path
```

### 测试 13
```
错误: 字体大小必须大于0
```

