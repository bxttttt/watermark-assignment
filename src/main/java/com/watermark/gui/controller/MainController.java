package com.watermark.gui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermark.gui.model.*;
import com.watermark.gui.service.ImageProcessingService;
import com.watermark.gui.service.TemplateService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 主界面控制器
 */
public class MainController implements Initializable {
    
    // FXML注入的组件
    @FXML private BorderPane mainContainer;
    @FXML private HBox topContainer;
    @FXML private VBox leftPanel;
    @FXML private VBox rightPanel;
    @FXML private VBox centerPanel;
    
    // 左侧面板 - 文件管理
    @FXML private Label fileSectionTitle;
    @FXML private Button addFilesButton;
    @FXML private Button addFolderButton;
    @FXML private Button clearFilesButton;
    @FXML private ListView<ImageFile> imageListView;
    @FXML private CheckBox selectAllCheckBox;
    
    // 右侧面板 - 水印设置
    @FXML private Label settingsSectionTitle;
    @FXML private TabPane settingsTabPane;
    @FXML private Tab textWatermarkTab;
    @FXML private Tab imageWatermarkTab;
    @FXML private Tab outputTab;
    @FXML private Tab templateTab;
    
    // 文本水印设置
    @FXML private TextField textField;
    @FXML private ComboBox<String> fontFamilyComboBox;
    @FXML private Spinner<Integer> fontSizeSpinner;
    @FXML private CheckBox boldCheckBox;
    @FXML private CheckBox italicCheckBox;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider opacitySlider;
    @FXML private CheckBox shadowCheckBox;
    @FXML private CheckBox strokeCheckBox;
    @FXML private ColorPicker strokeColorPicker;
    
    // 图片水印设置
    @FXML private Button selectImageButton;
    @FXML private Label imagePathLabel;
    @FXML private Slider imageScaleSlider;
    @FXML private Slider imageOpacitySlider;
    
    // 输出设置
    @FXML private ComboBox<String> outputFormatComboBox;
    @FXML private Slider jpegQualitySlider;
    @FXML private Label jpegQualityLabel;
    @FXML private CheckBox preserveAspectRatioCheckBox;
    @FXML private Spinner<Double> scaleSpinner;
    
    // 模板管理
    @FXML private ComboBox<Template> templateComboBox;
    @FXML private TextField templateNameField;
    @FXML private TextArea templateDescriptionArea;
    @FXML private Button saveTemplateButton;
    @FXML private Button deleteTemplateButton;
    
    // 底部操作区域
    @FXML private VBox bottomContainer;
    @FXML private Button previewButton;
    @FXML private Button exportButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;
    
    // 中心预览区域
    @FXML private VBox previewContainer;
    @FXML private ImageView previewImageView;
    @FXML private Label previewLabel;
    
    // 数据模型
    private final ObservableList<ImageFile> imageFiles = FXCollections.observableArrayList();
    private final ObservableList<Template> templates = FXCollections.observableArrayList();
    private WatermarkSettings currentSettings = new WatermarkSettings();
    private ImageFile selectedImage = null;
    
