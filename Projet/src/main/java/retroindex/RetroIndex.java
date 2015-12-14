package retroindex;

import indexer.Indexer;
import mycomparator.DocumentComparator;
import org.tartarus.snowball.ext.englishStemmer;
import serializer.Serializer;

import java.util.*;

/**
 * Created by Salah on 06/11/2015.
 */
public class RetroIndex implements Runnable{

    public static RetroIndexDictionnary dictionnary = new RetroIndexDictionnary();
    public String query;

    public void RetroIndex()
    {

    }

    public void RetroIndex(String query)
    {
        this.query = query;
    }

    public void run()
    {
        //this.find()
    }

    public void loadRetroIndex()
    {
        Serializer serializer = new Serializer();
        //RetroIndexDictionnary tmpdictionnary = serializer.loadRetroIndex();
        RetroIndexDictionnary tmpdictionnary = serializer.loadRetroIndexThread();
        tmpdictionnary.computeWordsInformationIdf();
        dictionnary = tmpdictionnary;
    }

    public void addEntry(final LocalIndex localIndex)
    {
        dictionnary.addLocalIndexOnDictionnary(localIndex);
    }

    public retroindex.SearchResult find(final String search)
    {
        retroindex.SearchResult searchResult = new retroindex.SearchResult();

        String[] queryToken = search.split("[ ]+");
        double querySize = queryToken.length;
        Map<String, Double> queryVector = new HashMap<String, Double>();
        List<Double> queryFrequancy = new ArrayList<Double>();
        Map<String, List<WordInformation>> wordPerDocuments = new HashMap<String, List<WordInformation>>();

        Long startTime = System.currentTimeMillis();

        for (String word : queryToken)
        {
            word = word.toLowerCase();
            englishStemmer englishStemmer = new englishStemmer();
            englishStemmer.setCurrent(word);
            englishStemmer.stem();
            word = englishStemmer.getCurrent();

            if (queryVector.containsKey(word))
                queryVector.put(word, queryVector.get(word) + 1.0);
            else
                queryVector.put(word, 1.0);
        }

        for (Map.Entry<String, Double> entry : queryVector.entrySet()) {
            String word = entry.getKey();
            Double numberWord = entry.getValue();
            Double idfWord = 1.0;

            if (dictionnary.getDictionnary().containsKey(word))
                idfWord = dictionnary.getDictionnary().get(word).getIdf();

            queryVector.put(word, MyAlgo.tfIdf(MyAlgo.tf(numberWord, querySize), idfWord));
            System.out.println("query: word: " +word + " tf: " +MyAlgo.tf(numberWord, querySize) + " idf: " + idfWord + " tfidf: "+ queryVector.get(word));
            queryFrequancy.add(queryVector.get(word));

            if (dictionnary.getDictionnary().containsKey(word)) {
                for (WordInformation wordInfo : dictionnary.getDictionnary().get(word).getWordInformations())
                {
                    WordInformation wordinfo = new WordInformation(wordInfo.getWord(), wordInfo.getUrl(), wordInfo.getListPosition(), wordInfo.getFrequency());
                    wordinfo.transformToTfIdf(dictionnary.getDictionnary().get(word).getIdf());

                    if (wordPerDocuments.containsKey(wordInfo.getUrl()))
                        wordPerDocuments.get(wordInfo.getUrl()).add(wordinfo);
                    else {
                        List<WordInformation> wordInformations = new ArrayList<WordInformation>();
                        wordInformations.add(wordinfo);
                        wordPerDocuments.put(wordInfo.getUrl(), wordInformations);
                    }
                }
            }
        }



        for (Map.Entry<String, List<WordInformation>> entry : wordPerDocuments.entrySet()) {
            String url = entry.getKey();
            List<WordInformation> wordInformations = entry.getValue();

            List<Double> frequancyWordInDoc = new ArrayList<Double>();

            for (String word : queryVector.keySet()) {
                Double tfidfWordDoc = 0.0;
                for (WordInformation wordInformation : wordInformations) {
                    if (wordInformation.getWord().equals(word)) {
                        tfidfWordDoc = wordInformation.getFrequency();
                    }
                }
                frequancyWordInDoc.add(tfidfWordDoc);
            }

            Float cosFrequancy = MyAlgo.cosinusSimilarity(queryFrequancy, frequancyWordInDoc);
            Document document = new Document(url, cosFrequancy);
            searchResult.getDocuments().add(document);
        }

        DocumentComparator docCompare = new DocumentComparator();
        Collections.sort(searchResult.getDocuments(), docCompare);


        Double time = (double)(System.currentTimeMillis()-startTime);
        searchResult.setQuery(search);
        searchResult.setTime(time);

        return searchResult;
    }

