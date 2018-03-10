package com.x.server;

import com.x.server.annotation.Get;
import com.x.server.annotation.Post;
import com.x.server.annotation.SparkApi;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class ApiEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiEndpoint.class);
    private Service service;

    public ApiEndpoint(Service service) {
        this.service = service;
    }

    public void register() {
        Reflections reflections = new Reflections("com.x.yourapp");
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(SparkApi.class);
        services.forEach(service -> {
            try {
                LOGGER.info("Registering Api [" + service.getName() + "]");
                Arrays.stream(service.getMethods()).forEach(method -> {
                    if (method.isAnnotationPresent(Post.class)) {
                        processPost(method);
                    } else if (method.isAnnotationPresent(Get.class)) {
                        processGet(method);
                    }
                });
            } catch (Exception e) {
                LOGGER.error("registerEndpoints ", e);
            }
        });
    }

    private void processGet(Method method) {
        Get endpoint = method.getAnnotation(Get.class);
        this.service.get(endpoint.path(), endpoint.acceptType(), (Request request, Response response) ->
                new ApiRegister(request, response).process(method)
        );
        LOGGER.info("Registered Api Endpoint [" + method.getName() + "]");
    }

    private void processPost(Method method) {
        Post endpoint = method.getAnnotation(Post.class);
        this.service.post(endpoint.path(), endpoint.acceptType(), (Request request, Response response) ->
                new ApiRegister(request, response).process(method)
        );
        LOGGER.info("Registered Api Endpoint [" + method.getName() + "]");
    }
}
