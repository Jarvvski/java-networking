package com.jarvvski.networking.local;

import com.jarvvski.networking.handlers.MasterHandler;
import com.jarvvski.networking.registers.ClientRegister;
import com.jarvvski.networking.registers.UpdateRegister;
import com.jarvvski.networking.messages.Data;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements LocalDevice {

    private ServerSocket serverSocket;
    private ClientRegister clientRegister;
    private UpdateRegister updateRegister;
    private MasterHandler masterHandler;
    private boolean connected;

    public Server(int portNumber) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Starting server");
        this.masterHandler  = new MasterHandler();

        this.clientRegister = new ClientRegister(this);
        Thread cRegister = new Thread(clientRegister);
        cRegister.setDaemon(true);

        this.updateRegister = new UpdateRegister(this);
        Thread uRegister = new Thread(updateRegister);
        uRegister.setDaemon(true);

        cRegister.start();
        uRegister.start();
        this.connected = true;
    }

    public boolean isRunning() {
        return this.connected;
    }

    public MasterHandler getMasterHandler() {
        return this.masterHandler;
    }

    public ClientRegister getClientRegister() {
        return clientRegister;
    }

    public UpdateRegister getUpdateRegister() {
        return this.updateRegister;
    }


    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void close() {
        this.connected = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(Data data) {
        this.clientRegister.getClients().forEach(client -> client.send(data));
    }
}
