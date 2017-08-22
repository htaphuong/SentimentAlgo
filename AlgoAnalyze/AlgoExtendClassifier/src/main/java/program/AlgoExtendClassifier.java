package program;

import classifier.kNNClassifier;
import model.Document;
import utils.FileHandler;

import java.util.ArrayList;

/**
 * Created by Phuong Huynh on 8/20/2017.
 */
public class AlgoExtendClassifier {


    public static void main(String arg[]){
        String inputFolder = "src/main/resources/clustering/";
        ArrayList<Document> freeList = new ArrayList<Document>();
        ArrayList<Document> classifiedList = new ArrayList<Document>();


        FileHandler.collectData(inputFolder,freeList,classifiedList);
        kNNClassifier classifier = new kNNClassifier();
        classifier.classify02(freeList,classifiedList);

        for (Document doc : freeList){
            System.out.print("#" + doc.getLabel() + "\t");
            System.out.println(doc.getName());
        }
        /*
        for (Document doc : classifiedList){
            System.out.print("#" + doc.getLabel() + "\t");
            System.out.println(doc.getName());
        }
        */
    }


}
