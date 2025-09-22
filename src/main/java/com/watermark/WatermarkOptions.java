package com.watermark;

import picocli.CommandLine;

import java.awt.Color;
import java.io.File;

/**
 * 命令行参数配置类
 */
@CommandLine.Command(
    name = "image-watermark",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    description = "为图片添加基于EXIF拍摄时间的水印工具"
)
public class WatermarkOptions {
    
    @CommandLine.Parameters(
        index = "0",
        description = "图片文件或目录路径"
    )
    private File inputPath;
    
    @CommandLine.Option(
        names = {"-f", "--font-size"},
        description = "字体大小 (默认: 24)",
        defaultValue = "24"
    )
    private int fontSize;
    
    @CommandLine.Option(
        names = {"-c", "--color"},
        description = "水印颜色，格式：red,green,blue 或 #RRGGBB (默认: 255,255,255)",
        defaultValue = "255,255,255"
    )
    private String colorString;
    
    @CommandLine.Option(
        names = {"-p", "--position"},
        description = "水印位置: TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, " +
                     "TOP_CENTER, BOTTOM_CENTER, LEFT_CENTER, RIGHT_CENTER (默认: BOTTOM_RIGHT)",
        defaultValue = "BOTTOM_RIGHT"
    )
    private String positionString;
    
    @CommandLine.Option(
        names = {"-s", "--suffix"},
        description = "输出文件名后缀 (默认: _watermarked)",
        defaultValue = "_watermarked"
    )
    private String fileSuffix;
    
    @CommandLine.Option(
        names = {"-v", "--verbose"},
        description = "显示详细输出"
    )
    private boolean verbose;
    
    // Getters
    public File getInputPath() {
        return inputPath;
    }
    
    public int getFontSize() {
        return fontSize;
    }
    
    public String getColorString() {
        return colorString;
    }
    
    public String getPositionString() {
        return positionString;
    }
    
    public String getFileSuffix() {
        return fileSuffix;
    }
    
    public boolean isVerbose() {
        return verbose;
    }
    
    /**
     * 获取解析后的颜色对象
     */
    public Color getColor() {
        return WatermarkDrawer.parseColor(colorString);
    }
    
    /**
     * 获取解析后的位置枚举
     */
    public WatermarkPosition getPosition() {
        try {
            return WatermarkPosition.valueOf(positionString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("无效的位置参数: " + positionString + "，使用默认位置: BOTTOM_RIGHT");
            return WatermarkPosition.BOTTOM_RIGHT;
        }
    }
    
    /**
     * 验证输入参数
     */
    public boolean validate() {
        if (inputPath == null || !inputPath.exists()) {
            System.err.println("错误: 输入路径不存在: " + inputPath);
            return false;
        }
        
        if (fontSize <= 0) {
            System.err.println("错误: 字体大小必须大于0");
            return false;
        }
        
        if (fontSize > 200) {
            System.err.println("警告: 字体大小过大 (" + fontSize + ")，建议不超过200");
        }
        
        return true;
    }
}
