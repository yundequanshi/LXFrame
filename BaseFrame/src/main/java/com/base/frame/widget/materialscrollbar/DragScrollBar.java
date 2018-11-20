package com.base.frame.widget.materialscrollbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DragScrollBar extends MaterialScrollBar<DragScrollBar> {

    float handleOffset = 0;
    float indicatorOffset = 0;

    public DragScrollBar(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public DragScrollBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DragScrollBar(Context context, RecyclerView recyclerView, boolean lightOnTouch) {
        super(context, recyclerView, lightOnTouch);
    }

    boolean held = false;

    @Override
    void setTouchIntercept() {
        final Handle handle = super.handleThumb;
        View.OnTouchListener otl = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!hiddenByUser) {
                    boolean valid = validTouch(event);

                    // check valid touch region only on action down => otherwise the check will fail if users scrolls very fast
                    if (event.getAction() == MotionEvent.ACTION_DOWN && !valid) {
                        return false;
                    }

                    if (event.getAction() == MotionEvent.ACTION_DOWN && valid) {
                        held = true;

                        indicatorOffset = event.getY() - handle.getY() - handle.getLayoutParams().height / 2;
                        float offset2 = event.getY() - handle.getY();
                        float balance = handle.getY() / scrollUtils.getAvailableScrollBarHeight();
                        handleOffset = (offset2 * balance) + (indicatorOffset * (1 - balance));
                    }
                    //On Down
                    if ((event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) && held) {
                        onDown(event);
                        fadeIn();
                        //On Up
                    } else {
                        onUp();

                        held = false;

                        fadeOut();
                    }
                    performClick();
                    return true;
                }
                return false;
            }
        };
        setOnTouchListener(otl);
    }

    @Override
    int getMode() {
        return 0;
    }

    @Override
    float getHideRatio() {
        return .4F;
    }

    @Override
    void onScroll() {
    }

    @Override
    boolean getHide() {
        return true;
    }

    @Override
    void implementFlavourPreferences() {
    }

    @Override
    float getHandleOffset() {
        return draggableFromAnywhere ? 0 : handleOffset;
    }

    @Override
    float getIndicatorOffset() {
        return draggableFromAnywhere ? 0 : indicatorOffset;
    }
}
