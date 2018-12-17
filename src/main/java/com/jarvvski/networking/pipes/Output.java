package com.jarvvski.networking.pipes;

import com.jarvvski.networking.messages.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Output implements Runnable {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private Queue<Data> outputDataQueue = new LinkedList<>();

    public Output(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void attemptRecovery() {
        try {
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queueData(Data data) {
        this.outputDataQueue.add(data);
    }

    public Queue<Data> getDataQueue() {
        return this.outputDataQueue;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}

            if (!this.outputDataQueue.isEmpty() && this.socket.isConnected()) {
                Data data = this.outputDataQueue.peek();

                try {
                    this.outputStream.writeObject(data);
                    this.outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    this.attemptRecovery();
                    continue;
                }

                this.outputDataQueue.poll();
            }
        }
    }
}
