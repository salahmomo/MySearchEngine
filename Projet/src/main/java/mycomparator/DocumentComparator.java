package mycomparator;

import retroindex.Document;

import java.util.Comparator;

/**
 * Created by Salah on 06/11/2015.
 */
public class DocumentComparator implements Comparator<Document> {
    public int compare(Document doc1, Document doc2) {
        if (doc1.getFrequency() > doc2.getFrequency())
            return -1;
        else if (doc1.getFrequency() == doc2.getFrequency())
            return 0;
        else
            return 1;
    }
}
