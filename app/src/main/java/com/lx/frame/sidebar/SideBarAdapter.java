package com.lx.frame.sidebar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.frame.widget.materialscrollbar.INameableAdapter;
import com.lx.frame.R;
import com.lx.frame.scrollbar.entity.ScrollBarData;
import com.lx.frame.sidebar.entity.SideBarData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SideBarAdapter extends RecyclerView.Adapter<SideBarAdapter.SideBarViewHolder>{

    private List<SideBarData> datas = new ArrayList<>();

    public SideBarAdapter() {
    }

    public void setDatas(List<SideBarData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SideBarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SideBarViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_side_bar, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SideBarViewHolder viewHolder, int position) {
        SideBarData data = datas.get(position);
        if (position == 0 || !datas.get(position - 1).index.equals(data.index)) {
            viewHolder.tvIndex.setTextColor(Color.parseColor("#0CA8FF"));
        } else {
            viewHolder.tvIndex.setTextColor(Color.WHITE);
        }
        viewHolder.tvIndex.setText(data.index);
        viewHolder.tvName.setText(data.name);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SideBarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_name)
        TextView tvName;

        public SideBarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
