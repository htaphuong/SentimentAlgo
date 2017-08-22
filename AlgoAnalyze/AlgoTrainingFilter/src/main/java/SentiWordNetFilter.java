import common.io.FileIO;
import wordnet.AlgoSentiWord;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Phuong Huynh on 8/13/2017.
 */
public class SentiWordNetFilter {

    /*
    *  The function is used to filter the comments which can be computed score
    * */
    public static void filter() throws IOException {
        File input = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.trainingFile);
        String inputPath = input.getAbsolutePath();

        File output = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.filteredTrainingFile);
        String outputPath = output.getAbsolutePath();

        ArrayList<String> comments = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"));
            int lineNum = 0;

            String line;
            while ((line = br.readLine()) != null) {
                ++lineNum;

                if (line.trim().startsWith("#")) {
                    String data[] = line.split("\t");
                    String label = data[0];

                    // check if the line is valid?
                    if (data.length != 2)
                        throw new Exception("Invalid line: " + lineNum);

                    int result = AlgoSentiWord.predictWithDetail(data[1]);

                    String comment = "";
                    if (result == 0) {
                        comment += "#neu\t";
                    } else if (result == -1) {
                        comment += "#neg\t";
                    } else if (result == 1) {
                        comment += "#pos\t";
                    } else if (result == -100) {
                        //comment += "#UID\t";
                        comment += "#neg\t";
                    }
                    comment += data[1];

                    int filter = -1;
                    /*###########################*/
                    // optional
                    // filter by class;
                    if (result == filter || result == -100)
                        comments.add(comment);
                }
            }

            FileIO.createWriter(outputPath);
            for (String c : comments){
                FileIO.writeln(c);
            }
            FileIO.closeWriter();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static void filterRawDataByClass() throws IOException {
        File input = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.trainingFile);
        String inputPath = input.getAbsolutePath();

        File output = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.filteredTrainingFile);
        String outputPath = output.getAbsolutePath();

        ArrayList<String> comments = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"));
            int lineNum = 0;

            String line;
            while ((line = br.readLine()) != null) {
                ++lineNum;

                if (line.trim().startsWith("#")) {
                    String data[] = line.split("\t");
                    String label = data[0];

                    // check if the line is valid?
                    if (data.length != 2)
                        throw new Exception("Invalid line: " + lineNum);

                    String comment = "";
                    comment += data[0] + "\t";
                    comment += data[1];

                    String filter = "#neg";
                    /*###########################*/
                    // optional
                    // filter comment
                    if (label.equals(filter))
                        comments.add(comment);
                }
            }

            FileIO.createWriter(outputPath);
            for (String c : comments){
                FileIO.writeln(c);
            }
            FileIO.closeWriter();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static void testFilter(String s) throws IOException {
       /*
        boolean isStopWord = true;
        if (isStopWord) {
            wordnet.AlgoSentiWord.predictStopWord(s);
        }
        */
        System.out.println(AlgoSentiWord.predictWithScore(s));
    }

    public static void filterStopWord() throws IOException {
        File stopWordFile = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.stopWordFile);
        String stopWordPath = stopWordFile.getAbsolutePath();

        ArrayList<String> stopWords = new ArrayList<String>();

        String line = "";
        FileIO.createReader(stopWordPath);
        while ((line = FileIO.readLine()) != null){
            stopWords.add(line);
        }
        FileIO.closeReader();

        for (String s : stopWords){
            testFilter(s);
        }

    }

}


