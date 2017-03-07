/*
 * Copyright (c) 2017. Aleksey Eremin
 * 07.03.17 13:10
 */

package ch.makery.address.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by ae on 07.03.2017.
 *
 * Класс-обертка для хранения данных о персонах
 * Вспомогательный класс для обертывания списка адресатов.
 * Используется для сохранения списка адресатов в XML
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part5/
 * раздел "Подготовка класса-модели для JAXB"
 * Кстати в русской версию офигенная ошибка - нет квалификатора типа <Person>
 *     надо смотреть ангю версию!
 * http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 * @author Marco Jakob
 */

@XmlRootElement(name = "persons")
public class PersonListWrapper {
    private List<Person> persons;
    
    @XmlElement(name = "person")
    public List<Person> getPersons()
    {
        return persons;
    }
    
    public void setPersons(List<Person> persons)
    {
        this.persons = persons;
    }

} // end of class
