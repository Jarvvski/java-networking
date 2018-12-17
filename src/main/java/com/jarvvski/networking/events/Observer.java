package com.jarvvski.networking.events;

import com.jarvvski.networking.local.Server;

public abstract class Observer {
    protected Server server;
    public abstract void update();
}
