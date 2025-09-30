# 图片水印工具启动指南

## 🚨 重要说明

由于您的系统缺少JavaFX运行时环境，GUI版本无法直接启动。这是一个常见的问题，以下是解决方案：

## 🔧 解决方案

### 方案1: 安装JavaFX运行时（推荐）

#### macOS用户
```bash
# 使用Homebrew安装JavaFX
brew install openjfx

# 或者下载JavaFX SDK
# 访问 https://openjfx.io/ 下载适合您系统的版本
```

#### Windows用户
1. 访问 https://openjfx.io/
2. 下载适合您系统的JavaFX SDK
3. 解压到合适的目录（如 `C:\Program Files\Java\javafx-21`）

#### Linux用户
```bash
# Ubuntu/Debian
sudo apt-get install openjfx

# 或者下载JavaFX SDK
# 访问 https://openjfx.io/ 下载适合您系统的版本
```

### 方案2: 使用命令行版本（立即可用）

命令行版本功能完整，无需JavaFX运行时：

```bash
# 基本使用
java -jar target/image-watermark-1.0.0.jar /path/to/images

# 自定义参数
java -jar target/image-watermark-1.0.0.jar /path/to/images -f 32 -c "255,0,0" -p BOTTOM_LEFT

# 查看帮助
java -jar target/image-watermark-1.0.0.jar --help
```

### 方案3: 使用IDE运行

如果您使用IntelliJ IDEA或Eclipse等IDE：

1. 导入项目
2. 配置JavaFX模块路径
3. 直接运行 `WatermarkApplication.java`

## 📋 功能对比

| 功能 | 命令行版本 | GUI版本 |
|------|------------|---------|
| 文件导入 | ✅ 支持 | ✅ 支持（拖拽） |
| 批量处理 | ✅ 支持 | ✅ 支持 |
| 水印设置 | ✅ 支持 | ✅ 支持（可视化） |
| 实时预览 | ❌ 不支持 | ✅ 支持 |
| 模板管理 | ❌ 不支持 | ✅ 支持 |
| 用户界面 | 命令行 | 图形界面 |

## 🎯 推荐使用方式

### 对于普通用户
- **命令行版本**：功能完整，易于使用
- 支持所有核心功能
- 无需额外安装

### 对于高级用户
- **GUI版本**：更直观的界面
- 实时预览功能
- 模板管理系统
- 需要安装JavaFX运行时

## 📚 使用示例

### 命令行版本示例

```bash
# 1. 基本使用 - 为所有图片添加默认水印
java -jar target/image-watermark-1.0.0.jar /path/to/photos/

# 2. 自定义字体大小和颜色
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -f 36 -c "255,0,0"

# 3. 自定义位置
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -p TOP_LEFT

# 4. 详细输出模式
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -v

# 5. 自定义文件后缀
java -jar target/image-watermark-1.0.0.jar /path/to/photos/ -s "_watermarked"
```

### GUI版本启动（安装JavaFX后）

```bash
# macOS/Linux
./start-gui.sh

# Windows
start-gui.bat

# 或直接运行
java --add-modules javafx.controls,javafx.fxml,javafx.swing -jar target/image-watermark-1.0.0.jar
```

## 🔍 故障排除

### 常见问题

1. **"缺少 JavaFX 运行时组件"**
   - 解决方案：安装JavaFX运行时或使用命令行版本

2. **"ImageProcessor is not a valid type"**
   - 解决方案：确保使用正确的JAR文件

3. **"无法读取图片"**
   - 解决方案：检查图片文件格式和权限

4. **"导出失败"**
   - 解决方案：检查输出目录权限和磁盘空间

### 获取帮助

```bash
# 查看详细帮助
java -jar target/image-watermark-1.0.0.jar --help

# 查看版本信息
java -jar target/image-watermark-1.0.0.jar --version
```

## 📞 技术支持

如果遇到问题，请：

1. 查看 `README.md` 文件
2. 检查 `TEST_RESULTS.md` 测试报告
3. 提交GitHub Issue
4. 联系项目维护者

---

**总结**: 虽然GUI版本需要JavaFX运行时，但命令行版本功能完整，可以满足所有基本需求。建议先使用命令行版本，如需图形界面，再安装JavaFX运行时。
