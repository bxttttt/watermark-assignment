package com.watermark;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * EXIF信息读取器
 * 用于从图片文件中提取拍摄时间信息
 */
public class ExifReader {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * 从图片文件中读取拍摄日期
     * 
     * @param imageFile 图片文件
     * @return 格式化的日期字符串 (yyyy-MM-dd)，如果无法读取则返回null
     */
    public static String readPhotoDate(File imageFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            
            // 尝试从EXIF数据中获取拍摄时间
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                Date dateTimeOriginal = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (dateTimeOriginal != null) {
                    LocalDate localDate = dateTimeOriginal.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.format(DATE_FORMATTER);
                }
                
                // 如果原始拍摄时间不存在，尝试获取数字化时间
                Date dateTimeDigitized = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);
                if (dateTimeDigitized != null) {
                    LocalDate localDate = dateTimeDigitized.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.format(DATE_FORMATTER);
                }
            }
            
            // 如果EXIF中没有日期信息，尝试从文件修改时间获取
            return getDateFromFileModificationTime(imageFile);
            
        } catch (ImageProcessingException | IOException e) {
            System.err.println("读取EXIF信息失败: " + imageFile.getName() + " - " + e.getMessage());
            // 如果读取EXIF失败，使用文件修改时间
            return getDateFromFileModificationTime(imageFile);
        }
    }
    
    /**
     * 从文件修改时间获取日期
     * 
     * @param imageFile 图片文件
     * @return 格式化的日期字符串
     */
    private static String getDateFromFileModificationTime(File imageFile) {
        try {
            Date lastModified = new Date(imageFile.lastModified());
            LocalDate localDate = lastModified.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return localDate.format(DATE_FORMATTER);
        } catch (Exception e) {
            System.err.println("获取文件修改时间失败: " + imageFile.getName() + " - " + e.getMessage());
            return LocalDate.now().format(DATE_FORMATTER);
        }
    }
    
    /**
     * 检查文件是否为支持的图片格式
     * 
     * @param file 文件
     * @return 是否为支持的图片格式
     */
    public static boolean isSupportedImageFormat(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || 
               fileName.endsWith(".png") || fileName.endsWith(".tiff") || 
               fileName.endsWith(".tif") || fileName.endsWith(".bmp") ||
               fileName.endsWith(".gif");
    }
}
