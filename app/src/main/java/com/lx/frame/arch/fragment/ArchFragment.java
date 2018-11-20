package com.lx.frame.arch.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.frame.entity.BaseEventData;
import com.lx.frame.R;
import com.lx.frame.arch.ArchActivity2;
import com.lx.frame.arch.entity.ArchViewModel;
import com.lx.frame.common.Flag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArchFragment extends Fragment {

    @BindView(R.id.tv_arch_get_value)
    TextView tvGetValue;

    private View view;
    private ArchViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ArchViewModel.class);
        viewModel.getEventData().observe(this, new Observer<BaseEventData>() {
            @Override
            public void onChanged(@Nullable BaseEventData baseEventData) {
                if (baseEventData.key == Flag.EventKey.ARCH_EVENT_KEY) {
                    tvGetValue.setText(String.valueOf(baseEventData.value));
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            initView(inflater);
        } else {
            if (container != null) {
                container.removeView(view);
            }
        }
        return view;
    }

    private void initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_arch, null);
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.btn_get_value})
    void allOnClick(View view) {
        switch (view.getId()) {
            //点击设置值
            case R.id.btn_get_value:
                viewModel.getEventData()
                        .setValue(new BaseEventData(Flag.EventKey.ARCH_EVENT_KEY, "测试arch"));
                break;
        }
    }
}