package com.jarvvski.networking.handlers;

import com.jarvvski.networking.messages.Data;

public interface Handler<T extends Data> {

    void handle(T data);

    String getClassName();

}
