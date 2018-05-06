package com.softwareproject.focus.Models;

/**
 * Created by Amjad on 02/04/18.
 */

public class app {
    private String name;
    private String icon_path;
    private String aSwitch ="";
    private int profile_id;

    public app(String name, String aSwitch, int profile_id) {
        this.name = name;
        this.aSwitch = aSwitch;
        this.profile_id = profile_id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon_path;
    }

    public String getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(String aSwitch) {this.aSwitch = aSwitch;}

    public int getProfile_id() {
        return profile_id;
    }
}
