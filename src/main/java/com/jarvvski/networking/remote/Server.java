package com.jarvvski.networking.remote;

import com.jarvvski.networking.pipes.Input;
import com.jarvvski.networking.pipes.Output;
import com.jarvvski.networking.messages.Data;
import com.jarvvski.networking.registers.UpdateRegister;

import java.io.IOException;
import java.net.Socket;

public class Server implements RemoteDevice {

    private Socket socket;
    private Output output;
    private Input input;
    private UpdateRegister updateRegister;

    public Server(Socket socket, UpdateRegister updateRegister) {
        this.socket = socket;
        this.updateRegister = updateRegister;

        this.output = new Output(this.socket);
        Thread oThread = new Thread(output);
        oThread.setDaemon(true);

        this.input  = new Input(this.socket, this);
        Thread iThread = new Thread(input);
        iThread.setDaemon(true);

        oThread.start();
        iThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(Data data) {
        this.output.queueData(data);
    }

    public boolean hasUpdate() {
        return (this.input.getDataQueue().size() > 0);
    }

    public Input getInput() {
        return this.input;
    }

    public Data getUpdate() {
        return this.input.getDataQueue().poll();
    }

    public UpdateRegister getUpdateRegister() {
        return this.updateRegister;
    }

    public void close() {
        try {
            System.out.print("Closing client connection");
            // TODO: Should probably send some kind of connection object
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDead() {
        // TODO: implement check with some kind of heartbeat object;
        return false;
    }
}
