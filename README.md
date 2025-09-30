# 图片水印工具

一个基于Java的图片水印工具，提供命令行和图形界面两种使用方式。

## 功能特性

### 命令行版本
- 自动读取图片EXIF信息中的拍摄时间
- 支持多种图片格式（JPG、PNG、TIFF、BMP、GIF）
- 可自定义水印字体大小、颜色和位置
- 支持单文件或批量处理整个目录
- 自动创建带水印的图片副本

### GUI版本
- 🖼️ 直观的图形用户界面
- 📁 支持拖拽导入图片和文件夹
- 🎨 实时预览水印效果
- 📝 文本水印：自定义字体、颜色、透明度、阴影、描边
- 🖼️ 图片水印：支持PNG透明水印，可调整缩放和透明度
- ⚙️ 输出设置：JPEG/PNG格式选择，质量调节，图片缩放
- 💾 模板管理：保存和加载水印配置模板
- 📊 批量处理：选择多个图片进行批量导出

## 系统要求

- Java 11 或更高版本
- Maven 3.6+（用于编译）
- 至少 2GB 可用内存

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/bxttttt/watermark-assignment.git
cd watermark-assignment
```

### 2. 编译项目

```bash
mvn clean compile package
```

### 3. 运行应用程序

#### 🖥️ GUI版本（推荐）

```bash
# 方法1：使用Maven运行
mvn javafx:run

# 方法2：使用启动脚本（macOS/Linux）
chmod +x start-gui.sh
./start-gui.sh

