package com.watermark.gui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 水印模板数据模型
 */
public class Template {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("settings")
    private WatermarkSettings settings;
    
    @JsonProperty("createdAt")
    private String createdAt;
    
    @JsonProperty("modifiedAt")
    private String modifiedAt;
    
    public Template() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.modifiedAt = this.createdAt;
    }
    
    public Template(String name, String description, WatermarkSettings settings) {
        this();
        this.name = name;
        this.description = description;
        this.settings = settings.copy();
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public WatermarkSettings getSettings() { return settings; }
    public void setSettings(WatermarkSettings settings) { 
        this.settings = settings.copy();
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
    public String getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(String modifiedAt) { this.modifiedAt = modifiedAt; }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Template template = (Template) obj;
        return name != null ? name.equals(template.name) : template.name == null;
    }
    
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
