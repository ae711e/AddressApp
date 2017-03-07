/*
 * Copyright (c) 2017. Aleksey Eremin
 * 03.03.17 16:04
 */

package ch.makery.address.model;

import ch.makery.address.util.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Created by ae on 03.03.2017.
 * Информация о персоне
 *
 */
public class Person {
    private final StringProperty    firstName;
    private final StringProperty    lastName;
    private final StringProperty    street;
    private final IntegerProperty   postalCode;
    private final StringProperty    city;
    private final ObjectProperty<LocalDate> birthday;
    
    /**
     * Конструктор по-умолчанию
     */
    public Person()
    {
        this(null, null);
    }
    
    public Person(String firstName, String lastName)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        
        // фиктивные данные (для удобства тестирования)
        this.street = new SimpleStringProperty("ул. Какая-то");
        this.postalCode = new SimpleIntegerProperty(603003);
        this.city = new SimpleStringProperty("г. Кудыкино");
        this.birthday = new SimpleObjectProperty<>(LocalDate.now());
        
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String getStreet() {
        return street.get();
    }
    
    public StringProperty streetProperty() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street.set(street);
    }
    
    public int getPostalCode() {
        return postalCode.get();
    }
    
    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }
    
    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }
    
    public String getCity() {
        return city.get();
    }
    
    public StringProperty cityProperty() {
        return city;
    }
    
    public void setCity(String city) {
        this.city.set(city);
    }
    
    // аннотировали, чтобы JAXB мог преобразовать дату в строку
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }
    
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
    
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
}
