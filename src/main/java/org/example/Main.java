package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final var wordCount = new WordCount();

        if(!(args.length > 0)){
            System.err.println("No server specified.");
            return;
        }

        List<ServerReader> serverReaders = new ArrayList<>();
        for(String arg: args){
            var address = arg.split(":");
            var serverIP = address[0];
            var serverPort = Integer.parseInt(address[1]);
            serverReaders.add(new ServerReader(serverIP, serverPort, wordCount));
        }

        for(ServerReader serverReader: serverReaders){
            serverReader.run();
        }

        for(ServerReader serverReader: serverReaders){
            try {
                serverReader.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        wordCount.getCount(5);
    }
}
