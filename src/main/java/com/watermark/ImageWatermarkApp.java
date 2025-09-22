package com.watermark;

import picocli.CommandLine;

/**
 * 图片水印工具主程序
 * 
 * 使用方法:
 * java -jar image-watermark.jar <图片路径或目录> [选项]
 * 
 * 示例:
 * java -jar image-watermark.jar /path/to/images
 * java -jar image-watermark.jar /path/to/image.jpg -f 32 -c "255,0,0" -p BOTTOM_LEFT
 * java -jar image-watermark.jar /path/to/images -v
 */
public class ImageWatermarkApp implements Runnable {
    
    @CommandLine.Mixin
    private WatermarkOptions options;
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ImageWatermarkApp()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public void run() {
        try {
            // 验证参数
            if (!options.validate()) {
                System.exit(1);
            }
            
            // 显示配置信息
            displayConfiguration();
            
            // 处理图片
            ImageProcessor processor = new ImageProcessor(options);
            processor.processImages();
            
        } catch (Exception e) {
            System.err.println("程序执行出错: " + e.getMessage());
            if (options.isVerbose()) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }
    
    /**
     * 显示配置信息
     */
    private void displayConfiguration() {
        System.out.println("=== 图片水印工具 ===");
        System.out.println("输入路径: " + options.getInputPath().getAbsolutePath());
        System.out.println("字体大小: " + options.getFontSize());
        System.out.println("水印颜色: " + options.getColorString() + " (" + options.getColor() + ")");
        System.out.println("水印位置: " + options.getPosition().getDescription());
        System.out.println("文件后缀: " + options.getFileSuffix());
        System.out.println("详细模式: " + (options.isVerbose() ? "开启" : "关闭"));
        System.out.println("===================");
        System.out.println();
    }
}
