package com.lx.frame.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.base.frame.basic.BaseActivity;
import com.lx.frame.R;
import com.lx.frame.image.ImageActivity;
import com.lx.frame.request.RequestActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.lv_main)
    ListView lvMain;

    private String[] data = {"请求", "选择图片并上传"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        color = R.color.transparent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        lvMain.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, data));
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        StartToActivity(RequestActivity.class);
                        break;
                    case 1:
                        StartToActivity(ImageActivity.class);
                        break;
                }
            }
        });
    }
}
