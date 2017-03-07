/*
 * Copyright (c) 2017. Aleksey Eremin
 * 07.03.17 15:09
 */

package ch.makery.address.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Created by ae on 07.03.2017.
 *
 * Адаптер для JAXB преобразования между типом LocalDate и строковым представлением
 * даты в стандарте ISO 8601. например '2004-03-27'
 *
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part5/
 * раздел "Тестирование"
 */
public class LocalDateAdapter extends XmlAdapter {
    
    @Override
    public Object unmarshal(Object v) throws Exception {
        String s = (String)v;
        return LocalDate.parse(s);
    }
    
    @Override
    public Object marshal(Object v) throws Exception {
        LocalDate ld = (LocalDate)v;
        String s = ld.toString();
        return s;
    }
}
