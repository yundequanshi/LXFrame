package com.lx.frame.scrollbar.entity;

import android.support.annotation.NonNull;

import com.lx.frame.sidebar.entity.SideBarData;

import java.io.Serializable;

public class ScrollBarData implements Serializable , Comparable<ScrollBarData>{

    public String index;

    public String name;

    public ScrollBarData() {
    }

    public ScrollBarData(String index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull ScrollBarData another) {
        return this.index.compareTo(another.index);
    }
}
