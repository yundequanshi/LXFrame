package com.base.frame.widget.materialscrollbar;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

/*
 * Lots of complicated maths taken mostly from Google.
 */
class ScrollingUtilities {

    private MaterialScrollBar materialScrollBar;

    ScrollingUtilities(MaterialScrollBar msb) {
        materialScrollBar = msb;
    }

    ICustomScroller customScroller;

    private ScrollPositionState scrollPosState = new ScrollPositionState();

    private int constant;

    private LinearLayoutManager layoutManager;

    private class ScrollPositionState {
        // The index of the first visible row
        private int rowIndex;
        // The offset of the first visible row
        private int rowTopOffset;
        // The height of a given row (they are currently all the same height)
        private int rowHeight;
        private int indicatorPosition;
    }

    void scrollHandleAndIndicator() {
        int scrollBarY;
        getCurScrollState();
        if(customScroller != null) {
            constant = customScroller.getDepthForItem(materialScrollBar.recyclerView.getChildAdapterPosition(materialScrollBar.recyclerView.getChildAt(0)));
        } else {
            constant = scrollPosState.rowHeight * scrollPosState.rowIndex;
        }
        scrollBarY = (int) getScrollPosition();
        materialScrollBar.handleThumb.setY(scrollBarY);
        materialScrollBar.handleThumb.invalidate();
        if(materialScrollBar.indicator != null) {
            int element;
            if(materialScrollBar.recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                element = scrollPosState.rowIndex * ((GridLayoutManager)materialScrollBar.recyclerView.getLayoutManager()).getSpanCount();
            } else {
                element = scrollPosState.indicatorPosition;
            }
            materialScrollBar.indicator.setText(element);

            materialScrollBar.indicator.setScroll(scrollBarY + materialScrollBar.getTop());
        }
    }

    private float getScrollPosition() {
        getCurScrollState();
        int scrollY = materialScrollBar.getPaddingTop() + constant - scrollPosState.rowTopOffset;
        int scrollHeight = getAvailableScrollHeight();
        int barHeight = getAvailableScrollBarHeight();
        return ((float) scrollY / scrollHeight) * barHeight;
    }

    private int getRowCount() {
        int rowCount = materialScrollBar.recyclerView.getLayoutManager().getItemCount();
        if(materialScrollBar.recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) materialScrollBar.recyclerView.getLayoutManager()).getSpanCount();
            rowCount = (int) Math.ceil((double) rowCount / spanCount);
        }
        return rowCount;
    }

    /**
     * Returns the available scroll bar height:
     * AvailableScrollBarHeight = Total height of the visible view - thumb height
     */
    int getAvailableScrollBarHeight() {
        return materialScrollBar.getHeight() - materialScrollBar.handleThumb.getHeight();
    }

    /**
     * Scrolls to the specified fraction of the RV
     *
     * @param touchFraction the fraction of the RV to scroll through
     * @return the distance traveled by the RV in the transformation applied by this method.
     * + is downward, - upward.
     */
    int scrollToPositionAtProgress(float touchFraction) {
        int priorPosition = materialScrollBar.recyclerView.computeVerticalScrollOffset();
        int exactItemPos;
        if(customScroller == null) {
            int spanCount = 1;
            if(materialScrollBar.recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) materialScrollBar.recyclerView.getLayoutManager()).getSpanCount();
            }

            // Stop the scroller if it is scrolling
            materialScrollBar.recyclerView.stopScroll();

            getCurScrollState();

            //The exact position of our desired item
            exactItemPos = (int) (getAvailableScrollHeight() * touchFraction);

            //Scroll to the desired item. The offset used here is kind of hard to explain.
            //If the position we wish to scroll to is, say, position 10.5, we scroll to position 10,
            //and then offset by 0.5 * rowHeight. This is how we achieve smooth scrolling.
            LinearLayoutManager layoutManager = ((LinearLayoutManager) materialScrollBar.recyclerView.getLayoutManager());
            try {
                layoutManager.scrollToPositionWithOffset(spanCount * exactItemPos / scrollPosState.rowHeight,
                        -(exactItemPos % scrollPosState.rowHeight));
            } catch (ArithmeticException e) { /* Avoids issues where children of RV have not yet been laid out */ }
        } else {
            if(layoutManager == null) {
                layoutManager = ((LinearLayoutManager) materialScrollBar.recyclerView.getLayoutManager());
            }
            exactItemPos = customScroller.getItemIndexForScroll(touchFraction);
            int offset = (int) (customScroller.getDepthForItem(exactItemPos) - touchFraction * getAvailableScrollHeight());
            layoutManager.scrollToPositionWithOffset(exactItemPos, offset);
            return 0;
        }
        return exactItemPos - priorPosition;
    }

    int getAvailableScrollHeight() {
        int visibleHeight = materialScrollBar.getHeight();
        int scrollHeight;
        if(customScroller != null) {
            scrollHeight = materialScrollBar.getPaddingTop() + customScroller.getTotalDepth() + materialScrollBar.getPaddingBottom();
        } else {
            scrollHeight = materialScrollBar.getPaddingTop() + getRowCount() * scrollPosState.rowHeight + materialScrollBar.getPaddingBottom();
        }
        return scrollHeight - visibleHeight;
    }

    void getCurScrollState() {
        scrollPosState.rowIndex = -1;
        scrollPosState.rowTopOffset = -1;
        scrollPosState.rowHeight = -1;

        if  (materialScrollBar.recyclerView.getAdapter() == null) {
            Log.e("MaterialScrollBarLib", "The adapter for your recyclerView has not been set; " +
                    "skipping layout.");
            return;
        }

        int itemCount = materialScrollBar.recyclerView.getAdapter().getItemCount();

        // Return early if there are no items
        if(itemCount == 0) {
            return;
        }

        View child = materialScrollBar.recyclerView.getChildAt(0);

        scrollPosState.rowIndex = materialScrollBar.recyclerView.getChildAdapterPosition(child);
        scrollPosState.indicatorPosition = getIndicatorPosition();

        if(materialScrollBar.recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            scrollPosState.rowIndex = scrollPosState.rowIndex / ((GridLayoutManager) materialScrollBar.recyclerView.getLayoutManager()).getSpanCount();
        }
        if(child == null) {
            scrollPosState.rowTopOffset = 0;
            scrollPosState.rowHeight = 0;
        } else {
            scrollPosState.rowTopOffset = materialScrollBar.recyclerView.getLayoutManager().getDecoratedTop(child);
            scrollPosState.rowHeight = child.getHeight();
        }
    }

    private int getIndicatorPosition(){
        if(materialScrollBar.scrollMode == MaterialScrollBar.ScrollMode.FIRST_VISIBLE) {
            return scrollPosState.rowIndex;
        } else {
            int itemCount = materialScrollBar.recyclerView.getAdapter().getItemCount();
            int itemIndex = ((int) (itemCount * materialScrollBar.currentScrollPercent));
            return itemIndex > 0 ? itemIndex - 1 : itemIndex;
        }
    }
}
