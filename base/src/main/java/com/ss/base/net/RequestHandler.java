package com.ss.base.net;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public interface RequestHandler {
    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    Response onAfterRequest(Response response, String result, Interceptor.Chain chain);
}
