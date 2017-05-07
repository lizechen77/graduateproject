package com.xtu.graduate.project.domains;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/29 0029.
 */
public class SiteApplication {
    private Integer applicationID;
    private Date begainTime;
    private Date endTime;
    private String departmentID;
    private String siteManagerID;
    private String status;
    private String siteID;
    private String details;
    private String activityName;

    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public Date getBegainTime() {
        return begainTime;
    }

    public void setBegainTime(Date begainTime) {
        this.begainTime = begainTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getSiteManagerID() {
        return siteManagerID;
    }

    public void setSiteManagerID(String siteManagerID) {
        this.siteManagerID = siteManagerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
