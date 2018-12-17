package com.jarvvski.networkingtest.handlers;

import com.jarvvski.networking.handlers.Handler;

public class AnotherAbstractHandler implements Handler<AnotherAbstractData> {

    public void handle(AnotherAbstractData data) {
        // nothing stub
    };

    public String getClassName() {
        return "AnotherAbstractData";
    }

}
