package com.lx.frame.sidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.base.frame.widget.SideBar;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.lx.frame.R;
import com.lx.frame.sidebar.entity.SideBarData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideBarActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.side_bar)
    SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        ButterKnife.bind(this);
        SideBarAdapter sideBarAdapter = new SideBarAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sideBarAdapter);
        final List<SideBarData> barData = getData();
        sideBarAdapter.setDatas(barData);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                for (int i = 0; i < barData.size(); i++) {
                    if (barData.get(i).index.equalsIgnoreCase(s)) {
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private List<SideBarData> getData() {
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));
        List<SideBarData> data = new ArrayList<>();
        String[] stringData = {"济南", "北京", "上海", "淄博", "高密", "青岛", "重庆", "大连", "临沂", "天津", "章丘", "齐河",
                "东北", "西藏", "苏州", "杭州", "漳州", "湖州", "甘肃"};
        for (String name : stringData) {
            data.add(new SideBarData(String.valueOf(Pinyin.toPinyin(name.charAt(0)).charAt(0)), name));
        }
        Collections.sort(data);
        return data;
    }
}
