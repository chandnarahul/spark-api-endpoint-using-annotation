package com.x.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ApiRegister {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRegister.class);
    private Request request;
    private Response response;

    public ApiRegister(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public String process(Method method) {
        try {
            Class<?> runtimeClass = Class.forName(method.getDeclaringClass().getName());
            Constructor<?> constructor = runtimeClass.getConstructor(Request.class, Response.class);
            Object runtimeObject = constructor.newInstance(request, response);
            return (String) method.invoke(runtimeObject);
        } catch (Exception e) {
            LOGGER.error("registerEndpoints", e);
            return null;
        }
    }
}
