package com.softwareproject.focus.Models;

import java.util.List;

/**
 * Created by Amjad on 03/04/18.
 */

public class profile {

    private int id;
    private String name;
    private String Days;
    private String Times;
    private String repeat;
    private String status;
    private List<app> list_app;

    public profile(int id, String name, String days, String times, String repeat, String status) {
        this.id = id;
        this.name = name;
        Days = days;
        Times = times;
        this.repeat = repeat;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getStatus() {
        return status;
    }

    public List<app> getList_app() {
        return list_app;
    }

    public void setList_app(List<app> list_app) {
        this.list_app = list_app;
    }
}
