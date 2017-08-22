import common.io.FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Phuong Huynh on 8/15/2017.
 */
public class SentiSVMFilter {
    public static void getSVMDetailResult() throws IOException {
        String inputSVMPath = PathConfigurationTrainingFilter.inputSVM;
        String outputSVMPath = PathConfigurationTrainingFilter.outputSVM;

        ArrayList<String> comments = new ArrayList<String>();

        LinkedList<String> actualLabels = new LinkedList<String>();
        LinkedList<String> predictiveLabels = new LinkedList<String>();

        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            /*#################################################*/
            /* Read and get actual labels from "testing file" */
            br = new BufferedReader(new InputStreamReader(new FileInputStream(inputSVMPath), "UTF-8"));
            int lineNum = 0;

            String line = "";
            while ((line = br.readLine()) != null) {
                ++lineNum;

                if (line.trim().startsWith("#")) {
                    String data[] = line.split("\t");
                    String label = data[0];

                    // check if the line is valid?
                    if (data.length != 2)
                        throw new Exception("Invalid line: " + lineNum);

                    if (label.equals("#neu")) {
                        actualLabels.add("0");
                    } else if (label.equals("#pos")) {
                        actualLabels.add("1");
                    } else if (label.equals("#neg")) {
                        actualLabels.add("-1");
                    }
                }
            }
            /* ###################################################*/

            /*#################################################*/
            /* Read and get predictive labels from "result file" */
            FileIO.createReader(outputSVMPath);
            lineNum = 0;

            while ((line = FileIO.readLine()) != null) {
                ++lineNum;

                String data[] = line.split("\t");
                String label = data[0];

                // check if the line is valid?
                if (data.length != 1)
                    throw new Exception("Invalid line: " + lineNum);

                if (label.equals("0.0")) {
                    predictiveLabels.add("0");
                } else if (label.equals("1.0")) {
                    predictiveLabels.add("1");
                } else if (label.equals("-1.0")) {
                    predictiveLabels.add("-1");
                }

            }
            /* ###################################################*/

            /*####################################################*/
            /*Compare and computing the statistic*/
            if (actualLabels.size() != predictiveLabels.size()) {
                System.out.println("##Wrong length of file##");
                return;
            }

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
            int total = actualLabels.size();

            for (int i = 0; i < actualLabels.size(); ++i) {
                String a = actualLabels.get(i);
                String p = predictiveLabels.get(i);

                if (a.equals(p)) {
                    ++correct;
                }

                if (p.equals("0") && a.equals("0")) {
                    ++neu_neu;
                } else if (a.equals("1") && p.equals("0")) {
                    ++neu_pos;
                } else if (a.equals("-1") && p.equals("0")) {
                    ++neu_neg;
                } else if (a.equals("0") && p.equals("1")) {
                    ++pos_neu;
                } else if (a.equals("1") && p.equals("1")) {
                    ++pos_pos;
                } else if (a.equals("-1") && p.equals("1")) {
                    ++pos_neg;
                } else if (a.equals("0") && p.equals("-1")) {
                    ++neg_neu;
                } else if (a.equals("1") && p.equals("-1")) {
                    ++neg_pos;
                } else if (a.equals("-1") && p.equals("-1")) {
                    ++neg_neg;
                }
            }

            /*####################################################*/
            File detailResult = new File("src/main/resources/filter/" + PathConfigurationTrainingFilter.svmDetailResult);
            String detailResultPath = detailResult.getAbsolutePath();

            FileIO.createWriter(detailResultPath);
            /*
            FileIO.createWriter(detailResultPath);
            FileIO.writeln("neu-neu\t" + neu_neu);
            FileIO.writeln("neu-pos\t" + neu_pos);
            FileIO.writeln("neu-neg\t" + neu_neg);

            FileIO.writeln("pos-neu\t" + pos_neu);
            FileIO.writeln("pos-pos\t" + pos_pos);
            FileIO.writeln("pos-neg\t" + pos_neg);

            FileIO.writeln("neg-neu\t" + neg_neu);
            FileIO.writeln("neg-pos\t" + neg_pos);
            FileIO.writeln("neg-neg\t" + neg_neg);
            */

            double pos_precision = ((double)pos_pos/(double)(pos_pos + pos_neu + pos_neg))*100;
            double pos_recall = ((double)pos_pos/(double)(pos_pos + neu_pos + neg_pos))*100;

            double neu_precision = ((double)neu_neu/(double)(neu_pos + neu_neu + neu_neg))*100;
            double neu_recall = ((double)neu_neu/(double)(pos_neu + neu_neu + neg_neu))*100;

            double neg_precision = ((double)neg_neg/(double)(neg_pos + neg_neu + neg_neg))*100;
            double neg_recall = ((double)neg_neg/(double)(pos_neg + neu_neg + neg_neg))*100;

            double acc = ((double) correct / (double) total) * 100;
            String stringP = acc + "%";

            String sumary = "";
            sumary += "pos_precision" + "\t" + pos_precision + "\n";
            sumary += "pos_recall" + "\t" + pos_recall+ "\n";

            sumary += "neu_precision" + "\t" + neu_precision+ "\n";
            sumary += "neu_recall" + "\t" + neu_recall + "\n";

            sumary += "neg_precision" + "\t" + neg_precision + "\n";
            sumary += "neg_recall" + "\t" + neg_recall + "\n";

            sumary += "accuracy" + "\t" + stringP;

            FileIO.writeln(sumary);
            FileIO.closeWriter();

            FileIO.closeWriter();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static void testReadFile() throws IOException {
        String outputSVMPath = PathConfigurationTrainingFilter.outputSVM;

        FileIO.createReader(outputSVMPath);

        int lineNum = 0;
        String line = "";

        while ((line = FileIO.readLine()) != null) {

        }
        FileIO.closeReader();
    }
}
