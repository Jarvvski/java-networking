package com.jarvvski.networking.remote;

import com.jarvvski.networking.messages.Data;
import com.jarvvski.networking.pipes.Input;
import com.jarvvski.networking.registers.UpdateRegister;

import java.net.Socket;

public interface RemoteDevice {

    void send(Data data);

    boolean hasUpdate();

    void close();

    Data getUpdate();

    UpdateRegister getUpdateRegister();

    Socket getSocket();

    Input getInput();
}
