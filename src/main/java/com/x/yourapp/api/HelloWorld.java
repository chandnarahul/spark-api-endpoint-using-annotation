package com.x.yourapp.api;

import com.x.server.annotation.Get;
import com.x.server.annotation.Post;
import com.x.server.annotation.SparkApi;
import spark.Request;
import spark.Response;

@SparkApi
public class HelloWorld {
    private Request request;
    private Response response;

    public HelloWorld(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Post(path = "/api/HelloWorld", acceptType = "*/*")
    public String post() {
        return "HelloWorld post !";
    }

    @Get(path = "/api/HelloWorld", acceptType = "*/*")
    public String get() {
        if (request.queryParamOrDefault("to", "").isEmpty()) {
            return "HelloWorld get !";
        } else {
            return "HelloWorld with query param. " + request.queryParams("to");
        }
    }

    @Get(path = "/api/HelloWorld/:callerName", acceptType = "*/*")
    public String getWithURLParam() {
        return "HelloWorld with url param. ! " + request.params("callerName");
    }
}
