package com.netease.nim.uikit.common.bean;

import java.io.Serializable;

/**
 * Created by 78560 on 2017/8/24.
 */
public class Draft implements Serializable {
    public String title;
    public String context;

    public Draft() {
    }

    public Draft(String title, String context) {
        this.title = title;
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Draft{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}