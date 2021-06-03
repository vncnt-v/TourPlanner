package TourPlannerUI.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class TourLog {

    @Getter @Setter public int id;
    @Getter @Setter public LocalDate date;
    @Getter @Setter public String report;
    @Getter @Setter public float distance;
    @Getter @Setter public String startTime;
    @Getter @Setter public String totalTime;
    @Getter @Setter public int rating;
    @Getter @Setter public int exhausting;
    @Getter @Setter public float averageSpeed;
    @Getter @Setter public float calories;
    @Getter @Setter public String breaks;
    @Getter @Setter public String weather;
    @Getter @Setter public TourItem LogTourItem;

    public TourLog(LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem tourItem){
        this.date = date;
        this.report = report;
        this.distance = distance;
        this.totalTime = totalTime;
        this.startTime = startTime;
        this.rating = rating;
        this.exhausting = exhausting;
        this.averageSpeed = averageSpeed;
        this.calories = calories;
        this.breaks = breaks;
        this.weather = weather;
        this.LogTourItem = tourItem;
    }

    public TourLog(Integer id, LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem tourItem){
        this.id = id;
        this.date = date;
        this.report = report;
        this.distance = distance;
        this.totalTime = totalTime;
        this.startTime = startTime;
        this.rating = rating;
        this.exhausting = exhausting;
        this.averageSpeed = averageSpeed;
        this.calories = calories;
        this.breaks = breaks;
        this.weather = weather;
        this.LogTourItem = tourItem;
    }
}
