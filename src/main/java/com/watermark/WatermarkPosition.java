package com.watermark;

/**
 * 水印位置枚举
 */
public enum WatermarkPosition {
    TOP_LEFT("左上角"),
    TOP_RIGHT("右上角"),
    BOTTOM_LEFT("左下角"),
    BOTTOM_RIGHT("右下角"),
    CENTER("居中"),
    TOP_CENTER("顶部居中"),
    BOTTOM_CENTER("底部居中"),
    LEFT_CENTER("左侧居中"),
    RIGHT_CENTER("右侧居中");
    
    private final String description;
    
    WatermarkPosition(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
