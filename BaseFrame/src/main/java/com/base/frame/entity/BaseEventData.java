package com.base.frame.entity;

import java.io.Serializable;
import java.util.List;

public class BaseEventData<T, E> implements Serializable {

    public T value;
    public List<E> valueList;
    public Enum key;


    public BaseEventData(Enum key, T value) {
        this.key = key;
        this.value = value;
    }

    public BaseEventData(Enum key, List<E> list) {
        this.key = key;
        this.valueList = list;
    }

    public BaseEventData(Enum key) {
        this.key = key;
    }
}
