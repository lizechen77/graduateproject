package com.xtu.graduate.project.domains;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
public class ActivityInfo {
    Integer activityID;

    String SiteID;

    Integer applicationID;

    String deparmentID;

    public Integer getActivityID() {
        return activityID;
    }

    public void setActivityID(Integer activityID) {
        this.activityID = activityID;
    }

    public String getSiteID() {
        return SiteID;
    }

    public void setSiteID(String siteID) {
        SiteID = siteID;
    }

    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public String getDeparmentID() {
        return deparmentID;
    }

    public void setDeparmentID(String deparmentID) {
        this.deparmentID = deparmentID;
    }
}
