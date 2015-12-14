package indexer;

import org.tartarus.snowball.ext.englishStemmer;
import retroindex.WordInformation;
import retroindex.LocalIndex;
import serializer.Serializer;

/**
 * Created by Salah on 24/10/2015.
 */
public class Indexer implements Runnable{

    private String input;

    public Indexer(final String input)
    {
        this.input = input;
    }

    public void run(){
        LocalIndex localIndex = documentIndexer(input);
        Serializer serializer = new Serializer();
        serializer.saveLocalIndex(localIndex);
        System.out.println("endIndexer");
    }
    public LocalIndex documentIndexer(String input)
    {
        String[] urlAndQuery = getUrlAndString(input);
        String url = urlAndQuery[0];
        String query = urlAndQuery[1];

        query = cleanup(query);
        query = tokenize(query);
        query = reduce(query);

        LocalIndex localIndex = value(query, url);
        return localIndex;
    }

    public String[] getUrlAndString(String input)
    {
        String[] listToken = input.split("[ ]+");
        String url = listToken[0];
        Integer i = 0;
        String query = "";
        for (String t : listToken)
        {
            if (i != 0)
            {
                if (i == 1)
                    query += t;
                else
                    query += " " + t;
            }
            i++;
        }

        return new String[] {url, query};
    }

    public static String cleanup(String input)
    {
        input = StringCleaner.removePunctuation(input);
        input = input.toLowerCase();
        return input;
    }

    public static String tokenize(String input)
    {

        String[] listToken = input.split("[ ]+");
        String queryToken = StopWord.removeStopWords(listToken);

        return queryToken;
    }

    public static String reduce(String input)
    {
        String[] listToken = input.split("[ ]+");
        englishStemmer englishStemmer= new englishStemmer();

        String query = "";
        int p = 0;

        for (String t : listToken)
        {
            englishStemmer.setCurrent(t);
            englishStemmer.stem();
            t = englishStemmer.getCurrent();
            if (p == 0)
                query +=t;
            else
                query += " " + t;
            p+=1;

        }
        return query;
    }

    public LocalIndex value(String input, String url)
    {
        //Map<String, WordInformation> mapDocumentInformationOfTerm = new HashMap<String, WordInformation>();
        LocalIndex localIndex = new LocalIndex();
        String[] listToken = input.split("[ ]+");
        Integer position = 1;
        Integer nbTokenInt = listToken.length;
        Double nbTokens = nbTokenInt.doubleValue();

        for (String token : listToken)
        {
            if (localIndex.getDocumentsForIsToken().containsKey(token))
            {
                /*WordInformation docInfo = new WordInformation();
                docInfo.updateDocumentInformation(position, nbTokens);*/
                localIndex.getDocumentsForIsToken().get(token).updateWordInformation(position, nbTokens);
            }
            else
            {
                WordInformation wordInformation = new WordInformation();
                wordInformation.initWordInformation(token, url, position, nbTokens);
                localIndex.getDocumentsForIsToken().put(token, wordInformation);
            }

            position++;
        }

        return localIndex;
    }
}
