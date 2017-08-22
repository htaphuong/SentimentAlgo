package wordnet;

import common.io.FileIO;
import config.PathConfigurationSentiWord;
import model.Comment;
import model.CommentCollection;
import utils.CommentExtractorSentiWord;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Phuong Huynh on 7/4/2017.
 */
public class ProcessRunner {
    public String predict() throws Exception {
        //String pathDict = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\VietSentiWordnet_Test.txt";
        String pathDict = PathConfigurationSentiWord.dictionary_source;
        String pathData = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\input.txt";

        DictHandler dictHandler = new DictHandler(pathDict);
        dictHandler.exportDict();

        CommentExtractorSentiWord.TextToComment(2);

        CommentCollection commentCollection = CommentCollection.getInstance();

        String fullPath = PathConfigurationSentiWord.predict;
        FileIO.createWriter(fullPath);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());
        int correct = 0;
        int total = commentCollection.getCommentList().size();
        for (Comment c : commentCollection.getCommentList()) {
            double score = analyzer.analyze(c.getContent());
            String label = "";
            /*
            if (score >= -0.3 && score <= 0.3) {
                label = "#neu";
                FileIO.write("0.0");
            }
            else if (score < -0.3 && score >= -1.0) {
                label = "#neg";
                FileIO.write("-1.0");
            }
            else if (score > 0.3 && score <= 1) {
                label = "#pos";
                FileIO.write("1.0");
            }
            else if (Double.isNaN(score)){
                label = "#neu";
                FileIO.write("0.0");
            }
            */

            /* ########## CAN BE REUSED ##################*/
            /*
            if (score < 0.25 && score > -0.25) {
                label = "#neu";
                FileIO.write("0.0");
            } else if (score <= -0.25 && score >= -1.0) {
                label = "#neg";
                FileIO.write("-1.0");
            } else if (score >= 0.25 && score <= 1) {
                label = "#pos";
                FileIO.write("1.0");
            } else if (Double.isNaN(score)) {
                label = "#neu";
                FileIO.write("0.0");
            } else {
                FileIO.write(String.valueOf(score));
            }

            if (label.equals(c.getLabel())) {
                ++correct;
            }
            */

            if (score == 0.0) {
                label = "#neu";
                FileIO.write("0.0");
            } else if (score < 0.0) {
                label = "#neg";
                FileIO.write("-1.0");
            } else if (score > 0.0) {
                label = "#pos";
                FileIO.write("1.0");
            } else if (Double.isNaN(score)) {
                label = "#neu";
                FileIO.write("0.0");
            } else {
                FileIO.write(String.valueOf(score));
            }

            if (label.equals(c.getLabel())) {
                ++correct;
            }
            FileIO.writeln("");
        }

        double p = ((double) correct / (double) total) * 100;
        String stringP = p + "%";
        FileIO.write("### p = " + stringP);
        FileIO.closeWriter();

