package com.watermark;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理器
 * 负责处理图片文件，添加水印并保存到指定目录
 */
public class ImageProcessor {
    
    private final WatermarkOptions options;
    
    public ImageProcessor(WatermarkOptions options) {
        this.options = options;
    }
    
    /**
     * 处理输入路径下的所有图片
     */
    public void processImages() {
        File inputPath = options.getInputPath();
        List<File> imageFiles = new ArrayList<>();
        
        // 收集图片文件
        if (inputPath.isFile()) {
            if (ExifReader.isSupportedImageFormat(inputPath)) {
                imageFiles.add(inputPath);
            } else {
                System.err.println("不支持的文件格式: " + inputPath.getName());
                return;
            }
        } else if (inputPath.isDirectory()) {
            collectImageFiles(inputPath, imageFiles);
        }
        
        if (imageFiles.isEmpty()) {
            System.out.println("未找到支持的图片文件");
            return;
        }
        
        // 创建输出目录
        File outputDir = createOutputDirectory(inputPath);
        if (outputDir == null) {
            return;
        }
        
        // 处理每个图片文件
        int processedCount = 0;
        int totalCount = imageFiles.size();
        
        System.out.println("开始处理 " + totalCount + " 个图片文件...");
        System.out.println("输出目录: " + outputDir.getAbsolutePath());
        
        for (File imageFile : imageFiles) {
            try {
                if (processSingleImage(imageFile, outputDir)) {
                    processedCount++;
                    if (options.isVerbose()) {
                        System.out.println("✓ 已处理: " + imageFile.getName());
                    }
                }
            } catch (Exception e) {
                System.err.println("✗ 处理失败: " + imageFile.getName() + " - " + e.getMessage());
                if (options.isVerbose()) {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("处理完成! 成功处理 " + processedCount + "/" + totalCount + " 个文件");
    }
    
    /**
     * 收集目录中的所有图片文件
     */
    private void collectImageFiles(File directory, List<File> imageFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && ExifReader.isSupportedImageFormat(file)) {
                    imageFiles.add(file);
                }
            }
        }
    }
    
    /**
     * 创建输出目录
     */
    private File createOutputDirectory(File inputPath) {
        File parentDir;
        String baseName;
        
        if (inputPath.isFile()) {
            parentDir = inputPath.getParentFile();
            baseName = inputPath.getParentFile().getName();
        } else {
            parentDir = inputPath.getParentFile();
            baseName = inputPath.getName();
        }
        
        String outputDirName = baseName + "_watermark";
        File outputDir = new File(parentDir, outputDirName);
        
        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                System.out.println("创建输出目录: " + outputDir.getAbsolutePath());
            } else {
                System.err.println("无法创建输出目录: " + outputDir.getAbsolutePath());
                return null;
            }
        } else {
            System.out.println("使用现有输出目录: " + outputDir.getAbsolutePath());
        }
        
        return outputDir;
    }
    
    /**
     * 处理单个图片文件
     */
    private boolean processSingleImage(File imageFile, File outputDir) throws IOException {
        // 读取EXIF日期信息
        String photoDate = ExifReader.readPhotoDate(imageFile);
        if (photoDate == null) {
            System.err.println("无法读取 " + imageFile.getName() + " 的拍摄日期信息");
            return false;
        }
        
        if (options.isVerbose()) {
            System.out.println("读取到拍摄日期: " + photoDate + " (" + imageFile.getName() + ")");
        }
        
        // 生成输出文件名
        String fileName = imageFile.getName();
        String baseName = getBaseFileName(fileName);
        String extension = getFileExtension(fileName);
        String outputFileName = baseName + options.getFileSuffix() + extension;
        File outputFile = new File(outputDir, outputFileName);
        
        // 绘制水印
        WatermarkDrawer.drawWatermark(
            imageFile,
            outputFile,
            photoDate,
            options.getFontSize(),
            options.getColor(),
            options.getPosition()
        );
        
        return true;
    }
    
    /**
     * 获取文件名（不含扩展名）
     */
    private String getBaseFileName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
