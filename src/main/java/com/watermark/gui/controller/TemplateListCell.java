package com.watermark.gui.controller;

import com.watermark.gui.model.Template;
import javafx.scene.control.ListCell;

/**
 * 模板列表单元格
 */
public class TemplateListCell extends ListCell<Template> {
    
    @Override
    protected void updateItem(Template item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }
}
