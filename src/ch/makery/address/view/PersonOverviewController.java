/*
 * Copyright (c) 2017. Aleksey Eremin
 * 04.03.17 11:12
 */

package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Created by Алексей on 04.03.2017.
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part2/
 * раздел Класс PersonOverviewController
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






}
