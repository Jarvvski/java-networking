package com.jarvvski.networking.registers;

import com.jarvvski.networking.local.LocalDevice;
import com.jarvvski.networking.remote.RemoteDevice;

import java.util.LinkedList;
import java.util.Queue;

public class UpdateRegister implements Runnable {

    private Queue<RemoteDevice> updatesQueue = new LinkedList<>();
    private LocalDevice localDevice;

    public UpdateRegister(LocalDevice device) {
        this.localDevice = device;
    }

    public void addDevice(RemoteDevice device) {
        if (!this.updatesQueue.contains(device)) {
            this.updatesQueue.add(device);
        }
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}

            if (!this.updatesQueue.isEmpty() && this.localDevice.isRunning()) {
                RemoteDevice remote = updatesQueue.poll();

                if (remote.getInput().getDataQueue().size() == 0) {
                    updatesQueue.remove(remote);
                    continue;
                }

                this.localDevice.getMasterHandler().handle(remote.getUpdate());

                if (remote.hasUpdate()) {
                    updatesQueue.add(remote);
                }
            }
        }
    }
}
