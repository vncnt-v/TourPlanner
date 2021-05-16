package TourPlannerUI.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

public class TourLog {

    @Getter @Setter public Integer Id;
    @Getter @Setter public LocalDateTime DateTime;
    @Getter @Setter public String Report;
    @Getter @Setter public float Distance;
    @Getter @Setter public Duration TotalTime;
    @Getter @Setter public int Rating;
    @Getter @Setter public int Exhausting;
    @Getter @Setter public float AverageSpeed;
    @Getter @Setter public float Calories;
    @Getter @Setter public int Breaks;
    @Getter @Setter public String Weather;
    @Getter @Setter public TourItem LogTourItem;

    public TourLog(Integer id, LocalDateTime dateTime, String report, float distance, Duration totalTime, int rating, int exhausting, float averageSpeed, float calories, int breaks, String weather, TourItem tourItem){
        Id = id;
        DateTime = dateTime;
        Report = report;
        Distance = distance;
        TotalTime = totalTime;
        Rating = rating;
        Exhausting = exhausting;
        AverageSpeed = averageSpeed;
        Calories = calories;
        Breaks = breaks;
        Weather = weather;
        LogTourItem = tourItem;
    }
}
