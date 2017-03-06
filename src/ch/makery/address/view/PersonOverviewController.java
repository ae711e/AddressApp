/*
 * Copyright (c) 2017. Aleksey Eremin
 * 04.03.17 11:12
 */

package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Created by Алексей on 04.03.2017.
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part2/
 * раздел Класс PersonOverviewController
 *
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
 */
public class PersonOverviewController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // ссылка на главное приложение
    private MainApp mainApp;

    /**
     * Конструктор
     * конструктор вызывается раньше метода initialize()
     */
    public PersonOverviewController()
    {
        ;//
    }

    /**
     * Инициализация контроллера.
     * этот метод вызывается после того, как будет загружен fxml-файл
     */
    @FXML
    private void initialize()
    {
        // инициализация таблицы персон с 2 колонками (столбцами)
        // (используется лямбда-выражение)
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        //
        // очистка дополнительной информации об адресате
        showPersonDetails(null);
        //
        // слушаем изменение выбора и при изменении отображаем дполнительную
        // информацию об адресате
        // http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
        // раздел "Наблюдение за выбором адресатов в таблице".
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue)
        );

    }

    /**
     * вызывается главным приложением, которое дает ссылку на себя
     * @param app   главное приложение
     */
    public void setMainApp(MainApp app)
    {
        this.mainApp = app;

        // добавим в таблицу данные из наблюдаемого списка
        this.personTable.setItems(mainApp.getPersonData());
    }


    // 3 урок
    // http://code.makery.ch/library/javafx-8-tutorial/ru/part3/

    /**
     * Заполняет все текстовые поля, отображая подробности в адресате
     * Если указанный адресат null, то все текстовые поля очищаются
     * @param person    адресат (объект типа Person)
     */
    private void showPersonDetails(Person person)
    {
        if(person != null) {
            // заполняем поля Label информацией из объекта
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            // переведем дату дня рождения в строку
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));

        } else {
            // если null, то убираем весь текст из полей
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку Delete (Удалить)
     * обработка отсутствия выбора - отображение диалогового окна
     * Чтобы посмотреть другие примеры использования диалогов,
     * откройте статью в блоге автора учебника "Диалоги JavaFX":
     * http://code.makery.ch/blog/javafx-dialogs-official/
     */
    // http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
    // раздел "Кнопка Delete"
    @FXML
    private void handleDeletePerson()
    {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // ничего не выбрано
            // обработка отсутствия выбора - отображение диалогового окна
            // Чтобы посмотреть другие примеры использования диалогов,
            // откройте статью в блоге автора учебника "Диалоги JavaFX":
            // http://code.makery.ch/blog/javafx-dialogs-official/ .
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("Не выбрано ни одного адресата");
            alert.setContentText("Выбери какого-нибудь адресата в таблице");
            alert.showAndWait();
        }
    }
    
    /**
     * Вызывается, когда пользователь тыкает в кнопку New
     * Открывает диалоговое окно с дополнительной информацией об адресате
     * http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
     * раздел "Вызов диалога редактирования"
     */
    @FXML
    private void handleNewPerson()
    {
        Person tempPerson = new Person();
        boolean okClick;
        okClick = mainApp.showPersonEditDialog(tempPerson);
        if(okClick) {
            // если нажали Ok, запишем персону
            mainApp.getPersonData().add(tempPerson);
        }
    }
    
    /**
     * Вызывается, когда пользовтаель тычет в кнопку Edit
     */
    @FXML
    private void handleEditPerson()
    {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPerson != null) {
            boolean okClick;
            okClick = mainApp.showPersonEditDialog(selectedPerson);
            if(okClick) {
                showPersonDetails(selectedPerson);
            }
        } else {
            // ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("Не выбран адресат");
            alert.setContentText("Выберите в таблице адресата для редактирования");
            
            alert.showAndWait();
        }
    }
    

} // end of class
