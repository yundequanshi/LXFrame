package com.lx.frame.arch.entity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.base.frame.entity.BaseEventData;

public class ArchViewModel extends ViewModel {

    private MutableLiveData<BaseEventData> eventData;

    public MutableLiveData<BaseEventData> getEventData() {
        if (eventData == null) {
            eventData = new MutableLiveData<BaseEventData>();
        }
        return eventData;
    }
}
