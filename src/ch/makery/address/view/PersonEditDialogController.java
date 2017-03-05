/*
 * Copyright (c) 2017. Aleksey Eremin
 * 05.03.17 20:45
 */

package ch.makery.address.view;

import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by ae on 05.03.2017.
 * Окно для изменения информации об адресате
 * @author Marco Jakob
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
 *
 */

public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

    private Stage   dialogStage;
    private Person  person;
    private boolean onClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл, будет загружен
     */
    @FXML
    private void initialize()
    {

    }

    /**
     * Устанавливает подмостки для этого окна
     *
     * @param dialogStage   подмостки (от диалогового окна)
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    // Продолжение следует... 21:06 05.03.2017
    // http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
    // раздел "Создание контроллера"
    //  ю


} // end of class
