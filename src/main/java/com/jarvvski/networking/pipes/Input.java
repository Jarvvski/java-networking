package com.jarvvski.networking.pipes;

import com.jarvvski.networking.local.LocalDevice;
import com.jarvvski.networking.messages.Data;
import com.jarvvski.networking.registers.UpdateRegister;
import com.jarvvski.networking.remote.RemoteDevice;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Input implements Runnable {

    private Socket socket;
    private ObjectInputStream inputStream;
    private RemoteDevice remoteDevice;
    private Queue<Data> inputDataQueue = new LinkedList<>();

    public Input(Socket socket, RemoteDevice remoteDevice) {
        this.socket = socket;
        this.remoteDevice = remoteDevice;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void attemptRecovery() {
        try {
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Queue<Data> getDataQueue() {
        return this.inputDataQueue;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}

            if (this.socket.isConnected()) {
                try {
                    Object obj = inputStream.readObject();
                    if (!(obj instanceof Data)) {
                        // no idea what they sent us, ignore it
                        System.out.println("Unknown type");
                        continue;
                    }
                    this.inputDataQueue.add((Data) obj);
                    this.remoteDevice.getUpdateRegister().addDevice(this.remoteDevice);

                } catch (EOFException e) {
                    this.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    this.attemptRecovery();
                } catch (ClassNotFoundException e) {
                    // we should never reach this
                    // as we're casting to pure OBJ
                }
            }

        }
    }
}
