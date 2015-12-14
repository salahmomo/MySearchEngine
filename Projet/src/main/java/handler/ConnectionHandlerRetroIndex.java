package handler;

import indexer.Indexer;
import retroindex.Document;
import retroindex.RetroIndex;
import retroindex.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by Salah on 13/11/2015.
 */
public class ConnectionHandlerRetroIndex implements Runnable {
    private Socket socket;
    private RetroIndex retroindex;

    public ConnectionHandlerRetroIndex(Socket socket, RetroIndex retroindex) {
        this.socket = socket;
        this.retroindex = retroindex;
    }

    public void run() {
        try
        {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String s;
            while((s = in.readLine()) != null) {
                System.out.println(s);
                if(s.equals("-retroIndexupdate"))
                    retroindex.loadRetroIndex();
                else {
                    SearchResult searchResult = retroindex.find(s);
                    System.out.println("query: " + searchResult.getQuery() + " time: " + searchResult.getTime());
                    for (Document doc : searchResult.getDocuments()) {
                        System.out.println("Freq: " + doc.getFrequency() + " Doc: " + doc.getUrl());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
