package com.watermark.gui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;

/**
 * 水印设置数据模型
 */
public class WatermarkSettings {
    
    // 水印类型
    public enum WatermarkType {
        TEXT, IMAGE
    }
    
    // 水印类型
    private WatermarkType type = WatermarkType.TEXT;
    
    // 文本水印设置
    private String text = "水印";
    private String fontFamily = "Arial";
    private int fontSize = 24;
    private boolean bold = false;
    private boolean italic = false;
    private String color = "#FFFFFF";
    private double opacity = 1.0;
    private boolean shadow = true;
    private boolean stroke = false;
    private String strokeColor = "#000000";
    
    // 图片水印设置
    private String imagePath = "";
    private double imageScale = 1.0;
    private double imageOpacity = 1.0;
    
    // 位置设置
    private double x = 0.8; // 相对位置 (0-1)
    private double y = 0.8;
    private double rotation = 0.0; // 旋转角度
    
    // 输出设置
    private String outputFormat = "JPEG";
    private int jpegQuality = 90;
    private boolean preserveAspectRatio = true;
    private double scale = 1.0;
    
    // 构造函数
    public WatermarkSettings() {}
    
    // Getters and Setters
    public WatermarkType getType() { return type; }
    public void setType(WatermarkType type) { this.type = type; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }
    
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    
    public boolean isBold() { return bold; }
    public void setBold(boolean bold) { this.bold = bold; }
    
    public boolean isItalic() { return italic; }
    public void setItalic(boolean italic) { this.italic = italic; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public double getOpacity() { return opacity; }
    public void setOpacity(double opacity) { this.opacity = opacity; }
    
    public boolean isShadow() { return shadow; }
    public void setShadow(boolean shadow) { this.shadow = shadow; }
    
    public boolean isStroke() { return stroke; }
    public void setStroke(boolean stroke) { this.stroke = stroke; }
    
    public String getStrokeColor() { return strokeColor; }
    public void setStrokeColor(String strokeColor) { this.strokeColor = strokeColor; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public double getImageScale() { return imageScale; }
    public void setImageScale(double imageScale) { this.imageScale = imageScale; }
    
    public double getImageOpacity() { return imageOpacity; }
    public void setImageOpacity(double imageOpacity) { this.imageOpacity = imageOpacity; }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    public double getRotation() { return rotation; }
    public void setRotation(double rotation) { this.rotation = rotation; }
    
    public String getOutputFormat() { return outputFormat; }
    public void setOutputFormat(String outputFormat) { this.outputFormat = outputFormat; }
    
    public int getJpegQuality() { return jpegQuality; }
    public void setJpegQuality(int jpegQuality) { this.jpegQuality = jpegQuality; }
    
    public boolean isPreserveAspectRatio() { return preserveAspectRatio; }
    public void setPreserveAspectRatio(boolean preserveAspectRatio) { this.preserveAspectRatio = preserveAspectRatio; }
    
    public double getScale() { return scale; }
    public void setScale(double scale) { this.scale = scale; }
    
    // 辅助方法
    @JsonIgnore
    public javafx.scene.paint.Color getFxColor() {
        return javafx.scene.paint.Color.web(color);
    }
    
    @JsonIgnore
    public javafx.scene.paint.Color getFxStrokeColor() {
        return javafx.scene.paint.Color.web(strokeColor);
    }
    
    public void setFxColor(Color color) {
        this.color = String.format("#%02X%02X%02X", 
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    public void setFxStrokeColor(Color color) {
        this.strokeColor = String.format("#%02X%02X%02X", 
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    // 复制方法
    public WatermarkSettings copy() {
        WatermarkSettings copy = new WatermarkSettings();
        copy.type = this.type;
        copy.text = this.text;
        copy.fontFamily = this.fontFamily;
        copy.fontSize = this.fontSize;
        copy.bold = this.bold;
        copy.italic = this.italic;
        copy.color = this.color;
        copy.opacity = this.opacity;
        copy.shadow = this.shadow;
        copy.stroke = this.stroke;
        copy.strokeColor = this.strokeColor;
        copy.imagePath = this.imagePath;
        copy.imageScale = this.imageScale;
        copy.imageOpacity = this.imageOpacity;
        copy.x = this.x;
        copy.y = this.y;
        copy.rotation = this.rotation;
        copy.outputFormat = this.outputFormat;
        copy.jpegQuality = this.jpegQuality;
        copy.preserveAspectRatio = this.preserveAspectRatio;
        copy.scale = this.scale;
        return copy;
    }
}
