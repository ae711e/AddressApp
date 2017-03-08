/*
 * Copyright (c) 2017. Aleksey Eremin
 * 08.03.17 18:31
 */

package ch.makery.address.view;

import ch.makery.address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.lang.reflect.Array;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

/**
 * Created by ae on 08.03.2017.
 * Контроллер для представления статистики по лням рождения
 * http://code.makery.ch/library/javafx-8-tutorial/ru/part6/
 * урок 6
 *
 */
public class BirthdayStatisticsController {

    @FXML
    private BarChart    barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        // получаем массив с английскими именами месяцев
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();

        // преобразуем массив в список и добавляем в наш список месяцев
        monthNames.addAll(Arrays.asList(months));

        // назначаем имена месяцев категориями для горизонтальной оси
        xAxis.setCategories(monthNames);
    }

    /**
     * Задает адресатов, о которых будет показана статистика
     * код смотрим в англ. версии
     * http://code.makery.ch/library/javafx-8-tutorial/part6/
     *
     * @param persons   список адресатов
     */
    public void setPersonData(List<Person> persons)
    {
        // считаем адресатов, имеющих дни рождения в указанном месяце
        int[]  monthCounter = new int[12];
        for(Person p: persons) {
            int month = p.getBirthday().getMonthValue()-1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // создаем объект XYChart.Data для каждого месяца
        // добавляем его в серии
        for(int i=0; i < monthCounter.length; i++) {
            String  ss = (String) monthNames.get(i);
            series.getData().add(new XYChart.Data<>(ss, monthCounter[i]));
        }

        barChart.getData().add(series);

    }

}