        return stringP;
    }

    public String predictWithDetailResult() throws Exception {
        //String pathDict = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\VietSentiWordnet_Test.txt";
        String pathDict = PathConfigurationSentiWord.dictionary_source;
        String pathData = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\input.txt";

        DictHandler dictHandler = new DictHandler(pathDict);
        dictHandler.exportDict();

        CommentExtractorSentiWord.TextToComment(2);

        CommentCollection commentCollection = CommentCollection.getInstance();

        String fullPath = PathConfigurationSentiWord.predict;
        FileIO.createWriter(fullPath);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());

        HashMap<String,Integer> result = new HashMap<String, Integer>();

        int neu_neu = 0;
        int neu_pos = 0;
        int neu_neg = 0;

        int pos_neu = 0;
        int pos_pos = 0;
        int pos_neg = 0;

        int neg_neu = 0;
        int neg_pos = 0;
        int neg_neg = 0;

        int uid_neu = 0;
        int uid_pos = 0;
        int uid_neg = 0;

        int correct = 0;

        int total = commentCollection.getCommentList().size();
        for (Comment c : commentCollection.getCommentList()) {
            double score = analyzer.analyze(c.getContent());
            String label = "";

            if (score == 0.0) {
                label = "#neu";
                //FileIO.write("0.0");
            } else if (score < 0.0) {
                label = "#neg";
                //FileIO.write("-1.0");
            } else if (score > 0.0) {
                label = "#pos";
                //FileIO.write("1.0");
            } else if (Double.isNaN(score)) {
                label = "#neu";
                //FileIO.write("-100");
            } else {
                //FileIO.write(String.valueOf(score));
            }

            if (label.equals(c.getLabel())) {
                ++correct;
            }

            if (label.equals("#neu") && c.getLabel().equals("#neu")){
                ++neu_neu;
            }
            else if (label.equals("#neu") && c.getLabel().equals("#pos")){
                ++neu_pos;
            }
            else if (label.equals("#neu") && c.getLabel().equals("#neg")){
                ++neu_neg;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#neu")){
                ++pos_neu;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#pos")){
                ++pos_pos;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#neg")){
                ++pos_neg;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#neu")){
                ++neg_neu;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#pos")){
                ++neg_pos;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#neg")){
                ++neg_neg;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#neu")){
                ++uid_neu;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#pos")){
                ++uid_pos;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#neg")){
                ++uid_neg;
            }

            //FileIO.writeln("");
        }

        FileIO.writeln("neu-neu\t" + neu_neu);
        FileIO.writeln("neu-pos\t" + neu_pos);
        FileIO.writeln("neu-neg\t" + neu_neg);

        FileIO.writeln("pos-neu\t" + pos_neu);
        FileIO.writeln("pos-pos\t" + pos_pos);
        FileIO.writeln("pos-neg\t" + pos_neg);

        FileIO.writeln("neg-neu\t" + neg_neu);
        FileIO.writeln("neg-pos\t" + neg_pos);
        FileIO.writeln("neg-neg\t" + neg_neg);

        FileIO.writeln("uid-neu\t" + uid_neu);
        FileIO.writeln("uid-pos\t" + uid_pos);
        FileIO.writeln("uid-neg\t" + uid_neg);

        double pos_precision = ((double)pos_pos/(double)(pos_pos + pos_neu + pos_neg))*100;
        double pos_recall = ((double)pos_pos/(double)(pos_pos + neu_pos + neg_pos))*100;

        double neu_precision = ((double)neu_neu/(double)(neu_pos + neu_neu + neu_neg))*100;
        double neu_recall = ((double)neu_neu/(double)(pos_neu + neu_neu + neg_neu))*100;

        double neg_precision = ((double)neg_neg/(double)(neg_pos + neg_neu + neg_neg))*100;
        double neg_recall = ((double)neg_neg/(double)(pos_neg + neu_neg + neg_neg))*100;

        double p = ((double) correct / (double) total) * 100;
        String stringP = p + "%";
        FileIO.write("### p = " + stringP);
        FileIO.closeWriter();

        String sumary = "";
        /*
        sumary += "pos_precision" + "\t" + pos_precision + "\n";
        sumary += "pos_recall" + "\t" + pos_recall+ "\n";

        sumary += "neu_precision" + "\t" + neu_precision+ "\n";
        sumary += "neu_recall" + "\t" + neu_recall + "\n";

        sumary += "neg_precision" + "\t" + neg_precision + "\n";
        sumary += "neg_recall" + "\t" + neg_recall + "\n";

        sumary += "accuracy" + "\t" + stringP;
        */
        sumary += p + ",";
        return sumary;
    }

    public String predictExtend() throws Exception {
        //String pathDict = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\VietSentiWordnet_Test.txt";
        String pathDict = PathConfigurationSentiWord.dictionary_source;
        String pathData = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\input.txt";

        DictHandler dictHandler = new DictHandler(pathDict);
        dictHandler.exportDict();

        CommentExtractorSentiWord.TextToComment(2);

        CommentCollection commentCollection = CommentCollection.getInstance();

        String fullPath = PathConfigurationSentiWord.predict;
        FileIO.createWriter(fullPath);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());
        int correct = 0;
        int total = commentCollection.getCommentList().size();
        for (Comment c : commentCollection.getCommentList()) {
            double score = analyzer.analyze(c.getContent());
            String label = "";
            /*
            if (score >= -0.3 && score <= 0.3) {
                label = "#neu";
                FileIO.write("0.0");
            }
            else if (score < -0.3 && score >= -1.0) {
                label = "#neg";
                FileIO.write("-1.0");
            }
            else if (score > 0.3 && score <= 1) {
                label = "#pos";
                FileIO.write("1.0");
            }
            else if (Double.isNaN(score)){
                label = "#neu";
                FileIO.write("0.0");
            }
            */
            if (score < 0.0 && score >= -1.0) {
                label = "#neg";
                FileIO.write("-1.0");
            } else if (score <= 1.0 && score >= 0.0) {
                label = "#pos";
                FileIO.write("1.0");
            } else if (Double.isNaN(score)) {
                label = "#pos";
                FileIO.write("1.0");
            } else {
                FileIO.write(String.valueOf(score));
            }

            if (label.equals(c.getLabel())) {
                ++correct;
            }

            FileIO.writeln("");
        }

        double p = ((double) correct / (double) total) * 100;
        String stringP = p + "%";
        FileIO.write("### p = " + stringP);
        FileIO.closeWriter();

        return stringP;
    }

    public int predict(String content) throws IOException {
        String pathDict = PathConfigurationSentiWord.dictionary_source;

        DictHandler dictHandler = new DictHandler(pathDict);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());
        double score = analyzer.analyze(content);
        if (score < 0.0) {
            return -1;
        } else if (score >= 0.0) {
            return 1;
        } else if (Double.isNaN(score)) {
            return 1;
        }
        else
            return 1;
    }

    public int predictWithDetail(String content) throws IOException {
        String pathDict = PathConfigurationSentiWord.dictionary_source;

        DictHandler dictHandler = new DictHandler(pathDict);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());
        double score = analyzer.analyze(content);
        if (score < 0.0) {
            return -1;
        } else if (score == 0.0) {
            return 0;
        }
        else if (score > 0.0){
            return 1;
        } else if (Double.isNaN(score)) {
            return -100;
        }
        else
            return -100;
    }

    public double predictWithScore(String content) throws IOException {
        String pathDict = PathConfigurationSentiWord.dictionary_source;

        DictHandler dictHandler = new DictHandler(pathDict);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());

        //double score = analyzer.analyze(content);
        double score = analyzer.analyzeTestMode(content);

        return score;
    }

    public double predictStopWord(String content) throws IOException {
        String pathDict = PathConfigurationSentiWord.dictionary_source;

        DictHandler dictHandler = new DictHandler(pathDict);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());

        //double score = analyzer.analyze(content);
        double score = analyzer.analyzeStopWord(content);

        return score;
    }

    public String predictWithDetailResult02() throws Exception {
        //String pathDict = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\VietSentiWordnet_Test.txt";
        String pathDict = PathConfigurationSentiWord.dictionary_source;
        String pathData = "C:\\Users\\Phuong Huynh\\Desktop\\JAVA WORKSPACE\\BizSentiWord\\data\\input.txt";

        DictHandler dictHandler = new DictHandler(pathDict);
        dictHandler.exportDict();

        CommentExtractorSentiWord.TextToComment(2);

        CommentCollection commentCollection = CommentCollection.getInstance();

        String fullPath = PathConfigurationSentiWord.predict;
        FileIO.createWriter(fullPath);

        SentiAnalyzer analyzer = new SentiAnalyzer(dictHandler.getDict());

        HashMap<String,Integer> result = new HashMap<String, Integer>();

        int neu_neu = 0;
        int neu_pos = 0;
        int neu_neg = 0;

        int pos_neu = 0;
        int pos_pos = 0;
        int pos_neg = 0;

        int neg_neu = 0;
        int neg_pos = 0;
        int neg_neg = 0;

        int uid_neu = 0;
        int uid_pos = 0;
        int uid_neg = 0;

        int correct = 0;

        int total = commentCollection.getCommentList().size();
        for (Comment c : commentCollection.getCommentList()) {
            /*############*/
            // Use formula 2
            /*############*/
            double score = analyzer.analyze02(c.getContent());
            String label = "";

            if (score == 0.0) {
                label = "#neu";
                //FileIO.write("0.0");
            } else if (score < 0.0) {
                label = "#neg";
                //FileIO.write("-1.0");
            } else if (score > 0.0) {
                label = "#pos";
                //FileIO.write("1.0");
            } else if (Double.isNaN(score)) {
                label = "#neu";
                //FileIO.write("-100");
            } else {
                //FileIO.write(String.valueOf(score));
            }

            if (label.equals(c.getLabel())) {
                ++correct;
            }

            if (label.equals("#neu") && c.getLabel().equals("#neu")){
                ++neu_neu;
            }
            else if (label.equals("#neu") && c.getLabel().equals("#pos")){
                ++neu_pos;
            }
            else if (label.equals("#neu") && c.getLabel().equals("#neg")){
                ++neu_neg;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#neu")){
                ++pos_neu;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#pos")){
                ++pos_pos;
            }
            else if (label.equals("#pos") && c.getLabel().equals("#neg")){
                ++pos_neg;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#neu")){
                ++neg_neu;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#pos")){
                ++neg_pos;
            }
            else if (label.equals("#neg") && c.getLabel().equals("#neg")){
                ++neg_neg;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#neu")){
                ++uid_neu;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#pos")){
                ++uid_pos;
            }
            else if (label.equals("#uid") && c.getLabel().equals("#neg")){
                ++uid_neg;
            }

            //FileIO.writeln("");
        }

        FileIO.writeln("neu-neu\t" + neu_neu);
        FileIO.writeln("neu-pos\t" + neu_pos);
        FileIO.writeln("neu-neg\t" + neu_neg);

        FileIO.writeln("pos-neu\t" + pos_neu);
        FileIO.writeln("pos-pos\t" + pos_pos);
        FileIO.writeln("pos-neg\t" + pos_neg);

        FileIO.writeln("neg-neu\t" + neg_neu);
        FileIO.writeln("neg-pos\t" + neg_pos);
        FileIO.writeln("neg-neg\t" + neg_neg);

        FileIO.writeln("uid-neu\t" + uid_neu);
        FileIO.writeln("uid-pos\t" + uid_pos);
        FileIO.writeln("uid-neg\t" + uid_neg);

        double p = ((double) correct / (double) total) * 100;
        String stringP = p + "%";
        FileIO.write("### p = " + stringP);
        FileIO.closeWriter();

        return stringP;
    }

}
