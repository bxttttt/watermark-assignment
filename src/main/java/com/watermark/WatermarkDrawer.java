package com.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 水印绘制器
 * 负责在图片上绘制文本水印
 */
public class WatermarkDrawer {
    
    /**
     * 在图片上绘制水印并保存
     * 
     * @param sourceImage 源图片文件
     * @param outputImage 输出图片文件
     * @param watermarkText 水印文本
     * @param fontSize 字体大小
     * @param color 颜色
     * @param position 位置
     * @throws IOException 如果读取或写入图片失败
     */
    public static void drawWatermark(File sourceImage, File outputImage, String watermarkText, 
                                   int fontSize, Color color, WatermarkPosition position) throws IOException {
        
        // 读取源图片
        BufferedImage originalImage = ImageIO.read(sourceImage);
        if (originalImage == null) {
            throw new IOException("无法读取图片: " + sourceImage.getName());
        }
        
        // 创建新图片，保持原始格式
        BufferedImage watermarkedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                originalImage.getType()
        );
        
        // 复制原始图片
        Graphics2D g2d = watermarkedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        
        // 设置字体和颜色
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.setColor(color);
        
        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 计算水印位置
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(watermarkText);
        int textHeight = fontMetrics.getHeight();
        
        Point watermarkPos = calculateWatermarkPosition(
                originalImage.getWidth(), originalImage.getHeight(),
                textWidth, textHeight, position
        );
        
        // 添加阴影效果以提高可读性
        g2d.setColor(new Color(0, 0, 0, 128)); // 半透明黑色阴影
        g2d.drawString(watermarkText, watermarkPos.x + 1, watermarkPos.y + 1);
        
        // 绘制主文本
        g2d.setColor(color);
        g2d.drawString(watermarkText, watermarkPos.x, watermarkPos.y);
        
        g2d.dispose();
        
        // 保存图片
        String formatName = getImageFormat(sourceImage);
        ImageIO.write(watermarkedImage, formatName, outputImage);
    }
    
    /**
     * 计算水印位置
     * 
     * @param imageWidth 图片宽度
     * @param imageHeight 图片高度
     * @param textWidth 文本宽度
     * @param textHeight 文本高度
     * @param position 位置枚举
     * @return 水印位置坐标
     */
    private static Point calculateWatermarkPosition(int imageWidth, int imageHeight, 
                                                  int textWidth, int textHeight, 
                                                  WatermarkPosition position) {
        
        int margin = 20; // 边距
        
        switch (position) {
            case TOP_LEFT:
                return new Point(margin, textHeight + margin);
                
            case TOP_RIGHT:
                return new Point(imageWidth - textWidth - margin, textHeight + margin);
                
            case BOTTOM_LEFT:
                return new Point(margin, imageHeight - margin);
                
            case BOTTOM_RIGHT:
                return new Point(imageWidth - textWidth - margin, imageHeight - margin);
                
            case CENTER:
                return new Point(
                    (imageWidth - textWidth) / 2,
                    (imageHeight + textHeight) / 2
                );
                
            case TOP_CENTER:
                return new Point(
                    (imageWidth - textWidth) / 2,
                    textHeight + margin
                );
                
            case BOTTOM_CENTER:
                return new Point(
                    (imageWidth - textWidth) / 2,
                    imageHeight - margin
                );
                
            case LEFT_CENTER:
                return new Point(
                    margin,
                    (imageHeight + textHeight) / 2
                );
                
            case RIGHT_CENTER:
                return new Point(
                    imageWidth - textWidth - margin,
                    (imageHeight + textHeight) / 2
                );
                
            default:
                return new Point(margin, textHeight + margin);
        }
    }
    
    /**
     * 获取图片格式
     * 
     * @param imageFile 图片文件
     * @return 图片格式名称
     */
    private static String getImageFormat(File imageFile) {
        String fileName = imageFile.getName().toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "JPEG";
        } else if (fileName.endsWith(".png")) {
            return "PNG";
        } else if (fileName.endsWith(".tiff") || fileName.endsWith(".tif")) {
            return "TIFF";
        } else if (fileName.endsWith(".bmp")) {
            return "BMP";
        } else if (fileName.endsWith(".gif")) {
            return "GIF";
        } else {
            return "JPEG"; // 默认格式
        }
    }
    
    /**
     * 解析颜色字符串
     * 
     * @param colorString 颜色字符串，格式：red,green,blue 或 #RRGGBB
     * @return Color对象
     */
    public static Color parseColor(String colorString) {
        if (colorString == null || colorString.trim().isEmpty()) {
            return Color.WHITE; // 默认白色
        }
        
        colorString = colorString.trim();
        
        // 处理十六进制颜色
        if (colorString.startsWith("#")) {
            try {
                return Color.decode(colorString);
            } catch (NumberFormatException e) {
                System.err.println("无效的十六进制颜色格式: " + colorString);
                return Color.WHITE;
            }
        }
        
        // 处理RGB格式 (red,green,blue)
        String[] parts = colorString.split(",");
        if (parts.length == 3) {
            try {
                int red = Integer.parseInt(parts[0].trim());
                int green = Integer.parseInt(parts[1].trim());
                int blue = Integer.parseInt(parts[2].trim());
                
                // 确保RGB值在有效范围内
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));
                
                return new Color(red, green, blue);
            } catch (NumberFormatException e) {
                System.err.println("无效的RGB颜色格式: " + colorString);
                return Color.WHITE;
            }
        }
        
        System.err.println("不支持的颜色格式: " + colorString + "，使用默认白色");
        return Color.WHITE;
    }
}
