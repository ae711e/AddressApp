/*
 * Copyright (c) 2017. Aleksey Eremin
 * 04.03.17 11:12
 */

package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
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




}
