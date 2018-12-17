package com.jarvvski.networking.local;

import com.jarvvski.networking.handlers.MasterHandler;
import com.jarvvski.networking.registers.UpdateRegister;
import com.jarvvski.networking.messages.Data;

public interface LocalDevice {

    void close();

    MasterHandler getMasterHandler();

    UpdateRegister getUpdateRegister();

//    ServerSocket getServerSocket();

    boolean isRunning();

    void sendData(Data data);

}
