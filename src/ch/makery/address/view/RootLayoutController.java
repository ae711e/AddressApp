/*
 * Copyright (c) 2017. Aleksey Eremin
 * 07.03.17 14:01
 */

package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by ae on 07.03.2017.
 * Контроллер для корневого макета, который предоставляет
 * базовый макет приложения, содержащий строку меню и место,
 * где будут размещены остальные элементы JavaFX
 *
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part5/
 * раздел "Класс RootLayoutController"
 *
 * @author Marco Jakob
 */

public class RootLayoutController {
    // ссылка на главное приложение
    private MainApp mainApp;
    
    /**
     * Вызывается главным приложением, чтобы оставить ссылку на себя
     * @param mainApp   главное приложение
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
    
    /**
     * Создает пустую адресную книгу
     */
    @FXML
    private void handleNew()
    {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }
    
    /**
     * Открывает диалоговое окно для выбора xml файла с данными об адресатах
     */
    @FXML
    private void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();
        
        // задаем фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }
    
    /**
     * Сохраняет файл в файл адресатов, который открыт в настоящее время.
     * Если файл не открыт, то отображается диалог "Save as"
     */
    @FXML
    private void handleSave()
    {
        File personFile = mainApp.getPersonFilePath();
        if(personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSavAs();
        }
    }
    
    /**
     * Открывает диалоговое окно, чтобы пользователь выбрал имя файла,
     * куда сохранить данные
     */
    @FXML
    private void handleSavAs()
    {
        FileChooser fileChooser = new FileChooser();
        // задаем фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if(file != null) {
            // убедимся, что у файла корректное расширение
            if(!file.getPath().endsWith("xml")) {
                file = new File(file.getPath() + ".xml");  // если что, добавить требуемое расширение
            }
            mainApp.savePersonDataToFile(file);
        }
    }
    
    /**
     * Выводит информацию о приложении
     */
    @FXML
    private void handleAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Уроки JavaFX 8\nWeb: http://code.makery.ch/library/javafx-8-tutorial/ru/");
        alert.showAndWait();
    }
    
    /**
     * Закрывает приложение
     */
    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

    @FXML
    private void handleShowBirthdayStatistics()
    {
        mainApp.showBirthdayStatistics();
    }

} // end of class
