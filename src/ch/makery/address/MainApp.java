/*
 * Copyright (c) 2017. Aleksey Eremin
 * 03.03.17 14:34
 */

package ch.makery.address;

import ch.makery.address.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ae on 03.03.2017.
 * Урок JavaFX 8
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part1/
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part2/
 *
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    // данные в виде наблюдаемого списка
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    // запуск программы
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Конструктор, который заполняет тестовые данные
     */
    public MainApp()
    {
        personData.add(new Person("Вася", "Пупкин"));
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
        
    }
    
    /**
     * Возвращает данные в виде наблюдаемого списка адресатов
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception
    {
        this.primaryStage = stage;
        this.primaryStage.setTitle("AddressApp");
        
        initRootLayout();
        
        showPersonOverview();
        
    }
    
    /**
     * Инициализируем корневой макет
     */
    public void initRootLayout()
    {
        try {
            // загружаем корневой макет из fxml файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml "));
            this.rootLayout = (BorderPane) loader.load();
            
            // отображаем сцену, содержащую корневой макет
            Scene scene = new Scene(this.rootLayout);
            // на подмостки выводим сцену
            this.primaryStage.setScene(scene);
            // показываем подмостки (освещаем их)
            this.primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Отображает в корневом макете макет со сведениями об адресате
     */
    public void showPersonOverview()
    {
        try {
            // загружаем данные об адресатах
            FXMLLoader loader = new FXMLLoader(); // загружатель fxml файлов
            loader.setLocation(MainApp.class.getResource("view/PersonView.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // помещаем макет сведений в центр корневого макета
            this.rootLayout.setCenter(personOverview);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Возвращает подмостки
     * @return подмостки
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
} // end of class
