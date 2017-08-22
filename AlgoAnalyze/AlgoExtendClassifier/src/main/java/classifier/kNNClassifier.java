package classifier;

import model.Document;
import model.DocumentVector;

import java.util.*;

/**
 * Created by Phuong Huynh on 5/27/2017.
 */
public final class kNNClassifier {
    private final double acceptScore = 0.60;
    private ArrayList<DocumentVector> data = null;
    private ArrayList<DocumentClass> classList = null;

    private ArrayList<Document> classifiedList = null;

    /* removed
    public void performClassify(){
        BaseVectorBuilder builder = new BaseVectorBuilder();
        data = builder.buildDataVector();
        classList = new ArrayList<DocumentClass>();

        int classID = 1;
        for (DocumentVector docVector : data){
            if (classList.isEmpty()){
                String className = "Class" + classID;
                DocumentClass docClass = new DocumentClass(className);
                docClass.addItem(docVector);

                classList.add(docClass);

                ++classID;
            }
            else{
                double majorityScore = 0.0;
                DocumentClass majorityClass = null;

                for (DocumentClass c : classList){
                    double score = calculateClassScore(docVector,c);
                    if (score > majorityScore){
                        majorityScore = score;
                        majorityClass = c;
                    }
                }

                if (majorityScore >= acceptScore){
                    majorityClass.addItem(docVector);
                }
                else{
                    String className = "Class" + classID;
                    DocumentClass docClass = new DocumentClass(className);
                    docClass.addItem(docVector);

                    classList.add(docClass);
                    ++classID;
                }
            }
        }
    }
    */

    /*
    * Perform classify a list of Free Document
    * @param freeList: list of documents need to be classified
    * @param classifiedList: list of documents classified already
    */
    public void classify(ArrayList<Document> freeList, ArrayList<Document> classifiedList){
        if (freeList == null || classifiedList == null){
            return;
        }
        classifiedList = freeList;
        ArrayList<String> existLabels = new ArrayList<String>();

        double majorityScore = 0.0;
        String majorityClass = "";
        Document getTheClassifiedDoc = null;
        for (Document freeDoc : freeList){
            System.out.println("###################");
            System.out.println(freeDoc.getName());
            majorityScore = 0.0;    // reset the majority Score
            majorityClass = "";     // reset majority class
            for (Document classifiedDoc : classifiedList){
                if (!existLabels.contains(classifiedDoc.getLabel())){
                    existLabels.add(classifiedDoc.getLabel());
                }
                double score = calculateDistance(freeDoc, classifiedDoc);
                if (score > majorityScore){
                    majorityScore = score;
                    majorityClass = classifiedDoc.getLabel();
                    getTheClassifiedDoc = classifiedDoc;
                }
            }

            System.out.println(majorityScore);
            if (getTheClassifiedDoc != null)
                System.out.println(getTheClassifiedDoc.getName());
            System.out.println("###################");

            // Check if @majortiryScore > @acceptScore
            // then assign to the @freeDoc this current @majorityClass
            // else assign to the @freeDoc new class
            if (majorityScore > acceptScore){
                System.out.println("^^^^^^^^^^^^^^^^^^^^^");

                freeDoc.setLabel(majorityClass);
                //classifiedList.add(freeDoc);
            }
            else {
                String newLabel = generateLabel(existLabels);
                freeDoc.setLabel(newLabel);
                //classifiedList.add(freeDoc);
            }
        }
    }

    public void classify02(ArrayList<Document> freeList, ArrayList<Document> classifiedList){
        if (freeList == null || classifiedList == null){
            return;
        }
        classifiedList = freeList;
        ArrayList<String> existLabels = new ArrayList<String>();

        double majorityScore = 0.0;
        String majorityClass = "";
        Document getTheClassifiedDoc = null;
        for (Document freeDoc : freeList){
            System.out.println("###################");
            System.out.println(freeDoc.getName());
            majorityScore = 0.0;    // reset the majority Score
            majorityClass = "";     // reset majority class
            for (Document classifiedDoc : freeList){
                if (!existLabels.contains(classifiedDoc.getLabel())){
                    existLabels.add(classifiedDoc.getLabel());
                }
                if (freeDoc == classifiedDoc){
                    continue;
                }
                double score = calculateDistance(freeDoc, classifiedDoc);
                if (score > majorityScore){
                    majorityScore = score;
                    majorityClass = classifiedDoc.getLabel();
                    getTheClassifiedDoc = classifiedDoc;
                }
            }

            Double[] vectorA = generateVectorFromModel(freeDoc,freeDoc);
            Double[] vectorB = generateVectorFromModel(getTheClassifiedDoc,freeDoc);
            System.out.println(Arrays.toString(vectorA));
            System.out.println(Arrays.toString(vectorB));
            System.out.println(majorityScore);
            if (getTheClassifiedDoc != null)
                System.out.println(getTheClassifiedDoc.getName());
            System.out.println("###################");

            // Check if @majortiryScore > @acceptScore
            // then assign to the @freeDoc this current @majorityClass
            // else assign to the @freeDoc new class
            if (majorityScore > acceptScore){
                System.out.println("^^^^^^^^^^^^^^^^^^^^^");
                if (majorityClass == null || majorityClass.equals("")) {
                    majorityClass = generateLabel(existLabels);
                }
                freeDoc.setLabel(majorityClass);
                getTheClassifiedDoc.setLabel(majorityClass);
                //classifiedList.add(freeDoc);
            }
            else {
                String newLabel = generateLabel(existLabels);
                freeDoc.setLabel(newLabel);
                //classifiedList.add(freeDoc);
            }
        }
    }

