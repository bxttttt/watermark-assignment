package com.watermark.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 图片水印工具GUI应用程序主类
 */
public class WatermarkApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/watermark/gui/main.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 800);
        
        primaryStage.setTitle("图片水印工具 v1.0");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        
        // 设置应用程序图标
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/watermark/gui/icon.png")));
        } catch (Exception e) {
            // 如果没有图标文件，忽略错误
        }
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
