package com.watermark.gui.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import java.io.File;

/**
 * 图片文件数据模型
 */
public class ImageFile {
    
    private final File file;
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Image> thumbnail = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> fullImage = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty("待处理");
    private final BooleanProperty selected = new SimpleBooleanProperty(true);
    
    public ImageFile(File file) {
        this.file = file;
        this.name.set(file.getName());
        loadThumbnail();
    }
    
    private void loadThumbnail() {
        try {
            // 加载缩略图（最大150x150）
            Image thumb = new Image("file:" + file.getAbsolutePath(), 150, 150, true, true);
            this.thumbnail.set(thumb);
            
            // 加载完整图片
            Image full = new Image("file:" + file.getAbsolutePath());
            this.fullImage.set(full);
        } catch (Exception e) {
            System.err.println("加载图片失败: " + file.getName() + " - " + e.getMessage());
        }
    }
    
    // Getters
    public File getFile() { return file; }
    public String getName() { return name.get(); }
    public Image getThumbnail() { return thumbnail.get(); }
    public Image getFullImage() { return fullImage.get(); }
    public String getStatus() { return status.get(); }
    public boolean isSelected() { return selected.get(); }
    
    // Property getters
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<Image> thumbnailProperty() { return thumbnail; }
    public ObjectProperty<Image> fullImageProperty() { return fullImage; }
    public StringProperty statusProperty() { return status; }
    public BooleanProperty selectedProperty() { return selected; }
    
    // Setters
    public void setStatus(String status) { this.status.set(status); }
    public void setSelected(boolean selected) { this.selected.set(selected); }
    
    // 辅助方法
    public boolean isImageFile() {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
               name.endsWith(".png") || name.endsWith(".bmp") || 
               name.endsWith(".tiff") || name.endsWith(".tif");
    }
    
    public String getFileExtension() {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(lastDot) : "";
    }
    
    public String getFileNameWithoutExtension() {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(0, lastDot) : name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
