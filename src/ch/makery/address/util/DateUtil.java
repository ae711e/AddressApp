/*
 * Copyright (c) 2017. Aleksey Eremin
 * 04.03.17 21:08
 */

package ch.makery.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by ae on 04.03.2017.
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
 * раздел Преобразование дня рождения в строку
 *
 * Вспомогательные функции для работы с датами
 * @author Marco Jakob
 */
public class DateUtil {
    /** шаблон даты, используемый для преобразования (можно поменять на свой) */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /**  форматировщик для даты */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Возвращает дату в виде хорошо отформатированной строки
     * Используя определенный выше {@link DateUtil#DATE_PATTERN}
     * @param date  дата, которую нужно вернуть в виде строки
     * @return отформатированная строка даты
     */
    public static String format(LocalDate date)
    {
        if(date == null) {
            return null;
        }
        String s = DATE_FORMATTER.format(date);
        return s;
    }

    /**
     * Преобразует строку, которая отформатирована по правилам
     * шаблона {@link DateUtil#DATE_PATTERN} в объект {@link LocalDate}
     *
     * Возвращает null, если строка не может быть преобразована.
     *
     * @param dateString    дата в виде строки ДД.ММ.ГГГГ
     * @return  объект даты или null, если строка не может быть преобразована в дату
     */
    public static LocalDate parse(String dateString)
    {
        try {
            LocalDate dat = DATE_FORMATTER.parse(dateString, LocalDate::from);
            return dat;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Проверяет, является ли строка корректной датой
     *
     * @param dateString    строка, с датой
     * @return  true - правильная дата, false - неправильная дата
     */
    public static boolean validDate(String dateString)
    {
        // пытается преобразовать строку в дату
        return parse(dateString) != null;
    }

} // end of class
