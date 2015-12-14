package handler;

import indexer.Indexer;

import java.io.*;
import java.net.Socket;

/**
 * Created by Salah on 13/11/2015.
 */
public class ConnectionHandlerIndexer implements Runnable {
    private Socket socket;

    public ConnectionHandlerIndexer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try
        {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String s = null;
            while((s = in.readLine()) != null) {
                System.out.println(s);
                Indexer indexer = new Indexer(s);
                indexer.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
