package com.lx.frame.scrollbar;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ScrollBarAdapter extends RecyclerView.Adapter<ScrollBarAdapter.ScrollBarViewHolder> implements INameableAdapter {

    private List<ScrollBarData> datas = new ArrayList<>();

    public ScrollBarAdapter() {
    }

    public void setDatas(List<ScrollBarData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public Character getCharacterForElement(int element) {
        return datas.get(element).index.charAt(0);
    }

    @NonNull
    @Override
    public ScrollBarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScrollBarViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scroll_bar, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollBarViewHolder viewHolder, int position) {
        ScrollBarData data = datas.get(position);
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

    class ScrollBarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ScrollBarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
