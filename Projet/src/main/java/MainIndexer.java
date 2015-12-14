import handler.ConnectionHandlerIndexer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Salah on 13/11/2015.
 */
public class MainIndexer {
    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(7777);
            try {
                while (true) {
                    Socket socket = server.accept();
                    try {
                        ConnectionHandlerIndexer handlerIndexer = new ConnectionHandlerIndexer(socket);
                        handlerIndexer.run();
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
