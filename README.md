# 图片水印工具

一个基于Java的命令行工具，用于为图片添加基于EXIF拍摄时间的文本水印。

## 功能特性

- 自动读取图片EXIF信息中的拍摄时间
- 支持多种图片格式（JPG、PNG、TIFF、BMP、GIF）
- 可自定义水印字体大小、颜色和位置
- 支持单文件或批量处理整个目录
- 自动创建带水印的图片副本

## 编译和运行

### 编译项目

```bash
mvn clean compile package
```

### 运行程序

```bash
java -jar target/image-watermark-1.0.0.jar <图片路径或目录> [选项]
```

## 使用示例

### 基本用法

```bash
# 处理单个图片文件
java -jar target/image-watermark-1.0.0.jar /path/to/image.jpg

# 处理整个目录
java -jar target/image-watermark-1.0.0.jar /path/to/photos/
```

### 自定义选项

```bash
# 设置字体大小为32，红色水印，位置在左下角
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -f 32 -c "255,0,0" -p BOTTOM_LEFT

# 使用十六进制颜色，位置在右上角
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -c "#FF0000" -p TOP_RIGHT

# 启用详细输出模式
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -v
```

## 命令行选项

| 选项 | 长选项 | 描述 | 默认值 |
|------|--------|------|--------|
| `-f` | `--font-size` | 字体大小 | 24 |
| `-c` | `--color` | 水印颜色（RGB格式：red,green,blue 或十六进制：#RRGGBB） | 255,255,255 |
| `-p` | `--position` | 水印位置 | BOTTOM_RIGHT |
| `-s` | `--suffix` | 输出文件名后缀 | _watermarked |
| `-v` | `--verbose` | 显示详细输出 | false |
| `-h` | `--help` | 显示帮助信息 | - |
| `--version` | 显示版本信息 | - |

## 支持的水印位置

- `TOP_LEFT` - 左上角
- `TOP_RIGHT` - 右上角
- `BOTTOM_LEFT` - 左下角
- `BOTTOM_RIGHT` - 右下角
- `CENTER` - 居中
- `TOP_CENTER` - 顶部居中
- `BOTTOM_CENTER` - 底部居中
- `LEFT_CENTER` - 左侧居中
- `RIGHT_CENTER` - 右侧居中

## 支持的图片格式

- JPEG (.jpg, .jpeg)
- PNG (.png)
- TIFF (.tiff, .tif)
- BMP (.bmp)
- GIF (.gif)

## 输出说明

- 程序会在原目录的父目录下创建一个名为 `原目录名_watermark` 的新目录
- 处理后的图片会保存在这个新目录中
- 如果处理单个文件，会在该文件的同级目录创建 `原目录名_watermark` 目录
- 输出文件名格式：`原文件名_watermarked.扩展名`

## EXIF日期读取

程序会按以下优先级读取拍摄日期：

1. EXIF原始拍摄时间 (DateTimeOriginal)
2. EXIF数字化时间 (DateTimeDigitized)
3. 文件修改时间（作为备选）

如果无法读取EXIF信息，程序会使用文件修改时间作为水印内容。

## 注意事项

- 程序会保持原图片不变，只创建带水印的副本
- 字体大小建议在12-200之间
- 颜色值范围：RGB各分量0-255，或有效的十六进制颜色代码
- 水印文本为拍摄日期，格式：YYYY-MM-DD
