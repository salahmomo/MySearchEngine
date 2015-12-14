package indexer;

/**
 * Created by Salah on 06/11/2015.
 */
public class StringCleaner {

    public static String removePunctuation(String s)
    {
        //s = s.replaceAll("[.,;!?)+*$£€/(\"#\"~%_:`><'0-9-]", "");
        s = s.replaceAll("[^A-Za-z ]+", "");
        return s;
    }
}
