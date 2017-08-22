package classifier;

import model.Document;

import java.util.ArrayList;

/**
 * Created by Phuong Huynh on 5/27/2017.
 */
public class DocumentClass {
    private String name;
    private ArrayList<Document> documents;

    public DocumentClass(String name){
        this.name = name;
        this.documents = new ArrayList<Document>();
    }

    public DocumentClass(String name, ArrayList<Document> documents){
        this.name = name;
        this.documents = documents;
    }

    public boolean addDocument(Document document){
        if (this.documents == null)
            return false;

        this.documents.add(document);
        return true;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Document> getDocuments(){
        return this.documents;
    }

    public void printName(){
        System.out.println(this.name);
    }

}
