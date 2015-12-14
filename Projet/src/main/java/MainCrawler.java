import crawler.Crawler;
import indexer.Indexer;
import retroindex.Document;
import retroindex.LocalIndex;
import retroindex.RetroIndex;
import retroindex.SearchResult;
import serializer.Serializer;

import java.util.List;

/**
 * Created by Salah on 06/11/2015.
 */
public class MainCrawler {
    public static void main(String[] args){
        /*Crawler crawler = new Crawler("http://www.bbc.com/", 1);
        crawler.run();*/
        Crawler crawler1 = new Crawler("http://store.steampowered.com/", 3);
        crawler1.run();
    }
}
