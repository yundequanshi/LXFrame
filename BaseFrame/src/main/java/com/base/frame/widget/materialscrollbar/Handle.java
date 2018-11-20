package com.base.frame.widget.materialscrollbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

@SuppressLint("ViewConstructor")
public class Handle extends View {

    final int WIDTH = Utils.getDP(8, this);
    RectF handleArc;
    RectF handleHold;
    Paint p = new Paint();
    Integer mode;
    boolean expanded = false;
    Boolean rtl = false;

    public Handle(Context c, int m) {
        super(c);

        mode = m;
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
}

    void setRightToLeft(boolean rtl) {
        this.rtl = rtl;
    }

    @Override
    public void setBackgroundColor(int color) {
//        super.setBackgroundColor(color);

        p.setColor(color);
    }

    public void collapseHandle() {
        expanded = true;
        invalidate();
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
    }

    public void expandHandle() {
        expanded = false;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(mode == 0) {
            makeRect();
        } else {
            if(rtl) {
                handleHold = new RectF(new Rect(getLeft(), getTop(), getRight() - WIDTH / 2 - Utils.getDP(1, this), getBottom()));
            } else {
                handleHold = new RectF(new Rect(getLeft() + WIDTH / 2 + Utils.getDP(1, this), getTop(), getRight(), getBottom()));
            }
        }
    }

    private void makeRect() {
        if(rtl) {
            handleArc = new RectF(new Rect(getRight() - WIDTH,getTop(),getRight(),getBottom()));
            handleHold = new RectF(new Rect(getLeft(), getTop(), getRight() - WIDTH / 2, getBottom()));
        } else {
            handleArc = new RectF(new Rect(getLeft(),getTop(),getLeft() + WIDTH,getBottom()));
            handleHold = new RectF(new Rect(getLeft() + WIDTH / 2, getTop(), getRight(), getBottom()));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mode == 0 && !expanded) {
//            canvas.getClipBounds(boundRect);
//            boundRect.inset(-Utils.getDP(4, context), 0); //make the rect larger
//
//            canvas.clipRect(boundRect, Region.Op.REPLACE);

            canvas.drawRect(handleHold, p);
            canvas.drawArc(handleArc, rtl ? 270F : 90F, 180F, false, p); //335
        } else {
            canvas.drawRect(handleHold, p);
        }
    }
}
