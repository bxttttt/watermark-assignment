package com.watermark.gui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.watermark.gui.model.Template;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板管理服务类
 */
public class TemplateService {
    
    private static final String TEMPLATES_FILE = "templates.json";
    private final ObjectMapper objectMapper;
    private final File templatesFile;
    
    public TemplateService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        // 创建模板文件路径
        String userHome = System.getProperty("user.home");
        File appDir = new File(userHome, ".watermark-tool");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        
        this.templatesFile = new File(appDir, TEMPLATES_FILE);
    }
    
    /**
     * 加载模板列表
     */
    public List<Template> loadTemplates() {
        if (!templatesFile.exists()) {
            createDefaultTemplates();
        }
        
        try {
            Template[] templates = objectMapper.readValue(templatesFile, Template[].class);
            List<Template> templateList = new ArrayList<>();
            
            for (Template template : templates) {
                if (template != null) {
                    templateList.add(template);
                }
            }
            
            return templateList;
        } catch (IOException e) {
            System.err.println("加载模板失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * 保存模板列表
     */
    public void saveTemplates(ObservableList<Template> templates) {
        try {
            List<Template> templateList = new ArrayList<>(templates);
            objectMapper.writeValue(templatesFile, templateList);
        } catch (IOException e) {
            System.err.println("保存模板失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建默认模板
     */
    private void createDefaultTemplates() {
        List<Template> defaultTemplates = new ArrayList<>();
        
        // 默认文本水印模板
        com.watermark.gui.model.WatermarkSettings defaultTextSettings = new com.watermark.gui.model.WatermarkSettings();
        defaultTextSettings.setText("水印");
        defaultTextSettings.setFontFamily("Arial");
        defaultTextSettings.setFontSize(24);
        defaultTextSettings.setX(0.8);
        defaultTextSettings.setY(0.8);
        defaultTextSettings.setOpacity(0.8);
        
        Template defaultTextTemplate = new Template(
            "默认文本水印",
            "右下角白色半透明文本水印",
            defaultTextSettings
        );
        defaultTemplates.add(defaultTextTemplate);
        
        // 保存默认模板
        try {
            objectMapper.writeValue(templatesFile, defaultTemplates);
        } catch (IOException e) {
            System.err.println("创建默认模板失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除模板
     */
    public void deleteTemplate(Template template) {
        List<Template> templates = loadTemplates();
        templates.remove(template);
        saveTemplates(javafx.collections.FXCollections.observableArrayList(templates));
    }
    
    /**
     * 获取模板文件路径
     */
    public String getTemplatesFilePath() {
        return templatesFile.getAbsolutePath();
    }
}
