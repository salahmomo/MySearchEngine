import indexer.Indexer;
import retroindex.WordInformation;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static spark.Spark.*;

/**
 * Created by Salah on 13/11/2015.
 */
public class MainUser {
    public static void main(String[] args)
    {
        WordInformation w1 = new WordInformation("lolilol", "lolilol", new ArrayList<Integer>(), 0.0);
        WordInformation w2 = new WordInformation("lol", "lolilol", new ArrayList<Integer>(), 0.0);

        Set<WordInformation> set = new HashSet<WordInformation>();
        set.add(w1);
        set.add(w2);

        System.out.println(set.size());

        for (WordInformation w : set)
            System.out.println(w.getWord());

        /*String request = "jax riot";
        Socket socket = null;
        OutputStreamWriter osw;
        try {
            socket = new Socket("localhost", 8888);
            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            osw.write(request, 0, request.length());
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
