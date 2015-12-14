import handler.ConnectionHandlerRetroIndex;
import retroindex.Document;
import retroindex.RetroIndex;
import retroindex.SearchResult;

import javax.print.Doc;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by Salah on 13/11/2015.
 */
public class MainRetroIndexSpark {
    public static void main(String[] args) {
        RetroIndex retroindex = new RetroIndex();
        retroindex.loadRetroIndex();
        get("/SaSearch", (req, res) -> "SaSearch");


        get("/SaSearch/:name", (request, response) -> {
            SearchResult sr = retroindex.find(request.params(":name"));
            String s = "<div>Query: " + sr.getQuery() + " Time: " + sr.getTime()+"</div>";
            for (Document d : sr.getDocuments())
                s+= "<div>Freq: " + d.getFrequency() + " Doc: <a href="+d.getUrl()+System.lineSeparator()+">" + d.getUrl()+System.lineSeparator()+"</a></div>";
            return s;
        });

        /*try {
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
        }*/

    }
}
