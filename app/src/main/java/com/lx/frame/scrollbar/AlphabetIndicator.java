package com.lx.frame.scrollbar;

import android.annotation.SuppressLint;
import android.content.Context;

import com.base.frame.widget.materialscrollbar.INameableAdapter;
import com.base.frame.widget.materialscrollbar.Indicator;

/**
 * Indicator which should be used when only one character will be displayed at a time.
 */
@SuppressLint("ViewConstructor")
public class AlphabetIndicator extends Indicator<INameableAdapter, AlphabetIndicator> {

    public AlphabetIndicator(Context c) {
        super(c, INameableAdapter.class);
    }

    @Override
    protected String getTextElement(Integer currentSection, INameableAdapter adapter) {
        Character provided = adapter.getCharacterForElement(currentSection);
        return String.valueOf(Character.toUpperCase(provided));
    }

    @Override
    protected int getIndicatorHeight() {
        return 75;
    }

    @Override
    protected int getIndicatorWidth() {
        return 75;
    }

    @Override
    protected int getTextSize() {
        return 40;
    }

}