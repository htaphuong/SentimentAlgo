import config.PathConfigurationSentiSVM;

/**
 * Created by Phuong Huynh on 8/14/2017.
 */
public class PathConfigurationTrainingFilter {
    public static String root = System.getProperty("user.dir");
    public static String trainingFile = "training_comment.txt";
    public static String filteredTrainingFile = "training_comment_filter.txt";
    public static String stopWordFile = "VNstopwords.txt";

    public static String svmDetailResult = "svmDetailResult.txt";
    public static String inputSVM = PathConfigurationSentiSVM.testing_comment;
    public static String outputSVM = PathConfigurationSentiSVM.predict;
}
