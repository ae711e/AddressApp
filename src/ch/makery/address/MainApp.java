/*
 * Copyright (c) 2017. Aleksey Eremin
 * 03.03.17 14:34
 */

package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

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
            
            // даем контроллеру доступ к главному приложению
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            // показываем подмостки (освещаем их)
            this.primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // попытаемся загрузить последний загруженный файл с данными
        File file = getPersonFilePath();
        if(file != null) {
            loadPersonDataFromFile(file);
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
     * Открывает диалоговое окно для изменения деталей указанного адресата
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
    
    
    // ключ для сохранения имени файла в преференсах
    private final String PREFS_KEY="filePath";
    
    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной операционной системы.
     * Если preference не найдет, то возвращается null
     * http://code.makery.ch/library/javafx-8-tutorial/ru/part5/
     * раздел "Сохранение пользовательских настроек"
     * @return  файл
     */
    public File getPersonFilePath()
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get(PREFS_KEY, null);
        if(filePath != null) {
            return new File(filePath);
        }
        return null;
    }
    
    /**
     * Задает путь текущему загруженному файлу. Этот путь сохраняется в реестре,
     * специфичном для конкретной операционной системы.
     * http://code.makery.ch/library/javafx-8-tutorial/ru/part5/
     * раздел "Сохранение пользовательских настроек"
     * @param file  файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(File file)
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if(file != null) {
            prefs.put(PREFS_KEY, file.getPath());
            // обновление заголовка подмостков
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove(PREFS_KEY);
            // обновления заголовка окна
            primaryStage.setTitle("AddressApp");
        }
    }
    
    // урок 5
    // http://code.makery.ch/library/javafx-8-tutorial/ru/part5/.
    
    /**
     * Загружает информацию об адресатах из указанного файла
     * Текущая информация об адресатах будет заменена.
     * @param file  файл с данными в XML формате
     */
    public void loadPersonDataFromFile(File file)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML-файла и демаршализация
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            personData.clear();
            personData.addAll(wrapper.getPersons());
            
            // сохраняем путь к файлу в префсах, то бишь, в реестре
            setPersonFilePath(file);
            
        } catch (JAXBException e) {
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Не могу загрузить данные.");
            alert.setContentText("Не загружается файл: " + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    public void savePersonDataToFile(File file)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            // обертывавем данные об адресатах
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);
            
            // маршалируем (сопровождаем) и сохраняем в файл
            m.marshal(wrapper, file);
            
            // сохраним путь к файлу в реестре
            setPersonFilePath(file);
            
        } catch (JAXBException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Не могу сохранить данные.");
            alert.setContentText("Не записывается файл: " + file.getPath());
            
            alert.showAndWait();
        }
    }

    public void showBirthdayStatistics()
    {
        try {
            // загружаем fxml-файл и создаем новые подмостки
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // передает адресатов в контроллер
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
} // end of class
