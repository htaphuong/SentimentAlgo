package utils;

import io.FileIO;
import model.Document;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Phuong Huynh on 8/12/2017.
 */
public class FileHandler {

    private FileHandler(){
    }

    /*
    * This function is used for collecting file .fc and .uc stored in folder, and simulating data which is used in testing.
    * NOTE: just use for testing
    * */
    public static void collectData(String inputFolder, ArrayList<Document> freeList, ArrayList<Document> classifiedList){
        ArrayList<String> freeFileNames = new ArrayList<String>();
        ArrayList<String> classifiedFileNames = new ArrayList<String>();

        File original = new File(inputFolder);
        File[] files = original.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                String name = f.getName();
                String label = null;
                String content = "";

                if (f.getName().endsWith(".txt")) {
                    freeFileNames.add(f.getName());

                    FileIO.createReader(f.getAbsolutePath());
                    String line;
                    while ((line = FileIO.readLine()) != null) {
                        if (line.startsWith("#")) {
                            label = line.trim().toLowerCase().replace(" ","");
                        }
                        else {
                            content += line;
                        }
                    }

                    Document doc = new Document(name,label,content);
                    if (label == null){
                        freeList.add(doc);
                    }
                    else {
                        classifiedList.add(doc);
                    }
                }
                /*
                if (f.getName().endsWith(".uc"))
                    classifiedFileNames.add(f.getName());
                */
            }
        }
    }
}
