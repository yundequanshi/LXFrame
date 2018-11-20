package com.lx.frame.arch;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.base.frame.basic.BaseActivity;
import com.lx.frame.R;
import com.lx.frame.arch.fragment.ArchFragment;

import butterknife.ButterKnife;

public class TabActivity extends BaseActivity {

    public FragmentTabHost mTabHost;
    private Class mViewFrags[] = {
            ArchFragment.class,
            ArchFragment.class,
    };
    private int mImageViewArray[] = {
            R.drawable.home_tab_1,
            R.drawable.home_tab_1,
    };
    private String mDataTv[] = {"tab1", "tab2"};
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //R.id.realtabcontent决定了标签是在上面还是下面显示,FrameLayout在FragmentTabHost上面，标签在上方显示，否则在下方显示。
        mTabHost.setup(this, getSupportFragmentManager(), R.id.home_fl_content);
        //得到fragment的个数
        int count = mViewFrags.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mDataTv[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mViewFrags[i], null);
        }
        //去掉了底部导航栏的间隔竖线
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setCurrentTab(index);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_home_tab, null);
        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(mDataTv[index]);
        return view;
    }
}
