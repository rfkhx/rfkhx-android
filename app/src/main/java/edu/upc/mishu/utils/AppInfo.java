package edu.upc.mishu.utils;

import androidx.annotation.NonNull;

public class AppInfo {
    private String appName;
    private String packgeName;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackgeName() {
        return packgeName;
    }

    @NonNull
    @Override
    public String toString() {
        return "appName" + appName +" packgeName"+ packgeName;
    }
}
