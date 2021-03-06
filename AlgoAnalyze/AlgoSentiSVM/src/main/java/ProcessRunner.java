import utils.CommentExtractor;

/**
 * Created by Phuong Huynh on 6/27/2017.
 */
public class ProcessRunner {
    public void testTrain() throws Exception {
        CommentExtractor.TextToComment(1);
        CommentExtractor.CommentToCommentSVMandFeature();
        CommentExtractor.CommentSVMToTrainingSet();

        CommentExtractor.saveSVMFeatureSpace();

        SVMAnalyzer.train();
    }

    public void testTest() throws Exception {
        CommentExtractor.TextToComment(2);
        CommentExtractor.CommentToCommentSVMandFeature();
        CommentExtractor.CommentSVMToTestingSet();
        SVMAnalyzer.test();
    }

    public void predict(String content) throws Exception {
        CommentExtractor.loadSVMFeatureSpace();

        CommentExtractor.TextToComment(content);
        CommentExtractor.CommentToCommentSVMandFeature();
        CommentExtractor.CommentSVMToPredictSet();

        SVMAnalyzer.predict();
    }
}
