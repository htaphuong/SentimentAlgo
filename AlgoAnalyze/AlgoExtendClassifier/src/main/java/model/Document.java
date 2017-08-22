package model;

import vn.hus.nlp.tokenizer.VietTokenizer;

import java.util.TreeMap;

/**
 * Created by Phuong Huynh on 5/24/2017.
 */
public class Document {
    private String name = null;
    private String url = null;
    private String id = null;
    private String label = null;
    private String content = null;
    private TreeMap<String, Integer> termList = null;

    public Document(){
    }

    public Document(String name, String id){
        this.name = name;
        this.id = id;
        this.termList = new TreeMap<String, Integer>();
    }

    public Document(String name, String label, String content){
        this.name = name;
        this.label = label;
        this.content = content;
        generateTermList();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setID(String ID){
        this.id = id;
    }

    public void setURL(String url){
        this.url = url;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setListTerm(TreeMap listTerm){
        this.termList = listTerm;
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }

    public String getURL(){
        return this.url;
    }

    public String getLabel(){
        return this.label;
    }

    public TreeMap<String, Integer> getTermList(){
        return this.termList;
    }

    private void generateTermList(){
        String REMOVALLIST = "':,...!-()?\";";
        if (content == null)
            return;

        if (termList == null){
            termList = new TreeMap<String, Integer>();
        }
        else {
            termList.clear();
        }

        String[] sentences = tokenize(name);
        for (String sentence : sentences) {
            String terms[] = sentence.split(" ");
            for (String term : terms) {
                term = term.toLowerCase();
                if (REMOVALLIST.contains(term)){
                    continue;
                }
                if (termList.containsKey(term)){
                    int freq = termList.get(term);
                    ++ freq;
                    termList.replace(term,freq);
                }
                else {
                    termList.put(term, 1);
                }
            }
        }

        sentences = tokenize(content);
        for (String sentence : sentences) {
            String terms[] = sentence.split(" ");
            for (String term : terms) {
                term = term.toLowerCase();
                if (REMOVALLIST.contains(term)){
                    continue;
                }
                if (termList.containsKey(term)){
                    int freq = termList.get(term);
                    ++ freq;
                    termList.replace(term,freq);
                }
                else {
                    termList.put(term, 1);
                }
            }
        }
    }

    private static String[] tokenize(String sentence){
        VietTokenizer vietTokenizer = new VietTokenizer();
        return vietTokenizer.tokenize(sentence);
    }
}
