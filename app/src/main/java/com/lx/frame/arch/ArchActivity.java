package com.lx.frame.arch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.base.frame.basic.BaseActivity;
import com.base.frame.entity.BaseEventData;
import com.lx.frame.R;
import com.lx.frame.arch.entity.ArchViewModel;
import com.lx.frame.common.Flag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArchActivity extends BaseActivity {

    @BindView(R.id.tv_arch_value)
    TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);
        ButterKnife.bind(this);
        Flag.arch2ToArchViewModel = ViewModelProviders.of(this).get(ArchViewModel.class);
        Flag.arch2ToArchViewModel.getEventData().observe(this, new Observer<BaseEventData>() {
            @Override
            public void onChanged(@Nullable BaseEventData baseEventData) {
                if (baseEventData.key == Flag.EventKey.ARCH_EVENT_KEY) {
                    tvValue.setText("返回值" + String.valueOf(baseEventData.value));
                }
            }
        });
    }


    @OnClick({R.id.btn_next, R.id.btn_fragment})
    void allOnClick(View view) {
        switch (view.getId()) {
            //点击下一页
            case R.id.btn_next:
                StartToActivity(ArchActivity2.class);
                break;
            //fragment
            case R.id.btn_fragment:
                StartToActivity(TabActivity.class);
                break;
        }
    }
}
