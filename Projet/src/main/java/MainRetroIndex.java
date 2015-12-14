import crawler.Crawler;
import handler.ConnectionHandlerIndexer;
import handler.ConnectionHandlerRetroIndex;
import indexer.Indexer;
import retroindex.Document;
import retroindex.RetroIndex;
import retroindex.SearchResult;
import serializer.Serializer;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Salah on 13/11/2015.
 */
public class MainRetroIndex {
    public static void main(String[] args) {
        RetroIndex retroindex = new RetroIndex();
        retroindex.loadRetroIndex();
        try {
            ServerSocket server = new ServerSocket(8888);
            try {
                while (true) {
                    Socket socket = server.accept();
                    try {
                        ConnectionHandlerRetroIndex handlerRetroIndex = new ConnectionHandlerRetroIndex(socket, retroindex);
                        handlerRetroIndex.run();
                    } finally {
                        socket.close();
                    }
                }
            }
            finally {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}