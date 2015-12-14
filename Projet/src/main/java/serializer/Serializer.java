package serializer;

import io.vertx.core.Vertx;
import retroindex.LocalIndex;
import retroindex.RetroIndexDictionnary;
import retroindex.WordInformation;
import retroindex.WordsInformations;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * Created by Salah on 09/11/2015.
 */
public class Serializer{

    private final String folderIndex = "IndexFiles/";
    public static RetroIndexDictionnary retroIndex;
    public void saveRetroIndex(RetroIndexDictionnary retroIndex)
    {
        File dir = new File("IndexFiles");
        for (File file: dir.listFiles()) {
            file.delete();
        }
        FileWriter writer = null;
        try{
            for (Map.Entry<String, WordsInformations> entry : retroIndex.getDictionnary().entrySet()) {
                String word = entry.getKey();
                WordsInformations wordsInformations = entry.getValue();

                writer = new FileWriter(folderIndex+word+".index", false);

                String s = word + "|" + wordsInformations.getIdf();
                s+=System.lineSeparator();
                for (WordInformation wordinformaton : wordsInformations.getWordInformations()) {
                    s += wordinformaton.getUrl()+"|"+ wordinformaton.getFrequency()+"|";
                    for (int i = 0; i < wordinformaton.getListPosition().size(); i++) {
                        if (i==0)
                            s += wordinformaton.getListPosition().get(i);
                        else
                            s += ","+wordinformaton.getListPosition().get(i);
                    }
                    s += System.lineSeparator();
                }
                writer.write(s, 0, s.length());
                writer.close();
            }
        }catch(IOException ex){
            //ex.printStackTrace();
        }finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public void saveLocalIndex(LocalIndex localindex)
    {
        FileWriter writer = null;
        try{
            for (Map.Entry<String, WordInformation> entry : localindex.getDocumentsForIsToken().entrySet()) {
                String word = entry.getKey();
                WordInformation wordInformation = entry.getValue();
                if (!word.equals("")) {

                    writer = new FileWriter(folderIndex + word + ".index", true);

                    String s = "";

                    s += wordInformation.getUrl() + "|" + wordInformation.getFrequency() + "|";
                    for (int i = 0; i < wordInformation.getListPosition().size(); i++) {
                        if (i == 0)
                            s += wordInformation.getListPosition().get(i);
                        else
                            s += "," + wordInformation.getListPosition().get(i);
                    }
                    s += System.lineSeparator();
                    writer.write(s, 0, s.length());
                    writer.close();
                }
            }
        }catch(IOException ex){
            //ex.printStackTrace();
        }finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public RetroIndexDictionnary loadRetroIndexThread()
    {
        this.retroIndex = new RetroIndexDictionnary();
        LoadFile.countFile = 0;
        Long startTime = System.currentTimeMillis();

        File folder = new File("IndexFiles");
        File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                LoadFile loadFile = new LoadFile(file);
                loadFile.start();
                /*FileReader fr = new FileReader(file);
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
                            retroIndex.getDocumentsUrl().add(url);
                        }

                        WordsInformations wordsInformations = new WordsInformations(listWordInformations, 1);
                        retroIndex.getDictionnary().put(word, wordsInformations);
                        br.close();
                        fr.close();
                    } catch (IOException exception) {
                        System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                    }
                }*/
            }
        while (LoadFile.countFile != listOfFiles.length)
        {
        }
        Long time = System.currentTimeMillis()-startTime;
        System.out.println("With Thread: " + time+"ms");

        return retroIndex;
    }

    public RetroIndexDictionnary loadRetroIndex()
    {
        RetroIndexDictionnary retroIndex1 = new RetroIndexDictionnary();
        LoadFile.countFile = 0;
        Long startTime = System.currentTimeMillis();

        File folder = new File("IndexFiles");
        File[] listOfFiles = folder.listFiles();
        try {
            for (File file : listOfFiles) {
                FileReader fr = null;

                fr = new FileReader(file);
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
                                retroIndex1.getDocumentsUrl().add(url);
                            }

                            WordsInformations wordsInformations = new WordsInformations(listWordInformations, 1);
                            retroIndex1.getDictionnary().put(word, wordsInformations);
                            br.close();
                            fr.close();
                        } catch (IOException exception) {
                            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                        }
                    }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Long time = System.currentTimeMillis()-startTime;
        System.out.println("No Thread: " + time+"ms");


        return retroIndex1;
    }
}
