package com.jarvvski.networking.local;

import com.jarvvski.networking.handlers.MasterHandler;
import com.jarvvski.networking.registers.UpdateRegister;
import com.jarvvski.networking.messages.Data;
import com.jarvvski.networking.remote.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements LocalDevice {

    private Server server;
    private MasterHandler masterHandler;
    private UpdateRegister updateRegister;
    private boolean connected;

    public Client(String ipaddress, int portNumber) throws IOException {

        Socket socket = new Socket(ipaddress, portNumber);
        this.updateRegister = new UpdateRegister(this);

        this.server = new Server(socket, this.updateRegister);

        this.masterHandler = new MasterHandler();

        Thread uRegister = new Thread(updateRegister);
        uRegister.setDaemon(true);
        uRegister.start();

        this.connected = true;
    }

    public boolean isRunning() {
        return this.connected;
    }

    public MasterHandler getMasterHandler() {
        return this.masterHandler;
    }

    public UpdateRegister getUpdateRegister() {
        return this.updateRegister;
    }

    public ServerSocket getServerSocket() {
        return null;
    }

    public void close() {
        this.connected = false;
        this.server.close();
    }

    public void sendData(Data data) {
        this.server.send(data);
    }

}
