package retroindex;

import java.util.List;

/**
 * Created by Salah on 07/11/2015.
 */
public class MyAlgo {
    public static Double tf(Double termInDoc, Double totalWordInDoc)
    {
        return termInDoc/totalWordInDoc;
    }

    public static Double idf(Double numberOfDocuments, Double numberOfDocumentsWithTerm) {
        return 1 + Math.log(numberOfDocuments/numberOfDocumentsWithTerm);
    }

    public static Double tfIdf(Double tf, Double idf)
    {
        return tf*idf;
    }

    public static Float cosinusSimilarity(List<Double> tfIdfQueryList, List<Double> tfIdfDocument)
    {
        Float res = 0f;


        res = (dotProduct(tfIdfQueryList, tfIdfDocument).floatValue() /(distanceList(tfIdfQueryList).floatValue() * distanceList(tfIdfDocument).floatValue()));

        return res;
    }

    public static Double dotProduct(List<Double> tfIdfQueryList, List<Double> tfIdfDocument)
    {
        Double res = 0.0;
        for (int i = 0; i < tfIdfQueryList.size() && i < tfIdfDocument.size(); i++)
        {
            res += tfIdfQueryList.get(i) * tfIdfDocument.get(i);
        }

        return res;
    }

    public static Double distanceList(List<Double> list)
    {
        Double res = 0.0;
        for (Double d : list)
        {
            res += d*d;
        }

        res = Math.sqrt(res);

        return res;
    }
}