    public retroindex.SearchResult findByPhrases(final String search)
    {
        retroindex.SearchResult searchResult = new retroindex.SearchResult();

        String query = search;
        query = Indexer.cleanup(query);
        query = Indexer.tokenize(query);
        query = Indexer.reduce(query);

        String[] queryToken = query.split("[ ]+");

        double querySize = queryToken.length;
        Map<String, Double> queryVector = new HashMap<String, Double>();
        List<Double> queryFrequancy = new ArrayList<Double>();
        Map<String, List<WordInformation>> wordPerDocuments = new HashMap<String, List<WordInformation>>();

        Long startTime = System.currentTimeMillis();

        for (String word : queryToken)
        {
            if (!dictionnary.getDictionnary().containsKey(word))
                return searchResult;
            System.out.println("TokenPQ: " + word);
        }

        for (String word : queryToken)
        {
            if (queryVector.containsKey(word))
                queryVector.put(word, queryVector.get(word) + 1.0);
            else
                queryVector.put(word, 1.0);

            System.out.println("wordidf: " + dictionnary.getDictionnary().get(word).getIdf());
        }

        for (Map.Entry<String, Double> entry : queryVector.entrySet()) {
            String word = entry.getKey();
            Double numberWord = entry.getValue();
            Double idfWord = 0.0;

            if (dictionnary.getDictionnary().containsKey(word))
                idfWord = dictionnary.getDictionnary().get(word).getIdf();

            queryVector.put(word, MyAlgo.tfIdf(MyAlgo.tf(numberWord, querySize), idfWord));
            System.out.println("query: word: " +word + " tf: " +MyAlgo.tf(numberWord, querySize) + " idf: " + idfWord + " tfidf: "+ queryVector.get(word));
            queryFrequancy.add(queryVector.get(word));

            if (dictionnary.getDictionnary().containsKey(word)) {
                for (WordInformation wordInfo : dictionnary.getDictionnary().get(word).getWordInformations())
                {
                    WordInformation wordinfo = new WordInformation(wordInfo.getWord(), wordInfo.getUrl(), wordInfo.getListPosition(), wordInfo.getFrequency());
                    wordinfo.transformToTfIdf(dictionnary.getDictionnary().get(word).getIdf());

                    if (wordPerDocuments.containsKey(wordInfo.getUrl()))
                        wordPerDocuments.get(wordInfo.getUrl()).add(wordinfo);
                    else {
                        List<WordInformation> wordInformations = new ArrayList<WordInformation>();
                        wordInformations.add(wordinfo);
                        wordPerDocuments.put(wordInfo.getUrl(), wordInformations);
                    }
                }
            }
        }



        for (Map.Entry<String, List<WordInformation>> entry : wordPerDocuments.entrySet()) {
            String url = entry.getKey();
            List<WordInformation> wordInformations = entry.getValue();

            List<Double> frequancyWordInDoc = new ArrayList<Double>();

            List<List<Integer>> allWordspositons = new ArrayList<List<Integer>>();

            int wordInDoc = 0;

            for (String word : queryToken)
            {
                for (WordInformation wI :wordInformations)
                {
                    if (wI.getWord().equals(word)) {
                        wordInDoc += 1;
                        allWordspositons.add(wI.getListPosition());
                        break;
                    }
                }
            }

            if (wordInDoc == querySize) {

                List<Integer> posLetter = new ArrayList<Integer>();
                Integer consecutiveWord = 0;

                for (Integer il = 0; il < allWordspositons.size(); il++)
                {
                    if (il == 0) {
                        posLetter = allWordspositons.get(il);
                        consecutiveWord += 1;
                    }
                    else {
                        for (Integer i = 0; i < posLetter.size(); i++)
                        {
                            if (allWordspositons.get(il).contains(posLetter.get(i) + 1))
                            {
                                posLetter = allWordspositons.get(il);
                                consecutiveWord += 1;
                                break;
                            }
                        }
                    }
                }

                if (consecutiveWord == querySize)
                {
                    for (String word : queryVector.keySet()) {
                        Double tfidfWordDoc = 0.0;
                        for (WordInformation wordInformation : wordInformations) {
                            if (wordInformation.getWord().equals(word)) {
                                tfidfWordDoc = wordInformation.getFrequency();
                            }
                        }
                        frequancyWordInDoc.add(tfidfWordDoc);
                    }

                    Float cosFrequancy = MyAlgo.cosinusSimilarity(queryFrequancy, frequancyWordInDoc);
                    Document document = new Document(url, cosFrequancy);
                    searchResult.getDocuments().add(document);
                }
            }
        }

        DocumentComparator docCompare = new DocumentComparator();
        Collections.sort(searchResult.getDocuments(), docCompare);


        Long endTime = System.currentTimeMillis();
        Double time = (double)(System.currentTimeMillis()-startTime);
        searchResult.setQuery(search);
        searchResult.setTime(time);

        return searchResult;
    }

    public void updateRetroIndexIdf()
    {
        this.dictionnary.computeWordsInformationIdf();
    }
}
