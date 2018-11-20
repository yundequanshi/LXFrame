package com.lx.frame.arch;

import android.os.Bundle;
import android.view.View;

import com.base.frame.basic.BaseActivity;
import com.base.frame.entity.BaseEventData;
import com.lx.frame.R;
import com.lx.frame.common.Flag;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArchActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_back})
    void allOnClick(View view) {
        switch (view.getId()) {
            //点击返回
            case R.id.btn_back:
                Flag.arch2ToArchViewModel.getEventData()
                        .setValue(new BaseEventData(Flag.EventKey.ARCH_EVENT_KEY, "测试arch"));
                finish();
                break;
        }
    }
}
