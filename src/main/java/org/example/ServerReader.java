package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerReader extends Thread{
    private final String serverIP;
    private final int serverPort;
    private final WordCount wordCount;

    ServerReader(String serverIP, int serverPort, WordCount wordCount){
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.wordCount = wordCount;
    }

    public void run(){
        Socket socket;
        try {
            socket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            System.err.println("Unable to reach server at " + serverIP + ":" + serverPort + " " + e.getMessage());
            return;
        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        readFromServer(in);

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromServer(BufferedReader in) {
        var response = "";
        while (true){
            try {
                response = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response == null){
                break;
            }
            wordCount.countWords(response);
        }
    }
}
