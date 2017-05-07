package com.xtu.graduate.project.domains;

/**
 * Created by Administrator on 2017/4/29 0029.
 */
public class SiteInfo {
    private String siteID;
    private String siteName;
    private String locale;
    private String price;
    private String siteManagerID;

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSiteManagerID() {
        return siteManagerID;
    }

    public void setSiteManagerID(String siteManagerID) {
        this.siteManagerID = siteManagerID;
    }

}
