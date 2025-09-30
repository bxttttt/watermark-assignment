@echo off
echo 启动图片水印工具GUI...

REM 检查Java版本
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: 未找到Java运行环境，请先安装Java 11或更高版本
    pause
    exit /b 1
)

REM 检查JAR文件是否存在
set JAR_FILE=target\image-watermark-1.0.0.jar
if not exist "%JAR_FILE%" (
    echo 错误: JAR文件不存在，请先运行 mvn package
    pause
    exit /b 1
)

REM 启动GUI应用程序
echo 正在启动GUI应用程序...
java --module-path "C:\Program Files\Java\javafx-21\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing -jar "%JAR_FILE%"

echo GUI应用程序已退出
pause
