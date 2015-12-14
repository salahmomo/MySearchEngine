package crawler;

import indexer.StopWord;
import indexer.StringCleaner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.englishStemmer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salah on 24/10/2015.
 */
public class Crawler implements Runnable {

    static List<String> urlList = new ArrayList<String>();
    private String url;
    private int depth;

    public Crawler(final String url, int depth)
    {
        this.url = url;
        this.depth = depth;
    }

    public void run() {
        try {
            Document doc = Jsoup.connect(url).get();
            depth = depth - 1;
            urlList.add(url);

            String stringDoc = url;
            stringDoc += " " + doc.text();

            Socket socket = null;
            OutputStreamWriter osw;
            try {
                socket = new Socket("localhost", 7777);
                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                osw.write(stringDoc, 0, stringDoc.length());
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
            }

            Elements links = doc.select("a[href]");
            for(Element link: links){
                String newurl = link.attr("abs:href");
                if (!urlList.contains(newurl) && newurl.contains(url) && depth >= 0 && newurl.startsWith("http://") && !newurl.contains("/?")) {
                    Crawler crawl = new Crawler(link.attr("abs:href"), depth);
                    crawl.run();
                }
            }
        } catch (IOException e) {
        }
    }

    /*public static List<String> crawl(final String url, int deph){
        ArrayList<String> listDocs = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(url).get();
            deph = deph - 1;
            urlList.add(url);

            String stringDoc = url;
            stringDoc += " " + doc.text();
            listDocs.add(stringDoc);

            Elements links = doc.select("a[href]");
            for(Element link: links){
                String newurl = link.attr("abs:href");
                if (!urlList.contains(newurl) && newurl.contains(url) && deph >= 0 && newurl.startsWith("http://") && !newurl.contains("/?")) {
                    listDocs.addAll(crawl(link.attr("abs:href"), deph));
                }
            }
        } catch (IOException e) {
        }
        return listDocs;
    }

    public static void crawlerPara(final String url, int depth){
        try {
            Document doc = Jsoup.connect(url).get();
            depth = depth - 1;
            urlList.add(url);

            String stringDoc = url;
            stringDoc += " " + doc.text();

            Socket socket = null;
            OutputStreamWriter osw;
            try {
                socket = new Socket("localhost", 7777);
                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                osw.write(stringDoc, 0, stringDoc.length());
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
            }

            Elements links = doc.select("a[href]");
            for(Element link: links){
                String newurl = link.attr("abs:href");
                if (!urlList.contains(newurl) && newurl.contains(url) && depth >= 0 && newurl.startsWith("http://") && !newurl.contains("/?")) {

                    crawl(link.attr("abs:href"), depth);
                }
            }
        } catch (IOException e) {
        }
    }*/
}
