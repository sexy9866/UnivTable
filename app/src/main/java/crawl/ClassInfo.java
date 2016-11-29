package crawl;

import java.util.Calendar;

import weekview.WeekViewEvent;

public class ClassInfo {
    public static final int NULLPTR = -1;

    public String title = ""; // title of Lecture

    public String location = ""; // Location of Lecture
    public String rawtime = ""; // time in raw state
    public int weekDay = NULLPTR;
    public int startHour = NULLPTR;
    public int startMin = NULLPTR;
    public int endHour = NULLPTR;
    public int endMin = NULLPTR;

    public ClassInfo(){}

    public ClassInfo(String title, String location, String rawtime) {
        this.title = title;
        this.location = location;
        this.rawtime = rawtime;
    }

    public ClassInfo(String title, String location, String rawtime, int weekDay, int startHour, int startMin, int endHour, int endMin) {
        this.title = title;
        this.location = location;
        this.rawtime = rawtime;
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public ClassInfo clone(){
        return new ClassInfo(title, location, rawtime, weekDay, startHour, startMin, endHour, endMin);
    }

    public WeekViewEvent toWeekViewEvent(int newYear, int newMonth){
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, newYear);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.DAY_OF_WEEK, weekDay + 1);
        startTime.set(Calendar.HOUR_OF_DAY, startHour);
        startTime.set(Calendar.MINUTE, startMin);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
        endTime.set(Calendar.MINUTE, endMin);
        WeekViewEvent res = new WeekViewEvent(0, "[" + title + "]\n" + location, startTime, endTime);
        res.title = this.title;
        res.loc = this.location;
        res.classInfo = this;

        return res;
    }

}
