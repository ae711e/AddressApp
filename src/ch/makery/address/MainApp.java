/*
 * Copyright (c) 2017. Aleksey Eremin
 * 03.03.17 14:34
 */

package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
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
     * @return  наблюдаемый список
     */
    public ObservableList<Person> getPersonData()
    {
        return personData;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception
    {
        this.primaryStage = stage;
        this.primaryStage.setTitle("AddressApp");
        // установить иконку для приложения
        // http://code.makery.ch/library/javafx-8-tutorial/ru/part4/
        // раздел "Установка иконки для сцены"
        this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_64.png"));
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
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // помещаем макет сведений в центр корневого макета
            this.rootLayout.setCenter(personOverview);

            // дадим контролеру доступ к главному приложению
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

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
    
    /**
     * Открывает диалоговое окно для изменения деталей казанного адресата
     * Если пользовтаель тыкнул Ok, то изменения сохраняются в предоставленном
     * объекте адресата и возвращается true.
     * http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
     * @param person    объект адресата, который надо редактировать
     * @return  true - если пользователь ткнул Ok, в противном случае false
     */
    public boolean showPersonEditDialog(Person person)
    {
        try {
            // загружаем fxml-файл и создаем новые подмостки для всплывающего
            // диалогового окна
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // создаем подмостки диалогового окна
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            // добавим иконку на диалоговое окно
            dialogStage.getIcons().add(new Image("file:resources/images/person_32.png"));
            // создадим сцену для отрисовки окна и поместим на
            // эту сцену Заякоренную панель с полями редактирования.
            Scene scene = new Scene(page);
            // выведем на подмостки нашу сцену с заякоренной панелью
            dialogStage.setScene(scene);
            
            // загрузим контроллер редактирования персоны
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);  // передадим контроллеру его подмостки
            controller.setPerson(person);       // передадим контроллеру его персону
            
            // отобразим диалоговое окно (высветив подмостки) и ждем пока пользователь его не закроет
            dialogStage.showAndWait();
            
            boolean r;
            r = controller.isOkClicked();
            return r;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
} // end of class
