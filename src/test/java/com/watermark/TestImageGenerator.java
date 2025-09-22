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
 * 用于创建测试用的图片文件
 */
public class TestImageGenerator {
    
    public static void main(String[] args) {
        try {
            createTestImages();
            System.out.println("测试图片创建完成！");
        } catch (IOException e) {
            System.err.println("创建测试图片失败: " + e.getMessage());
        }
    }
    
    public static void createTestImages() throws IOException {
        // 创建测试目录
        File testDir = new File("test-data/images");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
        
        // 创建不同尺寸的测试图片
        createTestImage(new File(testDir, "test1.jpg"), 800, 600, "测试图片1", Color.BLUE);
        createTestImage(new File(testDir, "test2.png"), 1024, 768, "测试图片2", Color.RED);
        createTestImage(new File(testDir, "test3.jpg"), 640, 480, "测试图片3", Color.GREEN);
        createTestImage(new File(testDir, "test4.png"), 1200, 900, "测试图片4", Color.ORANGE);
        
        System.out.println("已创建4个测试图片文件");
    }
    
    private static void createTestImage(File outputFile, int width, int height, String text, Color bgColor) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // 设置背景色
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
        
        // 添加一些图形元素
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        
        // 居中绘制文本
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);
        
        // 添加日期信息
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        fm = g2d.getFontMetrics();
        x = (width - fm.stringWidth(date)) / 2;
        y += 60;
        g2d.drawString(date, x, y);
        
        g2d.dispose();
        
        // 保存图片
        String format = outputFile.getName().toLowerCase().endsWith(".png") ? "PNG" : "JPEG";
        ImageIO.write(image, format, outputFile);
        
        System.out.println("创建测试图片: " + outputFile.getName() + " (" + width + "x" + height + ")");
    }
}
