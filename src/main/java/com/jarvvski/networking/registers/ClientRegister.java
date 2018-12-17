package com.jarvvski.networking.registers;

import com.jarvvski.networking.local.LocalDevice;
import com.jarvvski.networking.remote.Client;
import com.jarvvski.networking.local.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

public class ClientRegister implements Runnable {

    private HashSet<Client> clients = new HashSet<>();
    private Server server;

    public ClientRegister(Server server) {
        this.server = server;
    }

    public void registerClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public HashSet<Client> getClients() {
        return this.clients;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}

            try {
                Socket clientSocket = this.server.getServerSocket().accept();
                // TODO: clearly need better way of handling remote here
                Client client = new Client(clientSocket, server.getUpdateRegister());
                this.registerClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
