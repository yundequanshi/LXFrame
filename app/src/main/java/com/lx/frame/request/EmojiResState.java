package com.lx.frame.request;

import java.io.Serializable;

public class EmojiResState<T> implements Serializable {

    public String msg;

    public int state;

    public EmojiResponse<T> response = new EmojiResponse();
}