# 方法3：直接运行JAR文件
java -jar target/image-watermark-1.0.0.jar
```

#### 💻 命令行版本

```bash
java -jar target/image-watermark-1.0.0.jar <图片路径或目录> [选项]
```

## GUI应用程序使用指南

### 启动GUI应用程序

1. **使用Maven运行**（推荐）：
   ```bash
   mvn javafx:run
   ```

2. **使用启动脚本**：
   ```bash
   chmod +x start-gui.sh
   ./start-gui.sh
   ```

3. **直接运行JAR**：
   ```bash
   java -jar target/image-watermark-1.0.0.jar
   ```

### GUI界面说明

#### 左侧面板 - 文件管理
- **添加文件**：选择单个或多个图片文件
- **添加文件夹**：批量导入文件夹中的所有图片
- **拖拽支持**：直接将图片文件拖拽到界面中
- **全选/清空**：批量选择或清空图片列表
- **图片列表**：显示已导入的图片，支持缩略图预览

#### 中心面板 - 预览
- **实时预览**：显示当前选中图片的水印效果
- **预览标签**：显示当前处理的图片名称
- **点击切换**：点击左侧图片列表切换预览

#### 右侧面板 - 水印设置

##### 📝 文本水印设置
- **文本内容**：输入自定义水印文本
- **字体设置**：选择字体类型、大小、粗体、斜体
- **颜色设置**：使用颜色选择器自定义水印颜色
- **透明度**：0-100%透明度调节
- **特效**：阴影和描边效果

##### 🖼️ 图片水印设置
- **选择图片**：选择PNG或其他格式的水印图片
- **缩放比例**：0-300%缩放调节
- **透明度**：0-100%透明度调节

##### ⚙️ 输出设置
- **输出格式**：选择JPEG或PNG格式
- **JPEG质量**：0-100%质量调节（仅JPEG格式）
- **保持宽高比**：保持原图比例
- **缩放比例**：0.1-5.0倍缩放

##### 💾 模板管理
- **保存模板**：保存当前水印配置为模板
- **加载模板**：快速加载已保存的模板
- **删除模板**：删除不需要的模板

#### 底部操作区域
- **刷新预览**：手动刷新预览效果
- **导出图片**：批量导出选中的图片
- **进度条**：显示导出进度
- **状态标签**：显示当前操作状态

### 使用步骤

1. **导入图片**：
   - 点击"添加文件"选择图片
   - 或点击"添加文件夹"批量导入
   - 或直接拖拽图片到界面

2. **设置水印**：
   - 选择"文本水印"或"图片水印"标签
   - 配置水印的各种参数
   - 实时查看预览效果

3. **选择图片**：
   - 在左侧列表中点击选择要处理的图片
   - 使用"全选"功能选择所有图片

4. **导出图片**：
   - 点击"导出图片"按钮
   - 选择输出文件夹
   - 等待处理完成

## 命令行版本使用说明

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

### 输入格式
- JPEG (.jpg, .jpeg)
- PNG (.png)
- TIFF (.tiff, .tif)
- BMP (.bmp)
- GIF (.gif)

### 输出格式
- JPEG (.jpg)
- PNG (.png)

## 输出说明

### 命令行版本
- 程序会在原目录的父目录下创建一个名为 `原目录名_watermark` 的新目录
- 处理后的图片会保存在这个新目录中
- 如果处理单个文件，会在该文件的同级目录创建 `原目录名_watermark` 目录
- 输出文件名格式：`原文件名_watermarked.扩展名`

### GUI版本
- 用户可以自由选择输出文件夹
- 支持自定义文件名前缀和后缀
- 保持原文件名或添加自定义后缀

## EXIF日期读取

程序会按以下优先级读取拍摄日期：

1. EXIF原始拍摄时间 (DateTimeOriginal)
2. EXIF数字化时间 (DateTimeDigitized)
3. 文件修改时间（作为备选）

如果无法读取EXIF信息，程序会使用文件修改时间作为水印内容。

## 故障排除

### GUI应用程序无法启动

如果GUI应用程序无法启动，请尝试以下解决方案：

1. **检查Java版本**：
   ```bash
   java -version
   ```
   确保使用Java 11或更高版本。

2. **使用Maven运行**：
   ```bash
   mvn javafx:run
   ```

3. **检查JavaFX依赖**：
   如果仍然无法启动，可能需要安装JavaFX运行时。

4. **使用命令行版本**：
   如果GUI版本无法使用，可以使用命令行版本：
   ```bash
   java -jar target/image-watermark-1.0.0.jar --help
   ```

### 常见问题

**Q: 为什么GUI应用程序启动失败？**
A: 请确保使用Java 11+版本，并尝试使用 `mvn javafx:run` 命令启动。

**Q: 支持哪些图片格式？**
A: 支持JPEG、PNG、TIFF、BMP、GIF格式的输入，输出支持JPEG和PNG。

**Q: 如何批量处理图片？**
A: GUI版本支持拖拽文件夹或使用"添加文件夹"功能，命令行版本可以直接指定目录路径。

**Q: 水印位置可以自定义吗？**
A: GUI版本支持拖拽调整位置，命令行版本提供9个预设位置。

## 项目结构

```
watermark-assignment/
├── src/main/java/com/watermark/
│   ├── ExifReader.java              # EXIF读取器
│   ├── WatermarkDrawer.java         # 水印绘制器
│   ├── ImageProcessor.java          # 图片处理器
│   ├── WatermarkOptions.java        # 命令行选项
│   ├── ImageWatermarkApp.java       # 命令行主程序
│   └── gui/                         # GUI应用程序
│       ├── WatermarkApplication.java # GUI主程序
│       ├── controller/              # 控制器
│       ├── model/                   # 数据模型
│       ├── service/                 # 服务层
│       └── resources/               # 资源文件
├── src/test/java/                   # 测试代码
├── pom.xml                          # Maven配置
├── README.md                        # 项目说明
├── start-gui.sh                     # GUI启动脚本
└── demo.sh                          # 演示脚本
```

## 版本历史

- **v1.0** - 初始命令行版本
- **v2.0** - 添加GUI版本
- **v2.1** - 修复GUI启动问题，完善功能

## 许可证

本项目采用MIT许可证。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 联系方式

如有问题或建议，请通过GitHub Issues联系我们。