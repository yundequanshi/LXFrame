package com.lx.frame.scrollbar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.base.frame.basic.BaseActivity;
import com.base.frame.widget.materialscrollbar.DragScrollBar;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.lx.frame.R;
import com.lx.frame.scrollbar.entity.ScrollBarData;
import com.lx.frame.sidebar.entity.SideBarData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollBarActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.dragScrollBar)
    DragScrollBar dragScrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_bar);
        ButterKnife.bind(this);
        ScrollBarAdapter scrollBarAdapter = new ScrollBarAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scrollBarAdapter);
        dragScrollBar.setIndicator(new AlphabetIndicator(this), true);
        List<ScrollBarData> barData = getData();
        scrollBarAdapter.setDatas(barData);
    }

    private List<ScrollBarData> getData() {
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));
        List<ScrollBarData> data = new ArrayList<>();
        String[] stringData = {"济南", "北京", "上海", "淄博", "高密", "青岛", "重庆", "大连", "临沂", "天津", "章丘", "齐河",
                "东北", "西藏", "苏州", "杭州", "漳州", "湖州", "甘肃"};
        for (String name : stringData) {
            String index = String.valueOf(Pinyin.toPinyin(name.charAt(0)).charAt(0));
            ScrollBarData sideBarData = new ScrollBarData(index, "");
            if (!data.contains(sideBarData)) {
                data.add(sideBarData);
            }
            data.add(new ScrollBarData(index, name));
        }
        Collections.sort(data);
        return data;
    }
}
