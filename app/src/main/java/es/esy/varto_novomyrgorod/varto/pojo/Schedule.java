package es.esy.varto_novomyrgorod.varto.pojo;

import java.util.Calendar;

public class Schedule {
    private String shop;
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    public Schedule() {
        this.shop = "not";
        this.sunday = "неизвестно";
        this.monday = "неизвестно";
        this.tuesday = "неизвестно";
        this.wednesday = "неизвестно";
        this.thursday = "неизвестно";
        this.friday = "неизвестно";
        this.saturday = "неизвестно";
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getDayOfWeek(int intDay) {
        String result = null;
        switch (intDay) {
            case Calendar.MONDAY: {
                result = getMonday();
            }
            break;
            case Calendar.TUESDAY: {
                result = getTuesday();
            }
            break;
            case Calendar.WEDNESDAY: {
                result = getWednesday();
            }
            break;
            case Calendar.THURSDAY: {
                result = getThursday();
            }
            break;
            case Calendar.FRIDAY: {
                result = getFriday();
            }
            break;
            case Calendar.SATURDAY: {
                result =getSaturday();
            }
            break;
            case Calendar.SUNDAY: {
                result = getSunday();
            }
            break;
        }
        return result;
    }

    public String getScheduleToday() {
        return getDayOfWeek(getCurrentDayOfWeek());
    }

    private int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}
