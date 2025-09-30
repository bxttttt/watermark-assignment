package com.watermark.gui.controller;

import com.watermark.gui.model.ImageFile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 图片文件列表单元格
 */
public class ImageFileListCell extends ListCell<ImageFile> {
    
    private final HBox container;
    private final ImageView thumbnailView;
    private final VBox infoContainer;
    private final Label nameLabel;
    private final Label statusLabel;
    private final CheckBox selectCheckBox;
    
    public ImageFileListCell() {
        container = new HBox(10);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.CENTER_LEFT);
        
        // 缩略图
        thumbnailView = new ImageView();
        thumbnailView.setFitWidth(60);
        thumbnailView.setFitHeight(60);
        thumbnailView.setPreserveRatio(true);
        
        // 信息容器
        infoContainer = new VBox(2);
        infoContainer.setAlignment(Pos.CENTER_LEFT);
        
        nameLabel = new Label();
        nameLabel.setStyle("-fx-font-weight: bold;");
        
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
        
        infoContainer.getChildren().addAll(nameLabel, statusLabel);
        
        // 选择框
        selectCheckBox = new CheckBox();
        selectCheckBox.setSelected(true);
        
        container.getChildren().addAll(thumbnailView, infoContainer, selectCheckBox);
        
        // 绑定选择状态
        selectCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (getItem() != null) {
                getItem().setSelected(newVal);
            }
        });
    }
    
    @Override
    protected void updateItem(ImageFile item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
        } else {
            thumbnailView.setImage(item.getThumbnail());
            nameLabel.setText(item.getName());
            statusLabel.setText(item.getStatus());
            selectCheckBox.setSelected(item.isSelected());
            
            setGraphic(container);
        }
    }
}
