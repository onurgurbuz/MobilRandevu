package com.mobilrandevu;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilrandevu.Model.AkademisyenPOJO;

import java.util.ArrayList;
import java.util.List;

public class Statikler {
    public static int year = 0;
    public static int month = 0;
    public static int day = 0;
    public static int hour = 0;
    public static int minute = 0;
    public static int tip = 0;
    public static int ogrenciID = -1;
    public static int akademisyenID = -1;
    public static AkademisyenPOJO secilenAkademisyen;

    public static List<WeekViewEvent> weekViewEventModels = new ArrayList<WeekViewEvent>();

}
