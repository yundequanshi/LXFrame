package com.base.frame.http;

import com.google.gson.Gson;

public interface HttpResponseInterface {

    String getResponseData(Gson gson, String response);
}
