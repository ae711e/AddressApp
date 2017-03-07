/*
 * Copyright (c) 2017. Aleksey Eremin
 * 05.03.17 20:45
 */

package ch.makery.address.view;

import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
    
    /**
     * Задает адресата, информацию о котором будем менять.
     * @param person    адресат
     */
    public void setPerson(Person person)
    {
        this.person = person;
        
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }
    
    /**
     * Возвращает true если пользователь кликнул на Ok, false - если не кликал
     * @return  true - пользователь ткнул кнопку Ok
     */
    public boolean isOkClicked()
    {
        return onClicked;
    }
    
    /**
     * Вызывается когда пользователь нажал на кнопку Ok
     */
    @FXML
    private void handleOk()
    {
        if(isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            
            onClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Вызывается, когда пользователь ткнул Cancel
     */
    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }
    
    private boolean isInputValid()
    {
        String errorMessage="";
    
        if(firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += " Не указано First Name\n";
        }
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += " Не указано Last Name\n";
        }
        if(streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += " Не указана Street\n";
        }
        if(postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += " Не указано Postal Code\n";
        } else {
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Указано неправильное число для Postal Code\n";
            }
        }
        if(cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += " Не указан City";
        }
        if(birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += " Не указано Birthday";
        } else {
            if(!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += " Введена неправильная дата. Используй dd.mm.yyyy";
            }
        }
        
        if(errorMessage.length() == 0) {
            return true;
        } else {
            //показываем диалоговое окно об ошибке ввода
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("Ошибка заполнения полей");
            alert.setHeaderText("Скорректируйте заполнение полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
} // end of class
