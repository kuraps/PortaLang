package com.tcodng.portalang;

import java.util.ArrayList;

public class ModelPortalNews {
    private String mTitle;
    private String mDesc;

    public ModelPortalNews(String title, String desc) {
        mTitle = title;
        mDesc = desc;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getDesc() {
        return mDesc;
    }
}