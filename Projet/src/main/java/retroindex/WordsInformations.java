package retroindex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Salah on 08/11/2015.
 */
public class WordsInformations {
    private Set<WordInformation> wordInformations = new HashSet<WordInformation>();
    private double idf;

    public WordsInformations()
    {

    }

    public WordsInformations(Set<WordInformation> wordInformations, double idf)
    {
        this.wordInformations = wordInformations;
        this.idf = idf;
    }

    public Set<WordInformation> getWordInformations() {
        return wordInformations;
    }

    public void setWordInformations(Set<WordInformation> wordInformations) {
        this.wordInformations = wordInformations;
    }

    public void addWordInformation(WordInformation wordInformation)
    {
        this.wordInformations.add(wordInformation);
    }

    public double getIdf() {
        return idf;
    }
    public void setIdf(double idf) {
        this.idf = idf;
    }

    public void computeIdf(double totalDocuments) {
        this.idf = 1 + Math.log(totalDocuments/wordInformations.size());
    }
}
