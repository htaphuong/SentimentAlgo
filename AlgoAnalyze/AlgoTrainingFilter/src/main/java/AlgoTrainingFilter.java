/**
 * Created by Phuong Huynh on 8/14/2017.
 */
public class AlgoTrainingFilter {

    public static void main(String arg[]) throws Exception {
        /*#############################################*/
        /* Important !!!! */
        //SentiWordNetFilter.filter();
        /*#############################################*/

        /*#############################################*/
        /* Important !!!! */
        AlgoSentiSVM.allDataSet();
        SentiSVMFilter.getSVMDetailResult();
        //SentiSVMFilter.testReadFile();
        /*#############################################*/

        /*#############################################*/
        /* Important !!!!
        * ONLY read and filter, NOT perform calculating sentiment score by SentiWordNet
        * */
        //SentiWordNetFilter.filterRawDataByClass();
        //SentiSVMFilter.testReadFile();
        /*#############################################*/

        //SentiWordNetFilter.testFilter("vãi, ăn quỵt tới 120 người, đây chắc là kỷ luc ăn quỵt có tổ chức");
        //SentiWordNetFilter.filterStopWord();
    }
}
