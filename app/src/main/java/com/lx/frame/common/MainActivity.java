package com.lx.frame.common;

import android.os.Bundle;
import android.widget.ListView;

import com.base.frame.basic.BaseActivity;
import com.lx.frame.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.lv_main)
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
