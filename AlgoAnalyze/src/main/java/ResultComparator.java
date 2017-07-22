import common.io.FileIO;
import config.PathConfigurationRoot;

import java.util.ArrayList;

/**
 * Created by Phuong Huynh on 7/13/2017.
 */
public class ResultComparator {

    String resultSVM = PathConfigurationRoot.resultSVM;
    String resultSentiWord = PathConfigurationRoot.resultSentiWord;

    public void comapareResult(){
        /* SVM result*/
        FileIO.createReader(resultSVM);
        ArrayList<String> svm = new ArrayList<String>();

        String line = "";
        while ((line = FileIO.readLine()) != null){
            svm.add(line);
        }

        /*SentiWord Result*/
        FileIO.createReader(resultSentiWord);
        ArrayList<String> sentiWord = new ArrayList<String>();

        while ((line = FileIO.readLine()) != null){
            sentiWord.add(line);
        }

        /* compare */
        int total = 0;
        int same = 0;
        for (int i = 0; i < svm.size(); ++i){
            if (i >= sentiWord.size())
                break;

            if (svm.get(i).equals(sentiWord.get(i)))
                ++ same;

            ++total;
        }

        System.out.println("Total results: " + total);
        System.out.println("Same results:" + same);
    }

    public static void main(String arg[]){
        ResultComparator resultComparator = new ResultComparator();
        resultComparator.comapareResult();
    }

}
