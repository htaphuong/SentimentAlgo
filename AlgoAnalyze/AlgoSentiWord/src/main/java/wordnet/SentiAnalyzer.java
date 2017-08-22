package wordnet;

import vn.hus.nlp.tokenizer.VietTokenizer;

import java.util.HashMap;

/**
 * Created by Phuong Huynh on 3/16/2017.
 */
public class SentiAnalyzer {
    HashMap<String,Double> dictionary = null;
    Double result = 0.0;
    public SentiAnalyzer(HashMap<String,Double> dictionary){
        this.dictionary = dictionary;
    }

    public String[] extractSentence(String sentence){
        VietTokenizer vietTokenizer = new VietTokenizer();
        return vietTokenizer.tokenize(sentence);
    }

    public double analyze(String sentence){

        String[] terms = extractSentence(sentence);
        int sum = 0;
        double score = 0;

        //String terms = sentence.replace(" ","_");
        /*
        for (Map.Entry<String,Double> entry: dictionay.entrySet()){
            if (terms.toLowerCase().contains(entry.getKey().toString())){
                terms = terms.replace(entry.getKey().toString(),"");
                ++sum;
                score += entry.getValue();
                System.out.println(entry.getKey());
            }
        }
        */

        for (String t : terms){
            String[] term = t.split(" ");
            for (String word : term) {
                if (dictionary.containsKey(word.toLowerCase())) {
                    ++sum;
                    score += dictionary.get(word.toLowerCase());
                    //System.out.println(word);
                }
            }
        }

        double result = score/ (double) sum;
        this.result = result;
        return result;
    }

    public double analyze02(String sentence){

        String[] terms = extractSentence(sentence);
        int sum = 0;
        double score = 0;

        //String terms = sentence.replace(" ","_");
        /*
        for (Map.Entry<String,Double> entry: dictionay.entrySet()){
            if (terms.toLowerCase().contains(entry.getKey().toString())){
                terms = terms.replace(entry.getKey().toString(),"");
                ++sum;
                score += entry.getValue();
                System.out.println(entry.getKey());
            }
        }
        */

        for (String t : terms){
            String[] term = t.split(" ");
            for (String word : term) {
                if (dictionary.containsKey(word.toLowerCase())) {
                    ++sum;
                    score += dictionary.get(word.toLowerCase());
                    //System.out.println(word);
                }
                else {
                    /*#############*/
                    // Another formula
                    /*#############*/
                    ++sum;
                    score += 0;
                }
            }
        }

        double result = score/ (double) sum;
        this.result = result;
        return result;
    }

    public double analyzeTestMode(String sentence){
        boolean test = false;

        String[] terms = extractSentence(sentence);
        int sum = 0;
        double score = 0;

        //String terms = sentence.replace(" ","_");
        /*
        for (Map.Entry<String,Double> entry: dictionay.entrySet()){
            if (terms.toLowerCase().contains(entry.getKey().toString())){
                terms = terms.replace(entry.getKey().toString(),"");
                ++sum;
                score += entry.getValue();
                System.out.println(entry.getKey());
            }
        }
        */

        for (String t : terms){
            String[] term = t.split(" ");
            for (String word : term) {
                if (dictionary.containsKey(word.toLowerCase())) {
                    if (test){
                        System.out.println(word);
                    }
                    ++sum;
                    score += dictionary.get(word.toLowerCase());
                    //System.out.println(word);
                }
            }
        }

        double result = score/ (double) sum;
        this.result = result;
        return result;
    }

    public double analyzeStopWord(String word){
        boolean test = true;

        int sum = 0;
        double score = 0;

        if (dictionary.containsKey(word.toLowerCase())) {
            if (test){
                System.out.println(word);
            }
            ++sum;
            score += dictionary.get(word.toLowerCase());
            //System.out.println(word);
        }

        double result = score/ (double) sum;
        this.result = result;
        return result;
    }

    public double getResult(){
        return this.result;
    }

}
