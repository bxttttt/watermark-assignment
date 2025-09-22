package com.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 测试图片生成器
 * 用于生成各种格式的测试图片
 */
public class TestImageGenerator {
    
    public static void main(String[] args) {
        try {
            createTestImages();
            System.out.println("测试图片生成完成！");
        } catch (Exception e) {
            System.err.println("生成测试图片失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void createTestImages() throws IOException {
        // 创建不同尺寸的测试图片
        createTestImage("test1.jpg", 800, 600, Color.BLUE, "测试图片1");
        createTestImage("test2.png", 1024, 768, Color.GREEN, "测试图片2");
        createTestImage("test3.jpg", 640, 480, Color.RED, "测试图片3");
        createTestImage("test4.png", 1920, 1080, Color.ORANGE, "测试图片4");
        createTestImage("test5.jpg", 400, 300, Color.MAGENTA, "测试图片5");
        
        System.out.println("已生成5张测试图片到 test-images/ 目录");
    }
    
    private static void createTestImage(String fileName, int width, int height, Color bgColor, String text) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // 设置背景色
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
        
        // 添加文本
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        
        int x = (width - textWidth) / 2;
        int y = (height + textHeight) / 2;
        
        g2d.drawString(text, x, y);
        
        // 添加当前日期作为测试
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        fm = g2d.getFontMetrics();
        textWidth = fm.stringWidth(currentDate);
        x = (width - textWidth) / 2;
        y = height - 50;
        g2d.drawString(currentDate, x, y);
        
        g2d.dispose();
        
        // 保存图片
        File outputFile = new File("test-images/" + fileName);
        String format = fileName.toLowerCase().endsWith(".png") ? "PNG" : "JPEG";
        ImageIO.write(image, format, outputFile);
        
        System.out.println("生成图片: " + fileName + " (" + width + "x" + height + ")");
    }
}
