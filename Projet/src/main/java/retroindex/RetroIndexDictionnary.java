package retroindex;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Salah on 06/11/2015.
 */
public class RetroIndexDictionnary {
    private Map<String, WordsInformations> dictionnary = new HashMap<String, WordsInformations>();
    private Set<String> documentsUrl = new HashSet<String>();

    public void addWordInformationToWord(String word, WordInformation docInfo)
    {
        dictionnary.get(word).addWordInformation(docInfo);
    }

    public Map<String, WordsInformations> getDictionnary() {
        return dictionnary;
    }

    public void addLocalIndexOnDictionnary(LocalIndex localIndex) {
        for (Map.Entry<String, WordInformation> entry : localIndex.getDocumentsForIsToken().entrySet()) {
            String word = entry.getKey();
            WordInformation wordInformation = entry.getValue();
            Boolean isPresent = false;
            documentsUrl.add(wordInformation.getUrl());

            if (dictionnary.containsKey(word)) {
                for (WordInformation wordInfo : dictionnary.get(word).getWordInformations()) {
                    if (wordInfo.getUrl().equals(wordInformation.getUrl())) {
                        wordInfo.refreshWordInformation(wordInformation.getListPosition(), wordInformation.getFrequency());
                        isPresent = true;
                    }
                }
                if (!isPresent)
                    dictionnary.get(word).addWordInformation(wordInformation);
            }
            else
            {
                WordsInformations wordsInformations = new WordsInformations();
                wordsInformations.addWordInformation(wordInformation);
                dictionnary.put(word, wordsInformations);
            }

        }

        computeWordsInformationIdf();
    }

    public void computeWordsInformationIdf()
    {
        for (Map.Entry<String, WordsInformations> entry : dictionnary.entrySet()) {
            String word = entry.getKey();
            WordsInformations wordsInformations = entry.getValue();

            wordsInformations.computeIdf(documentsUrl.size());
        }
    }

    public Set<String> getDocumentsUrl() {
        return documentsUrl;
    }

    public void setDocumentsUrl(Set<String> documentsUrl) {
        this.documentsUrl = documentsUrl;
    }
}