    /* removed
    private double calculateClassScore(DocumentVector docVector, DocumentClass docClass){
        ArrayList<DocumentResult> resultList = new ArrayList<DocumentResult>();

        for (DocumentVector item : docClass.getItems()){
            double score = cosineSim(docVector,item);
            DocumentResult result = new DocumentResult(item,score);
            resultList.add(result);
        }

        Collections.sort(resultList,new DocumentResultComparator());
        return resultList.get(resultList.size()- 1).cosineSim;
    }
    */

    /*
    * Calculate distance between two document
    * The model vector would be calculating based on the first Document
    * */
    private double calculateDistance(Document from, Document to){
        Double[] vectorA = null;
        Double[] vectorB = null;
        if (from.getTermList().size() >= to.getTermList().size()) {
            vectorA = generateVectorFromModel(from, from);  // Vector represented for @from
            vectorB = generateVectorFromModel(to, from);    // Vector represented for @to
        }
        else {
            vectorA = generateVectorFromModel(from, to);  // Vector represented for @from
            vectorB = generateVectorFromModel(to, to);  // Vector represented for @from
        }
        int len;

        if ((len = vectorA.length) != vectorB.length){

            return 0.0;
        }

        double numerator = 0.0;
        double denominator = 1.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;
        for (int i = 0; i < len; ++i){
            numerator += vectorA[i]*vectorB[i];
            magnitudeA += Math.pow(vectorA[i],2);
            magnitudeB += Math.pow(vectorB[i],2);
        }

        denominator *= Math.sqrt(magnitudeA)*Math.sqrt(magnitudeB);

        return (numerator/denominator);
    }

    /*
    * Generating vector.
    * The first param is Document needed to be generated
    * The second param is model used for generating vector from
    * */
    private Double[] generateVectorFromModel(Document doc, Document model){
        /*if (doc.getTermList().size() > model.getTermList().size()){
            model = doc;
        }*/

        Double[] vector = new Double[model.getTermList().size()];
        Arrays.fill(vector,0.0);
        int index = 0;
        for (Map.Entry<String, Integer> entry : model.getTermList().entrySet()){
            //System.out.print(entry.getKey() + ",");
            if (doc.getTermList().containsKey(entry.getKey())){
                int pos = index;
                int value = doc.getTermList().get(entry.getKey());

                vector[pos] = (double) value;
            }

            ++index;
        }

        return vector;
    }

    private double cosineSim(DocumentVector a, DocumentVector b){
        Double[] vectorA = a.getVector();
        Double[] vectorB = b.getVector();
        int len;

        if ((len = vectorA.length) != vectorB.length){

            return 0.0;
        }

        double numerator = 0.0;
        double denominator = 1.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;
        for (int i = 0; i < len; ++i){
            numerator += vectorA[i]*vectorB[i];
            magnitudeA += Math.pow(vectorA[i],2);
            magnitudeB += Math.pow(vectorB[i],2);
        }

        denominator *= Math.sqrt(magnitudeA)*Math.sqrt(magnitudeB);

        return (numerator/denominator);
    }

    public ArrayList<DocumentClass> getClasses(){
        return this.classList;
    }

    // Generator for random label of document
    protected String generateLabel(ArrayList<String> existLabels){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString().toLowerCase();

        if (existLabels.contains(saltStr)){
            return generateLabel(existLabels);
        }

        return saltStr;
    }

    private class DocumentResult {
        public DocumentVector document;
        public double cosineSim;

        public DocumentResult(DocumentVector document, double score){
            this.document = document;
            this.cosineSim = score;
        }
    }

    // Comparator for DocumentResult in ascending order
    private class DocumentResultComparator implements Comparator<DocumentResult> {

        public int compare(DocumentResult d1, DocumentResult d2) {
            return d1.cosineSim < d2.cosineSim ? -1 : d1.cosineSim == d2.cosineSim ? 0 : 1;
        }
    }
}