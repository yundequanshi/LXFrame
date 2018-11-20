package com.lx.frame.sidebar.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class SideBarData implements Serializable, Comparable<SideBarData> {

    public String index;

    public String name;

    public SideBarData() {
    }

    public SideBarData(String index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull SideBarData another) {
        return this.index.compareTo(another.index);
    }
}