    // 服务类
    private final ImageProcessingService imageService = new ImageProcessingService();
    private final TemplateService templateService = new TemplateService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComponents();
        setupEventHandlers();
        loadTemplates();
        updatePreview();
    }
    
    private void initializeComponents() {
        // 初始化字体选择
        fontFamilyComboBox.getItems().addAll(
            "Arial", "Times New Roman", "Helvetica", "Georgia", "Verdana", "Courier New"
        );
        fontFamilyComboBox.setValue("Arial");
        
        // 初始化字体大小
        fontSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 200, 24));
        
        // 初始化输出格式
        outputFormatComboBox.getItems().addAll("JPEG", "PNG");
        outputFormatComboBox.setValue("JPEG");
        
        // 初始化比例
        scaleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 5.0, 1.0, 0.1));
        
        // 设置图片列表
        imageListView.setItems(imageFiles);
        imageListView.setCellFactory(listView -> new ImageFileListCell());
        
        // 设置模板列表
        templateComboBox.setItems(templates);
        templateComboBox.setCellFactory(listView -> new TemplateListCell());
        
        // 设置拖拽支持
        setupDragAndDrop();
    }
    
    private void setupEventHandlers() {
        // 文件操作
        addFilesButton.setOnAction(e -> addFiles());
        addFolderButton.setOnAction(e -> addFolder());
        clearFilesButton.setOnAction(e -> clearFiles());
        selectAllCheckBox.setOnAction(e -> selectAllImages(selectAllCheckBox.isSelected()));
        
        // 文本水印设置
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setText(newVal);
            updatePreview();
        });
        
        fontFamilyComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setFontFamily(newVal);
            updatePreview();
        });
        
        fontSizeSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setFontSize(newVal);
            updatePreview();
        });
        
        boldCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setBold(newVal);
            updatePreview();
        });
        
        italicCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setItalic(newVal);
            updatePreview();
        });
        
        colorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setFxColor(newVal);
            updatePreview();
        });
        
        opacitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setOpacity(newVal.doubleValue() / 100.0);
            updatePreview();
        });
        
        shadowCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setShadow(newVal);
            updatePreview();
        });
        
        strokeCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setStroke(newVal);
            updatePreview();
        });
        
        strokeColorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setFxStrokeColor(newVal);
            updatePreview();
        });
        
        // 图片水印设置
        selectImageButton.setOnAction(e -> selectWatermarkImage());
        imageScaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setImageScale(newVal.doubleValue() / 100.0);
            updatePreview();
        });
        
        imageOpacitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setImageOpacity(newVal.doubleValue() / 100.0);
            updatePreview();
        });
        
        // 输出设置
        outputFormatComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setOutputFormat(newVal);
            updateJpegQualityVisibility();
        });
        
        jpegQualitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setJpegQuality(newVal.intValue());
            jpegQualityLabel.setText("质量: " + newVal.intValue() + "%");
        });
        
        preserveAspectRatioCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setPreserveAspectRatio(newVal);
        });
        
        scaleSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSettings.setScale(newVal);
        });
        
        // 模板管理
        templateComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadTemplate(newVal);
            }
        });
        
        saveTemplateButton.setOnAction(e -> saveTemplate());
        deleteTemplateButton.setOnAction(e -> deleteTemplate());
        
        // 操作按钮
        previewButton.setOnAction(e -> updatePreview());
        exportButton.setOnAction(e -> exportImages());
        
        // 图片选择
        imageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedImage = newVal;
            updatePreview();
        });
        
        // 初始化设置
        updateJpegQualityVisibility();
    }
    
    private void setupDragAndDrop() {
        mainContainer.setOnDragOver(this::handleDragOver);
        mainContainer.setOnDragDropped(this::handleDragDropped);
    }
    
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
    
    private void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        
        if (db.hasFiles()) {
            List<File> files = db.getFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    addFolderFiles(file);
                } else if (isImageFile(file)) {
                    imageFiles.add(new ImageFile(file));
                }
            }
            success = true;
        }
        
        event.setDropCompleted(success);
        event.consume();
    }
    
    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
               name.endsWith(".png") || name.endsWith(".bmp") || 
               name.endsWith(".tiff") || name.endsWith(".tif");
    }
    
    // 文件操作方法
    private void addFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片文件");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("图片文件", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.tiff", "*.tif"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif")
        );
        
        List<File> files = fileChooser.showOpenMultipleDialog(getStage());
        if (files != null) {
            for (File file : files) {
                if (isImageFile(file)) {
                    imageFiles.add(new ImageFile(file));
                }
            }
        }
    }
    
    private void addFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择图片文件夹");
        
        File folder = directoryChooser.showDialog(getStage());
        if (folder != null) {
            addFolderFiles(folder);
        }
    }
    
    private void addFolderFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    imageFiles.add(new ImageFile(file));
                }
            }
        }
    }
    
    private void clearFiles() {
        imageFiles.clear();
        selectedImage = null;
        updatePreview();
    }
    
    private void selectAllImages(boolean selected) {
        for (ImageFile imageFile : imageFiles) {
            imageFile.setSelected(selected);
        }
    }
    
    private void selectWatermarkImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择水印图片");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG (推荐)", "*.png")
        );
        
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            currentSettings.setImagePath(file.getAbsolutePath());
            currentSettings.setType(WatermarkSettings.WatermarkType.IMAGE);
            imagePathLabel.setText(file.getName());
            updatePreview();
        }
    }
    
    private void updateJpegQualityVisibility() {
        boolean isJpeg = "JPEG".equals(outputFormatComboBox.getValue());
        jpegQualitySlider.setVisible(isJpeg);
        jpegQualityLabel.setVisible(isJpeg);
    }
    
    private void updatePreview() {
        if (selectedImage != null && selectedImage.getFullImage() != null) {
            Task<Image> task = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return imageService.createPreviewImage(selectedImage.getFullImage(), currentSettings);
                }
                
                @Override
                protected void succeeded() {
                    previewImageView.setImage(getValue());
                    previewLabel.setText(selectedImage.getName());
                }
                
                @Override
                protected void failed() {
                    previewLabel.setText("预览生成失败");
                }
            };
            
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            previewLabel.setText("请选择图片进行预览");
        }
    }
    
    private void exportImages() {
        List<ImageFile> selectedImages = imageFiles.stream()
            .filter(ImageFile::isSelected)
            .collect(Collectors.toList());
        
        if (selectedImages.isEmpty()) {
            showAlert("提示", "请选择要导出的图片", Alert.AlertType.WARNING);
            return;
        }
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择输出文件夹");
        
        File outputDir = directoryChooser.showDialog(getStage());
        if (outputDir != null) {
            Task<Void> exportTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("开始导出...");
                    
                    for (int i = 0; i < selectedImages.size(); i++) {
                        ImageFile imageFile = selectedImages.get(i);
                        updateMessage("正在处理: " + imageFile.getName());
                        updateProgress(i + 1, selectedImages.size());
                        
                        try {
                            imageService.processImage(imageFile, currentSettings, outputDir);
                            Platform.runLater(() -> imageFile.setStatus("已导出"));
                        } catch (Exception e) {
                            Platform.runLater(() -> imageFile.setStatus("导出失败"));
                            System.err.println("导出失败: " + imageFile.getName() + " - " + e.getMessage());
                        }
                    }
                    
                    updateMessage("导出完成");
                    return null;
                }
            };
            
            progressBar.progressProperty().bind(exportTask.progressProperty());
            statusLabel.textProperty().bind(exportTask.messageProperty());
            
            Thread thread = new Thread(exportTask);
            thread.setDaemon(true);
            thread.start();
        }
    }
    
    // 模板管理方法
    private void loadTemplates() {
        templates.clear();
        templates.addAll(templateService.loadTemplates());
    }
    
    private void loadTemplate(Template template) {
        currentSettings = template.getSettings().copy();
        applySettingsToUI();
        updatePreview();
    }
    
    private void applySettingsToUI() {
        textField.setText(currentSettings.getText());
        fontFamilyComboBox.setValue(currentSettings.getFontFamily());
        fontSizeSpinner.getValueFactory().setValue(currentSettings.getFontSize());
        boldCheckBox.setSelected(currentSettings.isBold());
        italicCheckBox.setSelected(currentSettings.isItalic());
        colorPicker.setValue(currentSettings.getFxColor());
        opacitySlider.setValue(currentSettings.getOpacity() * 100);
        shadowCheckBox.setSelected(currentSettings.isShadow());
        strokeCheckBox.setSelected(currentSettings.isStroke());
        strokeColorPicker.setValue(currentSettings.getFxStrokeColor());
        imageScaleSlider.setValue(currentSettings.getImageScale() * 100);
        imageOpacitySlider.setValue(currentSettings.getImageOpacity() * 100);
        outputFormatComboBox.setValue(currentSettings.getOutputFormat());
        jpegQualitySlider.setValue(currentSettings.getJpegQuality());
        preserveAspectRatioCheckBox.setSelected(currentSettings.isPreserveAspectRatio());
        scaleSpinner.getValueFactory().setValue(currentSettings.getScale());
    }
    
    private void saveTemplate() {
        String name = templateNameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("错误", "请输入模板名称", Alert.AlertType.ERROR);
            return;
        }
        
        String description = templateDescriptionArea.getText().trim();
        Template template = new Template(name, description, currentSettings);
        
        if (templates.contains(template)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认");
            alert.setHeaderText("模板已存在");
            alert.setContentText("是否要覆盖现有模板？");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                templates.remove(template);
                templates.add(template);
                templateService.saveTemplates(templates);
                showAlert("成功", "模板已保存", Alert.AlertType.INFORMATION);
            }
        } else {
            templates.add(template);
            templateService.saveTemplates(templates);
            templateComboBox.setValue(template);
            showAlert("成功", "模板已保存", Alert.AlertType.INFORMATION);
        }
    }
    
    private void deleteTemplate() {
        Template selected = templateComboBox.getValue();
        if (selected == null) {
            showAlert("提示", "请选择要删除的模板", Alert.AlertType.WARNING);
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText("删除模板");
        alert.setContentText("确定要删除模板 \"" + selected.getName() + "\" 吗？");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            templates.remove(selected);
            templateService.saveTemplates(templates);
            templateComboBox.setValue(null);
            showAlert("成功", "模板已删除", Alert.AlertType.INFORMATION);
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Stage getStage() {
        return (Stage) mainContainer.getScene().getWindow();
    }
}
