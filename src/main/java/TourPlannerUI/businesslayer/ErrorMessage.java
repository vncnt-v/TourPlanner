package TourPlannerUI.businesslayer;

import lombok.Getter;

public class ErrorMessage {

    @Getter
    String msg;

    public ErrorMessage(String msg) {
        this.msg = msg;
    }
}
