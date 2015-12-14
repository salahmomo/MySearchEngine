package retroindex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salah on 06/11/2015.
 */
public class WordInformation {
    private String word;
    private String url;
    private List<Integer> listPosition = new ArrayList<Integer>();
    private double frequency;

    public WordInformation()
    {
    }

    public WordInformation(String word, String url, List<Integer> positions, double frequency)
    {
        this.word = word;
        this.url = url;
        this.listPosition = positions;
        this.frequency = frequency;
    }


    public void initWordInformation(String word, String url, Integer position, Double totalTokenOnDocument)
    {
        this.word = word;
        this.url = url;
        this.listPosition.add(position);
        this.frequency = this.listPosition.size()/totalTokenOnDocument;
    }

    public void updateWordInformation(Integer position, Double totalTokenOnDocument)
    {
        this.listPosition.add(position);
        this.frequency = this.listPosition.size()/totalTokenOnDocument;
    }

    public void refreshWordInformation(List<Integer> positions, Double totalTokenOnDocument)
    {
        this.listPosition = positions;
        this.frequency = this.listPosition.size()/totalTokenOnDocument;
    }

    @Override
    public int hashCode() {
        return this.url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WordInformation other = (WordInformation) obj;
        if (!this.url.equals(other.getUrl()))
            return false;

        return true;
    }

    public void transformToTfIdf(Double idf){
        this.frequency = this.frequency * idf;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getListPosition() {
        return listPosition;
    }

    public void setListPosition(List<Integer> listPosition) {
        this.listPosition = listPosition;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }
}
