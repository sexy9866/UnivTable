package weekview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import crawl.ClassInfo;

import static weekview.WeekViewUtil.*;

public class WeekViewEvent {

    /********************************************************
     * This codes are referenced by Github(https://github.com/alamkanak/Android-Week-View)
     ********************************************************/

    public ClassInfo classInfo;
    private long mId;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mName;
    private String mLocation;
    private int mColor;
    private boolean mAllDay;
    public String title, loc;
    public WeekViewEvent(){
    }

    public String getRawTime(){
        String sH = classInfo.startHour < 10 ? "0" + classInfo.startHour : "" + classInfo.startHour;
        String sM = classInfo.startMin < 10 ? "0" + classInfo.startMin : "" + classInfo.startMin;
        String eH = classInfo.endHour < 10 ? "0" + classInfo.endHour : "" + classInfo.endHour;
        String eM = classInfo.endMin < 10 ? "0" + classInfo.endMin : "" + classInfo.endMin;
        String res = sH + ":" + sM + " - " + eH + ":" + eM;
        return res;
    }

    public WeekViewEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        this.mId = id;

        this.mStartTime = Calendar.getInstance();
        this.mStartTime.set(Calendar.YEAR, startYear);
        this.mStartTime.set(Calendar.MONTH, startMonth-1);
        this.mStartTime.set(Calendar.DAY_OF_MONTH, startDay);
        this.mStartTime.set(Calendar.HOUR_OF_DAY, startHour);
        this.mStartTime.set(Calendar.MINUTE, startMinute);

        this.mEndTime = Calendar.getInstance();
        this.mEndTime.set(Calendar.YEAR, endYear);
        this.mEndTime.set(Calendar.MONTH, endMonth-1);
        this.mEndTime.set(Calendar.DAY_OF_MONTH, endDay);
        this.mEndTime.set(Calendar.HOUR_OF_DAY, endHour);
        this.mEndTime.set(Calendar.MINUTE, endMinute);

        this.mName = name;
    }

    public WeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime, boolean allDay) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAllDay = allDay;
    }

    public WeekViewEvent clone(){
        WeekViewEvent obj = new WeekViewEvent();
        obj.setId(this.mId);
        obj.setName(this.mName);
        obj.setLocation(this.mLocation);
        obj.setStartTime((Calendar) this.mStartTime.clone());
        obj.setEndTime((Calendar) this.mEndTime.clone());
        return obj;
    }

    public WeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime) {
        this(id, name, location, startTime, endTime, false);
    }

    public WeekViewEvent(long id, String name, Calendar startTime, Calendar endTime) {
        this(id, name, null, startTime, endTime);
    }


    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public boolean getAllday(){return mAllDay;}

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setAllDay(boolean allDay) {
        this.mAllDay = allDay;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekViewEvent that = (WeekViewEvent) o;

        return mId == that.mId;

    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    public List<WeekViewEvent> splitWeekViewEvents(){
        //This function splits the WeekViewEvent in WeekViewEvents by day
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        // The first millisecond of the next day is still the same day. (no need to split events for this).
        Calendar endTime = (Calendar) this.getEndTime().clone();
        endTime.add(Calendar.MILLISECOND, -1);
        if (!isSameDay(this.getStartTime(), endTime)) {
            endTime = (Calendar) this.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent event1 = new WeekViewEvent(this.getId(), this.getName(), this.getLocation(), this.getStartTime(), endTime, this.isAllDay());
            event1.setColor(this.getColor());
            events.add(event1);

            // Add other days.
            Calendar otherDay = (Calendar) this.getStartTime().clone();
            otherDay.add(Calendar.DATE, 1);
            while (!isSameDay(otherDay, this.getEndTime())) {
                Calendar overDay = (Calendar) otherDay.clone();
                overDay.set(Calendar.HOUR_OF_DAY, 0);
                overDay.set(Calendar.MINUTE, 0);
                Calendar endOfOverDay = (Calendar) overDay.clone();
                endOfOverDay.set(Calendar.HOUR_OF_DAY, 23);
                endOfOverDay.set(Calendar.MINUTE, 59);
                WeekViewEvent eventMore = new WeekViewEvent(this.getId(), this.getName(), null, overDay, endOfOverDay, this.isAllDay());
                eventMore.setColor(this.getColor());
                events.add(eventMore);

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) this.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent event2 = new WeekViewEvent(this.getId(), this.getName(), this.getLocation(), startTime, this.getEndTime(), this.isAllDay());
            event2.setColor(this.getColor());
            events.add(event2);
        }
        else{
            events.add(this);
        }

        return events;
    }
}
