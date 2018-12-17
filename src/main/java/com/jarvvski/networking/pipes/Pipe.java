package com.jarvvski.networking.pipes;

import com.jarvvski.networking.messages.Data;

import java.util.Queue;

public interface Pipe{

    void attemptRecovery();

    Queue<Data> getDataQueue();
}
