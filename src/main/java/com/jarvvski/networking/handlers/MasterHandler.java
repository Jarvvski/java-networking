package com.jarvvski.networking.handlers;

import com.jarvvski.networking.messages.Data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MasterHandler {

    private final Map<Class, Handler> handlerRegister = new HashMap<>();

    public <T extends Handler> void registerNewHandler(T handler) {
        this.handlerRegister.put(this.getHandlerTypeArgument(handler), handler);
    }

    public <T extends Handler> void registerMultipleHandlers(Collection<? extends Handler> handlerCollection) {
        handlerCollection.forEach(this::registerNewHandler);
    }

    public <T extends Handler> Class<? extends Data> getHandlerTypeArgument(T handler) {
        ParameterizedType parameterizedType = (ParameterizedType) handler.getClass().getGenericInterfaces()[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (Class<? extends Data>) typeArguments[0];
    }

    public Map<Class, Handler> getHandlerRegister() {
        return this.handlerRegister;
    }

    public <T extends Data> void handle(T data) {

        if (data == null) {
            System.out.println("No data?");
            return;
        }

        if (this.handlerRegister.containsKey(data.getClass())) {
            this.handlerRegister.get(data.getClass()).handle(data);
            ;
            return;
        }
        System.out.println("No matching handler found. Data dumped");
    }

}
