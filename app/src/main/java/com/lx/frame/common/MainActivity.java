package com.lx.frame.common;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.base.frame.basic.BaseActivity;
import com.lx.frame.R;
import com.lx.frame.arch.ArchActivity;
import com.lx.frame.image.ImageActivity;
import com.lx.frame.request.RequestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.lv_main)
    ListView lvMain;

    private String[] data = {"请求", "选择图片并上传", "arch event"};
    private long exitTime = 0;

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
                    case 2:
                        StartToActivity(ArchActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
