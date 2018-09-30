package com.lx.frame.request;

import java.io.Serializable;

public class EmojiResponse<T> implements Serializable {

    public String msg;

    public int code;

    public T data;
}
