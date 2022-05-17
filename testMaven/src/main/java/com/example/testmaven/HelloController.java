package com.example.testmaven;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.daytoursnyttsdk.DayTour;
import sample.daytoursnyttsdk.DayTours;
import sample.daytoursnyttsdk.SearchModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        DayTours dt = new DayTours("Date");
        ArrayList<String> activities = new ArrayList<>();
        activities.add("Hjolaferd");
        activities.add("Gonguferd");
        activities.add("Sundferd");
        activities.add("Paintball");
        SearchModel sm = new SearchModel("ANY", 0, 10000, 0, 555, activities,
                0, 500000,5, LocalDate.of(2020,1, 1),
                LocalDate.of(2025, 3, 3), false);
        dt.getDayTours(sm);
        for(DayTour x: dt.getDayTourList()){
            System.out.println(x.getTourName());
        }
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}