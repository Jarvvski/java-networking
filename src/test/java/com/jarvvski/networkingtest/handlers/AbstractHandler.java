package com.jarvvski.networkingtest.handlers;

import com.jarvvski.networking.handlers.Handler;

public class AbstractHandler implements Handler<AbstractData> {

    public void handle(AbstractData data) {
        // nothing stub
    };

    public String getClassName() {
        return "AbstractData";
    }

}
