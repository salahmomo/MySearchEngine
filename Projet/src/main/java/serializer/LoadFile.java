package serializer;

import io.vertx.core.AbstractVerticle;
import retroindex.RetroIndexDictionnary;
import retroindex.WordInformation;
import retroindex.WordsInformations;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Salah on 13/11/2015.
 */
public class LoadFile extends AbstractVerticle {

    public static Integer countFile;
    private File file;

    public LoadFile(File file)
    {
        this.file = file;
    }

    public void start() {
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String word = file.getName().replace(".index", "");

            if (!word.equals("")) {
                System.out.println(word);
                Set<WordInformation> listWordInformations = new HashSet<WordInformation>();
                 try {
                     String line = null;
                     //System.out.println(line);

                     while ((line = br.readLine()) != null)
                     {
                         String[] infos = line.split("[|]");
                         String url = infos[0];
                         Double freq = Double.parseDouble(infos[1]);
                         String[] tabPostions = infos[2].split(",");
                         List<Integer> positons = new ArrayList<Integer>();
                          for (String postion : tabPostions)
                              positons.add(Integer.parseInt(postion));
                         WordInformation wordInformation = new WordInformation(word, url, positons, freq);
                         listWordInformations.add(wordInformation);
                         addUrlOnDocumentsUrl(url);
                     }
                     WordsInformations wordsInformations = new WordsInformations(listWordInformations, 1);
                     putWordAndWordInfoOnDictionnary(word, wordsInformations);
                     br.close();
                     fr.close();
                 } catch (IOException exception) {
                     System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                 }
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        finally {
            countFile += 1;
        }
    }

    private synchronized void addUrlOnDocumentsUrl(String url)
    {
        Serializer.retroIndex.getDocumentsUrl().add(url);
    }

    private synchronized void putWordAndWordInfoOnDictionnary(String word, WordsInformations wordsInformations)
    {
        Serializer.retroIndex.getDictionnary().put(word, wordsInformations);
    }
}
