package com.watermark.gui.service;

import com.watermark.gui.model.ImageFile;
import com.watermark.gui.model.WatermarkSettings;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 图片处理服务类
 */
public class ImageProcessingService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * 创建预览图片
     */
    public Image createPreviewImage(Image originalImage, WatermarkSettings settings) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(originalImage, null);
        if (bufferedImage == null) {
            return null;
        }
        
        BufferedImage watermarkedImage = addWatermark(bufferedImage, settings);
        return SwingFXUtils.toFXImage(watermarkedImage, null);
    }
    
    /**
     * 处理图片并保存
     */
    public void processImage(ImageFile imageFile, WatermarkSettings settings, File outputDir) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile.getFile());
        if (originalImage == null) {
            throw new IOException("无法读取图片: " + imageFile.getName());
        }
        
        BufferedImage watermarkedImage = addWatermark(originalImage, settings);
        
        // 生成输出文件名
        String outputFileName = generateOutputFileName(imageFile, settings);
        File outputFile = new File(outputDir, outputFileName);
        
        // 保存图片
        String format = settings.getOutputFormat();
        ImageIO.write(watermarkedImage, format, outputFile);
    }
    
    /**
     * 添加水印到图片
     */
    private BufferedImage addWatermark(BufferedImage originalImage, WatermarkSettings settings) {
        // 创建新图片
        BufferedImage watermarkedImage = new BufferedImage(
            originalImage.getWidth(),
            originalImage.getHeight(),
            originalImage.getType()
        );
        
        Graphics2D g2d = watermarkedImage.createGraphics();
        
        // 设置渲染质量
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // 绘制原始图片
        g2d.drawImage(originalImage, 0, 0, null);
        
        // 添加水印
        if (settings.getType() == WatermarkSettings.WatermarkType.TEXT) {
            addTextWatermark(g2d, originalImage, settings);
        } else if (settings.getType() == WatermarkSettings.WatermarkType.IMAGE) {
            addImageWatermark(g2d, originalImage, settings);
        }
        
        g2d.dispose();
        return watermarkedImage;
    }
    
    /**
     * 添加文本水印
     */
    private void addTextWatermark(Graphics2D g2d, BufferedImage image, WatermarkSettings settings) {
        String text = settings.getText();
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        
        // 设置字体
        int fontStyle = Font.PLAIN;
        if (settings.isBold() && settings.isItalic()) {
            fontStyle = Font.BOLD | Font.ITALIC;
        } else if (settings.isBold()) {
            fontStyle = Font.BOLD;
        } else if (settings.isItalic()) {
            fontStyle = Font.ITALIC;
        }
        
        Font font = new Font(settings.getFontFamily(), fontStyle, settings.getFontSize());
        g2d.setFont(font);
        
        // 获取文本尺寸
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        
        // 计算位置
        int x = (int) (settings.getX() * image.getWidth());
        int y = (int) (settings.getY() * image.getHeight());
        
        // 应用旋转
        if (settings.getRotation() != 0) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(settings.getRotation()), x, y);
            g2d.setTransform(transform);
        }
        
        // 设置透明度
        Color textColor = settings.getFxColor();
        java.awt.Color awtColor = new java.awt.Color(
            (float) textColor.getRed(),
            (float) textColor.getGreen(),
            (float) textColor.getBlue(),
            (float) settings.getOpacity()
        );
        
        // 绘制阴影
        if (settings.isShadow()) {
            g2d.setColor(new java.awt.Color(0, 0, 0, (int) (128 * settings.getOpacity())));
            g2d.drawString(text, x + 1, y + 1);
        }
        
        // 绘制描边
        if (settings.isStroke()) {
            Color strokeColor = settings.getFxStrokeColor();
            java.awt.Color awtStrokeColor = new java.awt.Color(
                (float) strokeColor.getRed(),
                (float) strokeColor.getGreen(),
                (float) strokeColor.getBlue(),
                (float) settings.getOpacity()
            );
            g2d.setColor(awtStrokeColor);
            g2d.drawString(text, x - 1, y - 1);
            g2d.drawString(text, x + 1, y - 1);
            g2d.drawString(text, x - 1, y + 1);
            g2d.drawString(text, x + 1, y + 1);
        }
        
        // 绘制主文本
        g2d.setColor(awtColor);
        g2d.drawString(text, x, y);
        
        // 重置变换
        g2d.setTransform(new AffineTransform());
    }
    
    /**
     * 添加图片水印
     */
    private void addImageWatermark(Graphics2D g2d, BufferedImage image, WatermarkSettings settings) {
        String imagePath = settings.getImagePath();
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return;
        }
        
        try {
            BufferedImage watermarkImage = ImageIO.read(new File(imagePath));
            if (watermarkImage == null) {
                return;
            }
            
            // 计算缩放后的尺寸
            int scaledWidth = (int) (watermarkImage.getWidth() * settings.getImageScale());
            int scaledHeight = (int) (watermarkImage.getHeight() * settings.getImageScale());
            
            // 计算位置
            int x = (int) (settings.getX() * image.getWidth() - scaledWidth / 2);
            int y = (int) (settings.getY() * image.getHeight() - scaledHeight / 2);
            
            // 设置透明度
            g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 
                (float) (settings.getImageOpacity() * settings.getOpacity())
            ));
            
            // 绘制水印图片
            g2d.drawImage(watermarkImage, x, y, scaledWidth, scaledHeight, null);
            
        } catch (IOException e) {
            System.err.println("加载水印图片失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成输出文件名
     */
    private String generateOutputFileName(ImageFile imageFile, WatermarkSettings settings) {
        String baseName = imageFile.getFileNameWithoutExtension();
        String extension = settings.getOutputFormat().toLowerCase();
        
        // 添加后缀
        String suffix = "_watermarked";
        if (settings.getOutputFormat().equals("JPEG")) {
            extension = "jpg";
        }
        
        return baseName + suffix + "." + extension;
    }
    
    /**
     * 从EXIF获取拍摄日期
     */
    public String getPhotoDate(File imageFile) {
        try {
            // 这里可以集成之前的EXIF读取功能
            // 暂时返回当前日期
            return LocalDate.now().format(DATE_FORMATTER);
        } catch (Exception e) {
            return LocalDate.now().format(DATE_FORMATTER);
        }
    }
}
